package GlobalValues;

import Car.Car;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class which is used to have a common place for shared values.
 */
public class Values {

   public static List<Car> carTimesAfterLeavingList = Collections.synchronizedList(new ArrayList<Car>());
    // Arriving car  time used for generating cars.
    public static long arrivingCarMax = 180;
    public static long arrivingCarMin = 120;


    public static long testForCovid = 120;
    public static int carQueueSize = 10;
    public static int carGeneratingTime = 7200;
    // Value used to notify if the time of generating car is over. As soon as the the timer hits 2hours this value is changed.
    public static AtomicBoolean isTimeOver = new AtomicBoolean(false);

    // Queue Discipline values.
    public static String queueDiscipline = "LPT";
    public static String queueDisciplineFIFO = "FIFO";
    public static String queueDisciplineLIFO = "LIFO";
    public static String queueDisciplineSPT = "SPT";
    public static String queueDisciplineLPT = "LPT";

 // Value used to count the cars which left the queue due to a full queue.
    public static AtomicInteger personsOverallInAllTheCars = new AtomicInteger(0);
 // Stores the value of all the cars created.
    public static AtomicInteger carsGeneratedOverall = new AtomicInteger(0);
 // Value used to count the cars which left the queue due to a full queue.
    public static AtomicInteger carLeftLaneSinceItIsFull = new AtomicInteger(0);


    /**
     * Function which allows to calculate an equal distribution between two bounded values. Used For generating cars in a time interval of 1 to 3 minutes.
     * @return
     */
    public static long calculateTimeDistributionForArrivingAtTheCarStation() {
        UniformRealDistribution u = new UniformRealDistribution(arrivingCarMin,arrivingCarMax);
        return (long) u.sample();
    }

    /**
     * Function which allows to sort an array in reverse order.
     * @param carsList list which needs to be sorted.
     */
    public synchronized static void sorter(List<Car> carsList) {
        Collections.sort(carsList,Collections.reverseOrder());
    }

}
