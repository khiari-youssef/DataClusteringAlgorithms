package UnsupervisedLearning.utils;

import UnsupervisedLearning.ClusterExceptions.IncompatibleVectorDimensionsException;
import UnsupervisedLearning.ClusterExceptions.InsufficientDataSizeException;
import UnsupervisedLearning.ClusteringData.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class MLmathTools {

    private MLmathTools() {
    }


    /**
     * Calculates and returns a datapoint whose coordinates are the average the datapoins'coordinates in the dataset
     * returns  null when dataset is empty
     * @param dataset
     * @param label
     * @return
     * @throws NullPointerException
     */
    public static DataPoint averageDataPoint(List<DataPoint> dataset, String label) throws Exception {


        if (dataset.size() == 0 ) {
            throw  new InsufficientDataSizeException(dataset.size());
        } else {
            double sum = 0.0;
            double cardinal = dataset.size();
            int dim = dataset.get(0).getComponents().size();
            DataPoint dp = new DataPoint(dim,label);
            for (int i=0; i<dim; i++) {

                for (DataPoint d : dataset) {
                    sum+= (d.getComponents().get(i)/cardinal);
                }
                dp.getComponents().add(sum);
                sum = 0d;
            }

            return (dp);
        }

    }



    /**
     * calculates and returns the euclidian Distance between two datapoints dp1,dp2 with equal dimensions.
     * @param dp1
     * @param dp2
     * @return
     */
    public static  double euclidianDistance(DataPoint dp1 , DataPoint dp2)  throws Exception{
        double distance = 0d;
        if (dp1.getComponents().size() != dp2.getComponents().size()) {
     throw new IncompatibleVectorDimensionsException(dp1.getComponents().size(),dp2.getComponents().size() );
        } else {
            for (int i=0; i<dp1.getComponents().size(); i++) {
                distance+= Math.pow(dp1.getComponents().get(i)-dp2.getComponents().get(i),2);
            }
        }
        return(Math.sqrt(distance));
    }


    /**
     *
     * @param p1
     * @param ds
     * @param mindistance
     * @return
     */
     public static  DataPoint findMatchingDataPointByDistance(DataPoint p1, List<DataPoint> ds , double mindistance) throws Exception {
            for (DataPoint dp: ds) {
                if (MLmathTools.euclidianDistance(dp,p1) == mindistance) {
                    return (dp);
                }
            }
            return (null);
     }

    /**
     * calculates and returns the minimum distance between a datapoint and  centroids in the list.
     * @param centroids
     * @param dataPoint
     * @return
     */
     public static double  minDistance(List<DataPoint> centroids , DataPoint dataPoint) throws Exception {
        int n = centroids.size();
        if (n == 1) {
             double d = euclidianDistance(centroids.get(0),dataPoint);
             return(d);
        } else {
            ArrayList<DataPoint> firsthalf = new ArrayList<DataPoint>(n/2);
            ArrayList<DataPoint> secondhalf = new ArrayList<DataPoint>(n/2);
            for (int i=0; i<n/2; i++) {
                firsthalf.add(centroids.get(i));
            }
            for (int i=(n/2); i<n; i++) {
                secondhalf.add(centroids.get(i));
            }
            return(Math.min(minDistance(firsthalf,dataPoint),minDistance(secondhalf,dataPoint)));
        }
     }

     public static DataPoint translatePoint(DataPoint dataPoint, List<Double> translationVector) {

        if (translationVector.size() != dataPoint.getComponents().size()) {
            return (null);
        } else {
            DataPoint newdp = new DataPoint(dataPoint.getComponents().size(),dataPoint.getLabel()+"'");
            for (int i =0; i<dataPoint.getComponents().size(); i++) {
                 newdp.getComponents().add(dataPoint.getComponents().get(i)+translationVector.get(i));
            }
            return(newdp);
        }
     }







}
