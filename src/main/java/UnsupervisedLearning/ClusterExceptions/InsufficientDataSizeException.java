package UnsupervisedLearning.ClusterExceptions;

public class InsufficientDataSizeException  extends  Exception{

    public InsufficientDataSizeException(int size) {
        super("dataset is expected to have at least 2 data points, " + size + " data point(s) found" );
    }
}
