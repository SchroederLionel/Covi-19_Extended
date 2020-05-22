package Test;

import Car.Car;
import GlobalValues.Values;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class TestingPhase implements Runnable {

    private int id;
    private List<Car> carWaitingQueue;
    private List<Car> currentlyInTheSystemQueue;
    public static AtomicInteger carLeftQueueAfterGettingTested = new AtomicInteger(0);
    public static AtomicInteger amountOfPersonsWhichWereTested = new AtomicInteger(0);
    Car carRemovedFromQueue;

    public TestingPhase(List<Car> carWaitingQueue, List<Car> currentlyInTheSystemQueue,int id) {
      this.carWaitingQueue = carWaitingQueue;
      this.currentlyInTheSystemQueue = currentlyInTheSystemQueue;
      this.id = id;
    }

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



    private void removeFromCurrentlyInTheSystemQueue(){
        synchronized (currentlyInTheSystemQueue){
        currentlyInTheSystemQueue.remove(carRemovedFromQueue);}
    }


}
