package mlMathUtilsTestBundle;

import com.datatoolkit.dataClustering.ClusterExceptions.IncompatibleVectorDimensionsException;
import com.datatoolkit.dataClustering.ClusteringData.DataPoint;
import com.datatoolkit.dataClustering.utils.MLmathTools;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.gen5.api.Assertions;

public class EuclidianDistanceTestCases {


public final DataPoint dataPoint1 = new DataPoint(2,"test1dp1");

public final DataPoint dataPoint2 = new DataPoint(2,"test1dp2");



@Before
public void initDatapoints() {
    dataPoint1.getComponents().add(5d);
    dataPoint1.getComponents().add(0d);
    dataPoint2.getComponents().add(1d);
    dataPoint2.getComponents().add(2d);
}


@Test
public void EuclidianDistanceSuccessCase() {
    double distance = -1;
    try {
      distance = MLmathTools.euclidianDistance(dataPoint1,dataPoint2);
    } catch (Exception exception) {
        exception.printStackTrace();
    } finally {
        Assertions.assertTrue( distance != -1 && distance == Math.sqrt(20));
    }
}

@Test
public void EuclidianDistanceFailureCase() {

    double distance = -1;
    dataPoint1.getComponents().add(0d);
    boolean excpetionoccured = false;
    try {
        distance = MLmathTools.euclidianDistance(dataPoint1,dataPoint2);
    } catch (Exception exception) {
        exception.printStackTrace();
        excpetionoccured = exception instanceof  IncompatibleVectorDimensionsException;
    } finally {
        Assertions.assertTrue( distance == -1 && excpetionoccured);
    }
}


}
