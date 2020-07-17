import UnsupervisedLearning.ClusterAlgorithm;
import UnsupervisedLearning.ClusterManager;
import UnsupervisedLearning.ClusteringData.DataPoint;
import UnsupervisedLearning.ClusteringData.DataSet;
import UnsupervisedLearning.KmeansClusterable;
import UnsupervisedLearning.Listeners.ClusteringResultListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainClass {

    public static void main(String[] args) {

        // this is an example using simple data points.

        // create a list of datapoints
        List<DataPoint> dataPoints = new ArrayList<>();

         // creating new DataPoints
        DataPoint a1 = new DataPoint(2,"a1");
        DataPoint a2 = new DataPoint(2,"a2");
        DataPoint a3 = new DataPoint(2,"a3");
        DataPoint a4 = new DataPoint(2,"a4");
        DataPoint a5 = new DataPoint(2,"a5");
        DataPoint a6 = new DataPoint(2,"a6");
        DataPoint a7 = new DataPoint(2,"a7");
        DataPoint a8 = new DataPoint(2,"a8");

        // adding (x,y) components to each datapoint

        a6.getComponents().add(5d);
        a6.getComponents().add(2d);
        a1.getComponents().add(1d);
        a1.getComponents().add(3d);
        a2.getComponents().add(18d);
        a2.getComponents().add(0d);
        a3.getComponents().add(4d);
        a3.getComponents().add(3d);
        a4.getComponents().add(6d);
        a4.getComponents().add(6d);
        a5.getComponents().add(16d);
        a5.getComponents().add(4d);
        a7.getComponents().add(0d);
        a7.getComponents().add(0d);
        a8.getComponents().add(2.5d);
        a8.getComponents().add(10d);

        // adding datapoints to the list

        dataPoints.add(a1);
        dataPoints.add(a2);
        dataPoints.add(a3);
        dataPoints.add(a4);
        dataPoints.add(a5);
        dataPoints.add(a6);
        dataPoints.add(a7);
        dataPoints.add(a8);

       // create a clusterable dataset using the builder object

        KmeansClusterable kmeansClusterable = ClusterManager.getInstance().getBuilder()
                .withLabel("mycluster")
                .withOptimzedInit(true)
                .withDataSet(dataPoints)
                .withAlgorithm(ClusterAlgorithm.Kmeans)
                .withOutputClusters(4)
                .build();

        // launch the clustering task and fetch the result in the callback interface functions

       kmeansClusterable.kMeans(new ClusteringResultListener() {
           @Override
           public void onCompleted(DataSet initialDataSet, Map<String, ? extends DataSet> result, long duration) {
               System.out.println(initialDataSet.getLabel() + " clustering operation completed in " + duration + "ms");
               for (Map.Entry<String,? extends  DataSet> entry : result.entrySet()) {
                   System.out.println("*********************************");
                   System.out.println("dataset : "+ entry.getValue().getLabel());
                   entry.getValue().getDataPoints().forEach(System.out::println);
                   System.out.println("*********************************");
               }
           }

           @Override
           public void onFailure(Exception exception) {
                   exception.printStackTrace();
           }
       });

       // using customized data points



    }

}
