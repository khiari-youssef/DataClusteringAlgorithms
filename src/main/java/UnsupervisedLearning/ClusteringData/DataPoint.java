package UnsupervisedLearning.ClusteringData;

import java.util.ArrayList;
import java.util.List;

public final class DataPoint {

private List<Double>  components;
private String label;



    public DataPoint(int dimension,String label) {
        this.components = new ArrayList<Double>(dimension);
        this.label = label;
    }

    public List<Double> getComponents() {
        return components;
    }



    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        int i =0;
        String nuplets ="";
        for (i=0; i<components.size(); i++) {
            nuplets = (nuplets+""+components.get(i)+((components.size()-1 == i) ? "":","));
        }
        return (this.label+"("+nuplets+")");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  DataPoint) {
            DataPoint dp = (DataPoint)obj;
            if (dp.getComponents().size() == this.getComponents().size()) {
                boolean areequal = true;
                for (int i=0; i<dp.getComponents().size(); i++) {
                    if (!dp.getComponents().get(i).equals(this.getComponents().get(i))) {
                        areequal = false;
                        break;
                    }
                }
                return(areequal);
            } else {
                return (false);
            }
        } else {
            return super.equals(obj);
        }

    }


}
