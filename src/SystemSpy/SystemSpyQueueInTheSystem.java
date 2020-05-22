package SystemSpy;

import Car.Car;
import GlobalValues.Values;

import javax.swing.*;
import java.util.List;

/**
 * Class which allows to spy a specific queue.
 */
public class SystemSpyQueueInTheSystem implements Runnable {
    List<Car> carQueue;

    public SystemSpyQueueInTheSystem(List<Car> carQueue) {
        this.carQueue = carQueue;
    }
    @Override
    public void run() {
        // Table format for print out.
        String format = "|%1$-10s|%2$-10s|%3$-20s|\n";
        long timeTo = System.currentTimeMillis() + Values.carGeneratingTime;

        long time = System.currentTimeMillis();


        while(time <= timeTo+5000){
            synchronized (carQueue){
                if(!(carQueue.size() ==0))System.out.format(format, " Car ID", " Timestamp", " Station");
            }

            for (int i = 0; i < carQueue.size(); i++) {
                synchronized (carQueue){
                    System.out.format(format, carQueue.get(i).getId(),time - Long.valueOf(carQueue.get(i).getTimeStamp()), carQueue.get(i).getCurrentStation());}
            }
            if(!(carQueue.size() ==0)) System.out.println("--------------------------------------------");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time = System.currentTimeMillis();
        }
    }


}
