import Car.Car;
import GeneratingCars.GeneratingCarsWithMultipleQueues;
import GlobalValues.Values;
import SystemSpy.SystemSpyQueueInTheSystem;
import Test.TestingPhase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationB {
    public static void main(String[] args) throws InterruptedException {
        List<Car> carWaitingQueue1 = Collections.synchronizedList(new ArrayList<Car>());
        List<Car> carWaitingQueue2 = Collections.synchronizedList(new ArrayList<Car>());
        List<Car> carWaitingQueue3 = Collections.synchronizedList(new ArrayList<Car>());

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
        Values.arrivingCarMax =180;
        // Min arriving car time for generating.
        Values.arrivingCarMin = 120;

        Thread generatingCars =  new Thread(new GeneratingCarsWithMultipleQueues(carWaitingQueue1,carWaitingQueue2,carWaitingQueue3),"Generating Car Thread");
        generatingCars.start();

        Thread testingStation1 = new Thread(new TestingPhase(carWaitingQueue1,currentlyInTheSystemQueue,1),"Test Station 1");
        testingStation1.start();

        Thread testingStation2 = new Thread(new TestingPhase(carWaitingQueue2,currentlyInTheSystemQueue,2),"Test Station 2");
        testingStation2.start();

        Thread testingStation3 = new Thread(new TestingPhase(carWaitingQueue3,currentlyInTheSystemQueue,3),"Test Station 3");
        testingStation3.start();


        Thread systemSpyForSystemQueue = new Thread(new SystemSpyQueueInTheSystem(currentlyInTheSystemQueue)," System Spy");
        systemSpyForSystemQueue.start();


        testingStation2.join();
        testingStation1.join();
        testingStation3.join();

        System.out.println("Persons in a car: "+Values.personsOverallInAllTheCars);
        System.out.println("Cars generated: "+Values.carsGeneratedOverall);
        System.out.println("Cars left due to the queue: "+Values.carLeftLaneSinceItIsFull);

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
