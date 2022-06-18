package com.company;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class Race {
    final static int distance = 12000;
    static AtomicLong startRaceTime = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch cdl = new CountDownLatch(5);
        ArrayList<RaceCarRunnable> cars = new ArrayList<>();
        cars.add(new RaceCarRunnable("RedCar", 320, distance, cdl));
        cars.add(new RaceCarRunnable("WhiteCar", 280, distance, cdl));
        cars.add(new RaceCarRunnable("YellowCar", 315, distance, cdl));
        cars.add(new RaceCarRunnable("BlueCar", 275, distance, cdl));
        cars.add(new RaceCarRunnable("BlackCar", 325, distance, cdl));

        ArrayList<Thread> myThreads = new ArrayList<Thread>();
        for (RaceCarRunnable car : cars) {
            myThreads.add(new Thread(car));
        }


        startRace(myThreads);
        cdl.await();
        System.out.println("Race is over at time: " + convertToTime(System.currentTimeMillis()));
        for (RaceCarRunnable car : cars) {
            System.out.println(car.name + " FINISHED! Was on the race: " + convertToTime(car.getFinishTime()));

        }

        Optional<RaceCarRunnable> minCar = cars.stream().min((a, b) -> Long.compare(a.getFinishTime(), b.getFinishTime()));
        if (minCar.isPresent()) {
            System.out.println("Winner is " + minCar.get().getName());

        }


    }


    public static void startRace(List<Thread> threads) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("3");
                        Thread.sleep(500);
                        System.out.println("2");
                        Thread.sleep(500);
                        System.out.println("1");
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startRaceTime.set(System.currentTimeMillis());
                    System.out.println("GO. Race started at: " + convertToTime(startRaceTime.get()));
                    for (Thread thread1 : threads) {
                        thread1.start();


                    }
                }
        );
        thread.start();
    }

    public static String convertToTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}
