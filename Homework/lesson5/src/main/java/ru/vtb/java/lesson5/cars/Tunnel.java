package ru.vtb.java.lesson5.cars;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore tunnelAccessSemaphore;

    public Tunnel(Semaphore inputSemaphore) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";

        this.tunnelAccessSemaphore = inputSemaphore;
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);

            tunnelAccessSemaphore.acquire();

            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(c.getName() + " закончил этап: " + description);
            tunnelAccessSemaphore.release();
        }
    }
}

