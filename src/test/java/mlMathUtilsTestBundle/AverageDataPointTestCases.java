package mlMathUtilsTestBundle;

import DataIO.DataSource;
import com.datatoolkit.dataClustering.ClusteringData.DataPoint;
import com.datatoolkit.dataClustering.utils.MLmathTools;
import org.junit.Before;
import org.junit.Test;
import org.junit.gen5.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class AverageDataPointTestCases {

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
    public void AverageDatapointSuccessCase() {
        DataPoint dataPoint1 = new DataPoint(2, "c");
        dataPoint1.getComponents().add(3.852631578947368d);
        dataPoint1.getComponents().add(5.2894736842105265);
        boolean noError = true;
        DataPoint  avgdp = null;
        try {
        avgdp  = MLmathTools.averageDataPoint(dataPoints,"avgdp");
        } catch (Exception exception) {
            exception.printStackTrace();
            noError = false;
        } finally {
            Assertions.assertEquals(dataPoint1,avgdp);
        }
    }
    @Test
    public void AverageDatapointFailureCase() {
        DataPoint dataPoint1 = new DataPoint(2, "c");
        dataPoint1.getComponents().add(3.852631578947368d);
        dataPoint1.getComponents().add(5.2894736842105265);
        boolean noError = true;
        dataPoints.clear();
        DataPoint  avgdp = null;
        try {
            avgdp  = MLmathTools.averageDataPoint(dataPoints,"avgdp");
        } catch (Exception exception) {
            exception.printStackTrace();
            noError = false;
        } finally {
            Assertions.assertFalse(noError);
        }
    }



}
