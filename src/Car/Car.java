package Car;

import GlobalValues.Values;

import java.util.Random;

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


    @Override
    public int compareTo(Car o) {
        if(Values.queueDiscipline.equals(Values.queueDisciplineFIFO) || Values.queueDiscipline.equals(Values.queueDisciplineLIFO))
            return this.timeStamp == o.timeStamp ? 0 : this.timeStamp == null ? -1 : o.timeStamp == null ? 1 : this.timeStamp.compareTo(o.timeStamp);
        else
            return this.processingTime == o.processingTime ? 0: this.processingTime == null ? -1 : o.processingTime == null ? 1 : this.processingTime.compareTo(o.processingTime);
    }

    public void setNumberOfPessengers(){
        int max = 3;
        int min = 1;
        int range = max - min +1;
        this.numberOfPassengers = (int) (Math.random()*range)+min;
    }

    public void setProcessingTime(){
        int amountOfPassengers = getNumberOfPassengers();
        long processingTime = Values.testForCovid * amountOfPassengers;
        this.processingTime = String.valueOf(processingTime);

    }
    public String getProcessingTime() {
        return processingTime;
    }
    public int getNumberOfPassengers() {
        return this.numberOfPassengers;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    public long getStartWaiting() {
        return startWaiting;
    }

    public void setStartWaiting(long startWaiting) {
        this.startWaiting = startWaiting;
    }

    public long getStopsWaiting() {
        return stopsWaiting;
    }

    public void setStopsWaiting(long stopsWaiting) {
        this.stopsWaiting = stopsWaiting;
    }

    public  int getId() {
        return id;
    }


}
