package UnsupervisedLearning;


import UnsupervisedLearning.Listeners.ClusteringResultListener;



public interface KmeansClusterable {

   /**
    * Performs the k-means clustering algorithm and returns the result || error within a the callback parameter
    * @param clusteringResultListener
    */
   void kMeans(ClusteringResultListener clusteringResultListener);


}
