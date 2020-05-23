package GeneratingCars;

import Car.Car;
import GlobalValues.Values;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratingCarsWithMultipleQueues implements Runnable{

    private List<Car> carWaitingQueue1 = null;
    private List<Car> carWaitingQueue2 = null;
    private List<Car> carWaitingQueue3 = null;



    public GeneratingCarsWithMultipleQueues(List<Car> carWaitingQueue1,List<Car> carWaitingQueue2,List<Car> carWaitingQueue3) {
        this.carWaitingQueue1 = carWaitingQueue1;
        this.carWaitingQueue2 = carWaitingQueue2;
        this.carWaitingQueue3 = carWaitingQueue3;
    }

    public List<Car> getSmallestQueueToPlaceCar() {
        if(carWaitingQueue1.size() <= carWaitingQueue2.size() && carWaitingQueue1.size() <= carWaitingQueue3.size()){
            return carWaitingQueue1;
        }else if (carWaitingQueue2.size() <= carWaitingQueue1.size() && carWaitingQueue2.size() <= carWaitingQueue3.size()){
            return carWaitingQueue2;
        }
        return carWaitingQueue3;
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
            if(getSmallestQueueToPlaceCar().size() >= Values.carQueueSize) {
                // System.out.println("0. Car needs to leave the queue is already full: "+newArrivingCar.getId());
                Values.carLeftLaneSinceItIsFull.set(Values.carLeftLaneSinceItIsFull.get()+1);
            }else{
                newArrivingCar.setCurrentStation("Arrives Test Station");
                newArrivingCar.setStartWaiting(System.currentTimeMillis());

                    getSmallestQueueToPlaceCar().add(newArrivingCar);

            }
            timeStarted = System.currentTimeMillis();

        }
        while(carWaitingQueue1.size()!= 0 && carWaitingQueue2.size()!= 0 && carWaitingQueue3.size()!= 0){}
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
