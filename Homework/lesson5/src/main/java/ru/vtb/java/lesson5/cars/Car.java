package ru.vtb.java.lesson5.cars;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;

    private CountDownLatch latchWhenCarIsReady;
    private CyclicBarrier barrierWaitForStart;
    private CountDownLatch latchWhenFinishedTheRace;
    private final Object lockForWinner = new Object();
    private static Boolean weHaveAWinner = false;

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch latchWhenCarIsReady, CyclicBarrier barrierWaitForStart, CountDownLatch latchWhenFinishedTheRace) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;

        this.latchWhenCarIsReady = latchWhenCarIsReady;
        this.barrierWaitForStart = barrierWaitForStart;
        this.latchWhenFinishedTheRace = latchWhenFinishedTheRace;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // машина готова - сообщает, чтобы главный поток знал, когда старт гонки объявить (когда все готовы)
            latchWhenCarIsReady.countDown();
        }

        // сразу после подготовки пусть ждет остальных. Главный поток скажет, когда можно
        try {
            barrierWaitForStart.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // гонка началась. Все асинхронно, тут не надо ничего.
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        // пусть машина сама себя объявит победителем
        synchronized (lockForWinner) {
            if (!weHaveAWinner) {
                weHaveAWinner = true;
                System.out.println(name + " - WIN");
            }
        }

        // эта машина гонку закончила - сообщает об этом. На этом ее полномочия как бы все.
        latchWhenFinishedTheRace.countDown();
    }
}

