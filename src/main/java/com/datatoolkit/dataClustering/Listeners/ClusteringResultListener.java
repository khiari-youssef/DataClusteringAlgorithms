package com.datatoolkit.dataClustering.Listeners;

import com.datatoolkit.dataClustering.ClusteringData.DataSet;

import java.util.Map;

public interface ClusteringResultListener {

    /**
     * this method is invoked when the clustering task is successfully completed and holds the result within the result parameter
     * this methods retruns the initial dataset (optional) within the initialDataSet parameter
     *  the duration parameter shows the algorithm execution time.
     * @param initialDataSet
     * @param result
     * @param duration
     */
    void  onCompleted(DataSet initialDataSet,Map<String,? extends DataSet> result,long duration);

    /**
     * when an error occurs within the clustering task , this method is invoked
     * returns the exception thrown by the task.
     * @param exception
     */
    void onFailure(Exception exception);
}
