import Car.Car;
import GeneratingCars.GeneratingCars;
import GlobalValues.Values;
import SystemSpy.SystemSpyQueueInTheSystem;
import Test.TestingPhase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulation {

    public static void main(String[] args) throws InterruptedException {
        List<Car> carWaitingQueue = Collections.synchronizedList(new ArrayList<Car>());
        List<Car> currentlyInTheSystemQueue = Collections.synchronizedList(new ArrayList<Car>());
        /**
         * Comment below values out to get the default values.
         */
        // FIFO LIFO SPT LPT are the possible changes.
        Values.queueDiscipline = "FIFO";
        // Timer for how long a test should be.
        Values.testForCovid = 120;
        // Time for generating cars in seconds. Two hours default  = 7200.
        Values.carGeneratingTime = 7200;
        // For changing the car queue size.
        Values.carQueueSize =10;
        // Max arriving car time for generating.
        Values.arrivingCarMax =60;
        // Min arriving car time for generating.
        Values.arrivingCarMin = 10;

        Thread generatingCars =  new Thread(new GeneratingCars(carWaitingQueue),"Generating Car Thread");
        generatingCars.start();

        Thread testingStation1 = new Thread(new TestingPhase(carWaitingQueue,currentlyInTheSystemQueue,1),"Test Station 1");
        testingStation1.start();

        Thread testingStation2 = new Thread(new TestingPhase(carWaitingQueue,currentlyInTheSystemQueue,2),"Test Station 2");
        testingStation2.start();

        Thread testingStation3 = new Thread(new TestingPhase(carWaitingQueue,currentlyInTheSystemQueue,3),"Test Station 3");
        testingStation3.start();

        Thread systemSpyForSystemQueue = new Thread(new SystemSpyQueueInTheSystem(currentlyInTheSystemQueue)," System Spy");
        systemSpyForSystemQueue.start();

        testingStation3.join();
        testingStation2.join();
        testingStation1.join();

        System.out.println("Persons in a car: "+GeneratingCars.personsOverallInAllTheCars);
        System.out.println("Cars generated: "+GeneratingCars.carsGeneratedOverall);
        System.out.println("Cars left due to the queue: "+GeneratingCars.carLeftLaneSinceItIsFull);

        System.out.println("Cars left after getting tested: "+ TestingPhase.carLeftQueueAfterGettingTested);
        System.out.println("Persons in car which were tested: "+ TestingPhase.amountOfPersonsWhichWereTested);
        double time = 0.0;
        double individualTimeWainting = 0.0;

        for(int i = 0; i < Values.carTimesAfterLeavingList.size(); i++) {
            time += Values.carTimesAfterLeavingList.get(i).getStopsWaiting() - Values.carTimesAfterLeavingList.get(i).getStartWaiting();
            individualTimeWainting = Values.carTimesAfterLeavingList.get(i).getStopsWaiting() - Values.carTimesAfterLeavingList.get(i).getStartWaiting();
           // System.out.println("Car with id: "+Values.carTimesAfterLeavingList.get(i).getId() +" was waiting "+(individualTimeWainting)+" s");
        }


        System.out.println("-------------------------------------------------------------------");
        System.out.println("Average waiting time per car: "+  (time/(double)Values.carTimesAfterLeavingList.size()) + " s");
    }
}
