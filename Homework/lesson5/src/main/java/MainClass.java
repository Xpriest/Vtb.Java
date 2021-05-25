import ru.vtb.java.lesson5.cars.Car;
import ru.vtb.java.lesson5.cars.Race;
import ru.vtb.java.lesson5.cars.Road;
import ru.vtb.java.lesson5.cars.Tunnel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        // чтобы в тоннель только половина машин влезла
        var samephoreForTunnel = new Semaphore(CARS_COUNT / 2);
        Race race = new Race(new Road(60), new Tunnel(samephoreForTunnel), new Road(40));

        Car[] cars = new Car[CARS_COUNT];

        // чтобы главный поток знал, когда все машины готовы (и потом СТАРТ написал)
        var latchWhenCarIsReady = new CountDownLatch(CARS_COUNT);

        // чтобы главный поток знал, когда все машины приехали (и потом СТОП написал)
        var latchWhenCarsFinished = new CountDownLatch(CARS_COUNT);

        // а этот вот изврат, чтобы, когда все машины готовы, они не начали ехать, а ждали главный поток
        // и только после команды СТАРТ, с разрешения гл. потока - ехали
        var barrierWaitForStart = new CyclicBarrier(CARS_COUNT + 1);

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), latchWhenCarIsReady, barrierWaitForStart, latchWhenCarsFinished);
        }

        // запускаем потоки
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            // когда все машины будут готовы (они сами скажут) - мы начнем гонку
            latchWhenCarIsReady.await();

            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            // гонка начата - позволяем машинам ехать.
            // Т.к. нам надо сначала написать "СТАРТ", а потом уже машины поедут - CyclicBarrier мы именно тут освободим
            // (пока что все машины в него уперлись).
            barrierWaitForStart.await();

            // ждем, когда все машины доедут до конца трассы
            latchWhenCarsFinished.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
