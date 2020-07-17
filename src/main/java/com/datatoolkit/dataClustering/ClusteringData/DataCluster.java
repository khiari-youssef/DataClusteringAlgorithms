package com.datatoolkit.dataClustering.ClusteringData;

public class DataCluster extends DataSet {

    private DataPoint centroid;

    public DataCluster(String label, DataPoint centroid) {
        super(label);
        this.centroid = centroid;
    }

    public DataPoint getCentroid() {
        return centroid;
    }

    public void setCentroid(DataPoint centroid) {
        this.centroid = centroid;
    }

    @Override
    public String toString() {
        return (super.toString());
    }
}
