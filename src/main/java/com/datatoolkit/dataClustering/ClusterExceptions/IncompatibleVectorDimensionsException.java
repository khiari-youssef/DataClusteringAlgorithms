package com.datatoolkit.dataClustering.ClusterExceptions;

public class IncompatibleVectorDimensionsException extends Exception {

    public IncompatibleVectorDimensionsException(int size1,int size2) {
        super("two vectors with dimensions " + size1 + " and " + size2 + " don't co-exist in a clusterable dataset");
    }
}
