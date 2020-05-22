package GlobalValues;

import Car.Car;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Values {

   public static List<Car> carTimesAfterLeavingList = Collections.synchronizedList(new ArrayList<Car>());

    public static long arrivingCarMax = 180;
    public static long arrivingCarMin = 120;


    public static long testForCovid = 120;
    public static int carQueueSize = 10;
    public static int carGeneratingTime = 7200;

    public static AtomicBoolean isTimeOver = new AtomicBoolean(false);

    public static String queueDiscipline = "LPT";
    public static String queueDisciplineFIFO = "FIFO";
    public static String queueDisciplineLIFO = "LIFO";
    public static String queueDisciplineSPT = "SPT";
    public static String queueDisciplineLPT = "LPT";


    public static long calculateTimeDistributionForArrivingAtTheCarStation() {
        UniformRealDistribution u = new UniformRealDistribution(arrivingCarMin,arrivingCarMax);
        return (long) u.sample();
    }


    public synchronized static void sorter(List<Car> carsList) {
        Collections.sort(carsList,Collections.reverseOrder());
    }

}
