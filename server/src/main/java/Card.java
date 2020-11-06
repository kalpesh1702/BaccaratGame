import java.io.Serializable;

public class Card implements Serializable {
    String suite;
    int value;

    // constructor
    Card(String theSuite, int theValue){
        suite = theSuite;
        value = theValue;
    }

}
