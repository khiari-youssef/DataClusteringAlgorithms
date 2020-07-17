package UnsupervisedLearning;

import UnsupervisedLearning.ClusteringData.DataPoint;

public class Product  {

    private String id;
    private String label;
    private double price;
    private double discount;
    private String description;

    public Product(
                   String id,
                   String label1,
                   double price,
                   double discount,
                   String description) {
        this.id = id;
        this.label = label1;
        this.price = price;
        this.discount = discount;
        this.description = description;
    }


}
