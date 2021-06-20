package ru.vtb.java.lesson8;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import ru.vtb.java.lesson8.entities.Car;
import ru.vtb.java.lesson8.entities.Plane;

public class MainApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Program started!");
        System.out.println("---------------------------------------------------");

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        try {
            // МАШИНА
            Repository<Car, Long> carRepository = new Repository<>(Car.class, factory);

            System.out.println("получим машину (id = 1)");
            var car1 = carRepository.get(1L);
            car1.print();

            System.out.println("добавим машину (id = 3 будет)");
            var car2 = new Car("Mercedes", 280L);
            carRepository.insert(car2);
            car2.print();

            System.out.println("получим добавленную машину (id = 3)");
            var car2Inserted = carRepository.get(3L);
            car2Inserted.print();

            System.out.println("обновим полученную машину (id = 3)");
            car2Inserted.setHorsePower(320L);
            carRepository.update(car2Inserted);

            System.out.println("получим обновленную машун (id = 3)");
            car2Inserted = carRepository.get(3L);
            car2Inserted.print();

            System.out.println("получим машину (id = 1), затем удалим ее и еще раз попробуем получить");
            var tempCar1 = carRepository.get(1L);
            tempCar1.print();
            carRepository.delete(car1);
            var tempCar2 = carRepository.get(1L);
            if (tempCar2 == null) {
                System.out.println("Машины с Id = 1 больше нет.");
            }
            else {
                tempCar2.print();
            }

            // САМОЛЕТ
            Repository<Plane, Long> planeRepository = new Repository<>(Plane.class, factory);

            System.out.println("получим самолет (id = 1)");
            var plane1 = planeRepository.get(1L);
            plane1.print();

            System.out.println("обновим самолет (id = 1) и опять его получим");
            plane1.setFlightHeight(12345F);
            planeRepository.update(plane1);
            var plane1Updated = planeRepository.get(1L);
            plane1Updated.print();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (factory != null)
                factory.close();
        }

        System.out.println("---------------------------------------------------");
        System.out.println("Program finished!");
    }
}
