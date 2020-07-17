package UnsupervisedLearning.ClusteringData;

import java.util.ArrayList;

public class DataSet {

 private String label = "Set E";
 private ArrayList<DataPoint> dataPoints;

    public DataSet(String label) {
        this.label = label;
        this.dataPoints = new ArrayList<DataPoint>();
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<DataPoint> getDataPoints() {
        return dataPoints;
    }



    @Override
    public String toString() {
       String datapoints = "";
       for (int i=0; i<this.dataPoints.size(); i++) {
           if (i == this.dataPoints.size()-1) {
               datapoints =  (datapoints +""+this.dataPoints.get(i)+"");
           } else {
               datapoints =  (datapoints +""+(this.dataPoints.get(i))+" , ");
           }
       }
         String whenisCluster = (this instanceof DataCluster) ? "\n centroid : "+((DataCluster)this).getCentroid().toString():"";
        return("--------"+this.label+"--------\n"+"Datapoints = { "+datapoints+" }  "+whenisCluster+" \n-----------------------");
    }
}
