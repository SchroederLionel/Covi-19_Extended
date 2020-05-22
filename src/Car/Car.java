package Car;

import GlobalValues.Values;

import java.util.Random;

/**
 * Car class.
 */
public class Car implements Comparable<Car> {
    private static int starterID = 0;
    private  int id = 0;
    private int numberOfPassengers;
    private boolean hasTestNotification;
    private String timeStamp;
    private String currentStation;
    private long startWaiting;
    private long stopsWaiting;
    private String processingTime;


    public Car(Random random) {
       starterID++;
       this.id  = starterID;
       hasTestNotification = random.nextBoolean();
       timeStamp = String.valueOf(System.currentTimeMillis());
       setNumberOfPessengers();
       setProcessingTime();
    }

    /**
     * Comparator
     * @param  o is car which is used to compared with.
     * @return depending on the situation 0 or 1 with the build in compareTo.
     */
    @Override
    public int compareTo(Car o) {
        if(Values.queueDiscipline.equals(Values.queueDisciplineFIFO) || Values.queueDiscipline.equals(Values.queueDisciplineLIFO))
            return this.timeStamp == o.timeStamp ? 0 : this.timeStamp == null ? -1 : o.timeStamp == null ? 1 : this.timeStamp.compareTo(o.timeStamp);
        else
            return this.processingTime == o.processingTime ? 0: this.processingTime == null ? -1 : o.processingTime == null ? 1 : this.processingTime.compareTo(o.processingTime);
    }

    /**
     * Sets the amount of passengers.
     * Random value between 1 and 3 included.
     */
    public void setNumberOfPessengers(){
        int max = 3;
        int min = 1;
        int range = max - min +1;
        this.numberOfPassengers = (int) (Math.random()*range)+min;
    }

    /**
     * Function which allows to set the processing time.
     * The processing time is composed of the number of passengers multiplied with the tesing time.
     */
    public void setProcessingTime(){
        int amountOfPassengers = getNumberOfPassengers();
        long processingTime = Values.testForCovid * amountOfPassengers;
        this.processingTime = String.valueOf(processingTime);

    }

    /**
     * Function which allows to return the processing time.
     * @return the processing time.
     */
    public String getProcessingTime() {
        return processingTime;
    }

    /**
     * Function which allows to return the number of passengers.
     * @return passengers in a car. Between 1 and 3 included.
     */
    public int getNumberOfPassengers() {
        return this.numberOfPassengers;
    }

    /**
     * Function which allows to return the Time Stamp of a car.
     * @return Timestamp at the creation level of the car.
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Function which allows to return the current Test station. Between 1 and 3 included.
     * @return String of the current Station e.x.( Test Station 1). Where 1 is a changable value.
     */
    public String getCurrentStation() {
        return currentStation;
    }

    /**
     * Function which allows to set the current station of a car.
     * @param currentStation
     */
    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    /**
     * Function which return the start waiting time of the car. (As soon as the car is generated the time stamp is created).
     * @return starting time stamp.
     */
    public long getStartWaiting() {
        return startWaiting;
    }

    /**
     * Function which allows to set the start waiting time. This value is set as soon as the car enters the queue.
     * @param startWaiting
     */
    public void setStartWaiting(long startWaiting) {
        this.startWaiting = startWaiting;
    }

    /**
     * Function which allows to get the Stop waiting time.
     * The stop waiting time is set as soon as the car leaves the waiting queue and is ready to be tested.
     * @return stopswaiting Time Stamp
     */
    public long getStopsWaiting() {
        return stopsWaiting;
    }

    /**
     * Function which allows to set the stop waiting time.
     * The stop waiting time is set as soon as the car leaves the waiting queue and is ready to be tested.
     * @param stopsWaiting
     */
    public void setStopsWaiting(long stopsWaiting) {
        this.stopsWaiting = stopsWaiting;
    }

    /**
     * Function which allows to get the unique identifier of the car.
     * The id gets incremented for every car generated starting with 1.
     * @return int id.
     */
    public  int getId() {
        return id;
    }


}
