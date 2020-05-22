package Test;

import Car.Car;
import GlobalValues.Values;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class which represents a Testing station.
 */
public class TestingPhase implements Runnable {
    // Unique identifer of the testing station ranges from one to three and each of them are unique.
    private int id;
    private List<Car> carWaitingQueue;
    private List<Car> currentlyInTheSystemQueue;
    // Variable which stores the count of cars leaving the queue/system.
    public static AtomicInteger carLeftQueueAfterGettingTested = new AtomicInteger(0);
    // Total sum of persons in each car during the simulation.
    public static AtomicInteger amountOfPersonsWhichWereTested = new AtomicInteger(0);
    Car carRemovedFromQueue;

    public TestingPhase(List<Car> carWaitingQueue, List<Car> currentlyInTheSystemQueue,int id) {
      this.carWaitingQueue = carWaitingQueue;
      this.currentlyInTheSystemQueue = currentlyInTheSystemQueue;
      this.id = id;
    }

    /**
     * Function which allows to remove a car from the queue. (Sync not realy required since the List it self is already syn.)
     */
    private  void removeCarFromWaitingQueue(){
        synchronized (carWaitingQueue){
            if(Values.queueDiscipline.contains("FIFO") || Values.queueDiscipline.contains(Values.queueDisciplineSPT) )
                carRemovedFromQueue = carWaitingQueue.remove(0);
            else  {
                Values.sorter(carWaitingQueue);
                carRemovedFromQueue = carWaitingQueue.remove(0);
            }
        }
    }

    /**
     * Function which allows to add a car to the system queue.
     */
    private void addToCurrentlyInTheSystemQueue(){
        synchronized(carWaitingQueue){
        currentlyInTheSystemQueue.add(carRemovedFromQueue);}
    }

    @Override
    public void run() {
        while(!Values.isTimeOver.get()){
            synchronized (carWaitingQueue) {
                if(carWaitingQueue.size() > 0){
                        removeCarFromWaitingQueue();
                }
            }
            if(carRemovedFromQueue != null ){
                carRemovedFromQueue.setCurrentStation("Testing Station: "+this.id);
                carRemovedFromQueue.setStopsWaiting(System.currentTimeMillis());
                addToCurrentlyInTheSystemQueue();
                try {
                    // System.out.println("Car is currently getting tested: "+carRemovedFromQueue.getId() +" At teststation:" +this.id);
                    Thread.sleep(Long.valueOf(carRemovedFromQueue.getProcessingTime()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                removeFromCurrentlyInTheSystemQueue();
                carLeftQueueAfterGettingTested.set(carLeftQueueAfterGettingTested.get()+1);
                amountOfPersonsWhichWereTested.set(amountOfPersonsWhichWereTested.get()+carRemovedFromQueue.getNumberOfPassengers());
                // System.out.println("Car left the Test station: "+ carRemovedFromQueue.getId());
                Values.carTimesAfterLeavingList.add(carRemovedFromQueue);
                carRemovedFromQueue = null;
            }

        }
    }


    /**
     * Function which allows to remove a car from the System queue.
     */
    private void removeFromCurrentlyInTheSystemQueue(){
        synchronized (currentlyInTheSystemQueue){
        currentlyInTheSystemQueue.remove(carRemovedFromQueue);}
    }


}
