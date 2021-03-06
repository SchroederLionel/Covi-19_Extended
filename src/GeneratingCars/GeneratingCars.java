package GeneratingCars;

import Car.Car;
import GlobalValues.Values;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratingCars implements Runnable {

    private List<Car> carWaitingQueue = null;
    // Value used to count the cars which left the queue due to a full queue.


    public GeneratingCars(List<Car> carWaitingQueue) {
        this.carWaitingQueue = carWaitingQueue;
    }

    /**
     * Function which allows to generate some cars.
     * @throws InterruptedException due to the sleep of the car wiating to creaate an other car.
     */
    public void createSomeCars() throws InterruptedException {
        long timeStarted = System.currentTimeMillis();
        long timeAfterTwoHours = timeStarted + Values.carGeneratingTime;
        Random random = new Random();
        while (timeStarted <= timeAfterTwoHours) {
            Thread.sleep(Values.calculateTimeDistributionForArrivingAtTheCarStation());
            Car newArrivingCar = new Car(random);
            Values.personsOverallInAllTheCars.set(Values.personsOverallInAllTheCars.get()+newArrivingCar.getNumberOfPassengers());
            Values.carsGeneratedOverall.set(Values.carsGeneratedOverall.get()+1);
            if(carWaitingQueue.size() >= Values.carQueueSize) {
                // System.out.println("0. Car needs to leave the queue is already full: "+newArrivingCar.getId());
                Values.carLeftLaneSinceItIsFull.set(Values.carLeftLaneSinceItIsFull.get()+1);
            }else{
                newArrivingCar.setCurrentStation("Arrives Test Station");
                newArrivingCar.setStartWaiting(System.currentTimeMillis());
                synchronized (carWaitingQueue){
                    carWaitingQueue.add(newArrivingCar);
                }
            }
            timeStarted = System.currentTimeMillis();
        }
        while(carWaitingQueue.size()!= 0){}
        Values.isTimeOver.set(true);
    }

    @Override
    public void run() {
        try {
            createSomeCars();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
