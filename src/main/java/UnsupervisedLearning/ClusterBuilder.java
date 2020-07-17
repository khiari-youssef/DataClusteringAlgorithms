package UnsupervisedLearning;

import UnsupervisedLearning.ClusteringData.DataPoint;
import UnsupervisedLearning.ClusteringData.DataSet;

import java.util.ArrayList;
import java.util.List;

public final class ClusterBuilder {

    private List<DataPoint> dataPoints;
    private List<DataPoint> centroids;
    private boolean optimzeInit = false;
    private String label;
    private int clusters = 2;
    private ClusterAlgorithm clusterAlgorithm = ClusterAlgorithm.Kmeans;



    public ClusterBuilder() {
        dataPoints = new ArrayList<>();
        centroids = new ArrayList<>();
        label = "Set E";
    }



    public ClusterBuilder withOutputClusters(int number) {
        this.clusters = Math.max(number, 2);
        return(this);
    }

    public ClusterBuilder withLabel(String label) {
        this.label = label;
        return(this);
    }




    public ClusterBuilder withDataSet(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
        return(this);
    }


    public ClusterBuilder withOptimzedInit(boolean withOptimizedInit) {
        this.optimzeInit = true;
        return(this);
    }

    public ClusterBuilder withAlgorithm(ClusterAlgorithm clusterAlgorithm) {
        this.clusterAlgorithm = clusterAlgorithm;
        return(this);
    }

    public ClusterBuilder withInitialCentroids(List<DataPoint> centroids) {
        this.centroids = centroids;
        return(this);
    }

    public KmeansClusterable build() {
        DataSet dataSet = new DataSet(this.label);
        try {
            dataSet.getDataPoints().addAll(this.dataPoints);
            return (new Kmeans(dataSet, optimzeInit, clusters));
        } catch (Exception exception) {
            return(new Kmeans(null,false,0));
        }
    }












}
