package UnsupervisedLearning;

public class ClusterManager {

 private static final ClusterManager sharedInstance  = new ClusterManager();

 private  ClusterManager(){

 }

    /**
     * returns a single intance of ClusterManager class
     * @return
     */
 public  static  ClusterManager getInstance() {
     return(sharedInstance);
 }

 public ClusterBuilder getBuilder() {
     return(new ClusterBuilder());
 }


}
