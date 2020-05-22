package GeneratingCars;

import Car.Car;
import GlobalValues.Values;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratingCars implements Runnable {

    private List<Car> carWaitingQueue = null;

    public static AtomicInteger carLeftLaneSinceItIsFull = new AtomicInteger(0);

    public static AtomicInteger personsOverallInAllTheCars = new AtomicInteger(0);
    public static AtomicInteger carsGeneratedOverall = new AtomicInteger(0);

    public GeneratingCars(List<Car> carWaitingQueue) {
        this.carWaitingQueue = carWaitingQueue;
    }

    public void createSomeCars() throws InterruptedException {
        long timeStarted = System.currentTimeMillis();
        long timeAfterTwoHours = timeStarted + Values.carGeneratingTime;
        Random random = new Random();
        while (timeStarted <= timeAfterTwoHours) {
            Thread.sleep(Values.calculateTimeDistributionForArrivingAtTheCarStation());
            Car newArrivingCar = new Car(random);
            personsOverallInAllTheCars.set(personsOverallInAllTheCars.get()+newArrivingCar.getNumberOfPassengers());
            carsGeneratedOverall.set(carsGeneratedOverall.get()+1);
            if(carWaitingQueue.size() >= Values.carQueueSize) {
                // System.out.println("0. Car needs to leave the queue is already full: "+newArrivingCar.getId());
                carLeftLaneSinceItIsFull.set(carLeftLaneSinceItIsFull.get()+1);
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
