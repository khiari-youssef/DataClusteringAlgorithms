package mlMathUtilsTestBundle;

import DataIO.DataSource;
import com.datatoolkit.dataClustering.ClusteringData.DataPoint;
import com.datatoolkit.dataClustering.utils.MLmathTools;
import org.junit.Before;
import org.junit.Test;
import org.junit.gen5.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class MinDistanceTestCases {


    private List<DataPoint> dataPoints = new ArrayList<>();

    @Before
    public void initTestData() {
        try {
            dataPoints = DataSource.fetchDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void minDistance_test_Success_Case1() {
        DataPoint dataPoint = new DataPoint(2, "c");
        dataPoint.getComponents().add(10d);
        dataPoint.getComponents().add(1d);
        boolean noError = true;
        try {
            double distance = MLmathTools.minDistance(dataPoints, dataPoint);
            Assertions.assertEquals(distance, Math.sqrt(4 + Math.pow(1.5, 2)));
        } catch (Exception exception) {
            exception.printStackTrace();
            noError = false;
        } finally {
            Assertions.assertTrue(noError);
        }
    }

    @Test
    public void minDistance_test_Success_Case2() {
        DataPoint dataPoint = new DataPoint(2, "c");
        dataPoint.getComponents().add(4d);
        dataPoint.getComponents().add(7d);
        boolean noError = true;
        try {
            double distance = MLmathTools.minDistance(dataPoints, dataPoint);
            Assertions.assertEquals(distance, 0.5 * Math.sqrt(2));
        } catch (Exception exception) {
            exception.printStackTrace();
            noError = false;
        } finally {
            Assertions.assertTrue(noError);
        }
    }

    @Test
    public void minDistance_test_Success_Case3() {
        DataPoint dataPoint = new DataPoint(2, "c");
        dataPoint.getComponents().add(4d);
        dataPoint.getComponents().add(7d);
        boolean noError = true;
        try {
            dataPoints.clear();
            dataPoints.add(dataPoint);
            double distance = MLmathTools.minDistance(dataPoints, dataPoint);
            Assertions.assertEquals(distance, 0d);
        } catch (Exception exception) {
            exception.printStackTrace();
            noError = false;
        } finally {
            Assertions.assertTrue(noError);
        }
    }

    @Test
    public void minDistance_test_Failure() {
        DataPoint dataPoint = new DataPoint(2, "c");
        dataPoint.getComponents().add(4d);
        dataPoint.getComponents().add(7d);
        boolean noError = true;
        try {
            dataPoints.clear();
            double distance = MLmathTools.minDistance(dataPoints, dataPoint);
            Assertions.assertEquals(distance, 0d);
        } catch (Exception exception) {
            exception.printStackTrace();
            noError = false;
        } finally {
            Assertions.assertFalse(noError);
        }
    }


}
