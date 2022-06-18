package com.company;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class RaceCarRunnable extends Car {
    int passed;
    int distance;
    boolean isFinish = false;
    CountDownLatch cdl;
    Long finishTime = 0L;

    public RaceCarRunnable(String name, int maxSpeed, int distance, CountDownLatch cdl) {
        super(name, maxSpeed);
        this.distance = distance;
        this.cdl = cdl;
    }

    public int getRandomSpeed() {
        Random r = new Random();
        int min = maxSpeed / 2;
        int max = maxSpeed;
        int result = r.nextInt(max - min) + min;
        return result;
    }

    public float getProgress() {
        float progress = (passed * 100) / distance;
        return progress;
    }

    public long getFinishTime() {
        return finishTime;
    }

    @Override
    public void run() {
        while (!isFinish) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double s = getRandomSpeed();
            passed += s;
            System.out.println("Car " + name + " move to " + " " + getProgress() + " %");
            if (passed >= distance) {
                isFinish = true;
            }
        }
        finishTime = System.currentTimeMillis();
        cdl.countDown();
    }
}