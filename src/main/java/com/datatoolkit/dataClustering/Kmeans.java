package com.datatoolkit.dataClustering;

import com.datatoolkit.dataClustering.ClusterExceptions.InsufficientDataSizeException;
import com.datatoolkit.dataClustering.ClusteringData.DataCluster;
import com.datatoolkit.dataClustering.ClusteringData.DataPoint;
import com.datatoolkit.dataClustering.ClusteringData.DataSet;
import com.datatoolkit.dataClustering.Listeners.ClusteringResultListener;
import com.datatoolkit.dataClustering.utils.MLmathTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

 final class Kmeans implements KmeansClusterable, Runnable {


   private DataSet dataSet;
   private List<DataPoint> customCentroids;
   private boolean withOptimization = false;
   private int clusters = 2;
   private Thread clusterThread;
   private ClusteringResultListener clusteringResultListener;



    public Kmeans(DataSet dataSet,boolean withOptimization,int clusters) {
        this.dataSet = dataSet;
        this.withOptimization =withOptimization;
        this.clusters = clusters;
        customCentroids = new ArrayList<>();
    }

    public int getClusters() {
        return clusters;
    }

    public void setClusters(int clusters) {
        this.clusters = clusters;
    }


     /**
      * This method prepares the output Set of the algorithm given the input options
      * performs the optimization step if required
      * @author Khiari Youssef
      * @return
      * @throws Exception
      */
    private Map<String,? extends DataSet> prepareDataSetForClustering() throws Exception {

            if (withOptimization) {
                customCentroids.clear();
                customCentroids.addAll(kmeansCentroidInitializer(clusters));
            } else {
                customCentroids = new ArrayList<>(clusters);
            }
            // creating initial empty clusters
        if (dataSet == null || dataSet.getDataPoints() == null) {
             throw  new NullPointerException("dataset must be non-null to be clustered");
        } else {


            int k = customCentroids.size();
            if (k > 1) {
            Map<String, DataCluster> clusters = new TreeMap<String, DataCluster>();
            for (int i=0; i<k; i++) {
                DataCluster dc = new DataCluster(dataSet.getLabel()+":id"+(i+1),customCentroids.get(i));
                clusters.put(dc.getLabel(),dc);
            }
            // END INIT

            // preparing initial partitions
            for(DataPoint dp : dataSet.getDataPoints()) {
                double mind = MLmathTools.minDistance(customCentroids,dp);
                DataPoint matchingcentroid = MLmathTools.findMatchingDataPointByDistance(dp,customCentroids,mind);
                for (Map.Entry<String,DataCluster> entry : clusters.entrySet()) {
                    DataPoint c = entry.getValue().getCentroid();
                    if (c.equals(matchingcentroid)) {
                        entry.getValue().getDataPoints().add(dp);
                    }
                }
            }
            //iterating

            return(clusters);}
            else {
                throw  new InsufficientDataSizeException(k);
            }
        }

    }


     @Override
     public void run() {
        System.out.println("running ClusterThread");
         long startTime  = System.currentTimeMillis();
         try {
             Map<String,? extends DataSet> preparedDataset =   prepareDataSetForClustering();
             Map<String,DataCluster> result =  kmeansIterations(preparedDataset,0);
             if (clusteringResultListener != null) {
                 clusteringResultListener.onCompleted(dataSet,result,System.currentTimeMillis()-startTime);
             }
         } catch (Exception exception) {
             if(clusteringResultListener != null) {
                 clusteringResultListener.onFailure(exception);
             }
         }
     }

     @Override
    public void kMeans(ClusteringResultListener clusteringResultListener) {
        this.clusteringResultListener = clusteringResultListener;
        clusterThread = new Thread(this,"clusterThread");
         clusterThread.start();
    }

     /**
      * this is a recursive methods that returns the result sets after the algorithm converges.
      * the output of each iterations is passed as input the next one.
      * each iteration calculates the new centroids , refreshes the new cluster and checks convergence.
      * @param currentDataClusters
      * @param currentiteration
      * @return
      * @throws Exception
      */
    private Map<String,DataCluster> kmeansIterations(Map<String,? extends  DataSet> currentDataClusters,int currentiteration) throws Exception {
        boolean isStable = true;
        Map<String,DataCluster> newDataClusters = new TreeMap<String, DataCluster>();
        for(Map.Entry<String,? extends DataSet> entry : currentDataClusters.entrySet()) {
        DataCluster cluster = (DataCluster) entry.getValue();
        int dim = cluster.getCentroid().getComponents().size();
        DataPoint newcentroid = MLmathTools.averageDataPoint(cluster.getDataPoints(),cluster.getLabel()+"-"+currentiteration);

        cluster.setCentroid(newcentroid);
        newDataClusters.put(cluster.getLabel(),cluster);

        }

        List<DataCluster>  oldClusters = new ArrayList<DataCluster>();

        currentDataClusters.forEach( (key,value) -> {
            oldClusters.add((DataCluster)value);
        });
        List<DataCluster>  newClusters = new ArrayList<DataCluster>();
        newDataClusters.forEach( (key,value) -> {
            newClusters.add((DataCluster)value);
        });

        for (int i=0; i<oldClusters.size() ; i++) {
            boolean b = isStable(oldClusters.get(i),newClusters.get(i));
            if (!b) {
                isStable = false;
            }
        }

        if (isStable) {
            return(newDataClusters);
        } else {
            currentiteration++;
            return(kmeansIterations(newDataClusters,currentiteration));
        }

    }

     /**
      * this methods checks wether the algorithms converges or not
      * convergence is achieved when the old dataset matches the new one.
      * @param oldcluster
      * @param newcluster
      * @return
      */
    public boolean  isStable(DataCluster oldcluster , DataCluster newcluster) {
        if (oldcluster.getDataPoints().size() != newcluster.getDataPoints().size()) {
            return(false);
        } else {
          boolean areequal = true;
          for (int i=0; i<oldcluster.getDataPoints().size(); i++) {
              if (!oldcluster.getDataPoints().get(i).equals(newcluster.getDataPoints().get(i))) {
                  areequal = false;
                  break;
              }
          }
          return(areequal);
        }
    }

     /**
      * this method implements the kmeans++ algorithms which optimizes the kmeans iterations
      * the method is called before the iterations start, it costs more time complexity if used and reduces the eventual iterations taken by the kmeans algorithm.
      * @param clusters
      * @return
      * @throws Exception
      */
    private List<DataPoint> kmeansCentroidInitializer( int clusters) throws  Exception{
        List<DataPoint> centroids = new ArrayList<>();
        DataPoint firstCenter = new DataPoint(2,"c1");
        firstCenter.getComponents().add(1d);
        firstCenter.getComponents().add(8d);
        centroids.add(firstCenter);

        // first center selected
        if (clusters > 1) {
            for (int i = 0; i < clusters - 1; i++) {
                double squaredDistancesSum = 0;
                // calculate the square of the distance between each point and its nearest center
                for (DataPoint dp : dataSet.getDataPoints()) {
                    //1-calculating the sum of squares
                    double mind = MLmathTools.minDistance(centroids, dp);
                    double dsquare = Math.pow(mind, 20);
                    squaredDistancesSum += dsquare;
                }
                // for each point in dataset , we compute the probability that it will be chosen as the next centroid as long as there is still .
                double highestprobability = 0;
                DataPoint nextCentroid = null;
                for (DataPoint dp : dataSet.getDataPoints()) {
                    double mind = MLmathTools.minDistance(centroids, dp);
                    double probtoBeNextCenter = mind / squaredDistancesSum;
                    if (probtoBeNextCenter > highestprobability) {
                        highestprobability = probtoBeNextCenter;
                        nextCentroid = dp;
                    }
                }
                centroids.add(nextCentroid);
            }
        }
        return(centroids);

    }




}
