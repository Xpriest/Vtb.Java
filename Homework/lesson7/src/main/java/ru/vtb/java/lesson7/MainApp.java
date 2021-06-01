package ru.vtb.java.lesson7;

public class MainApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Program started!");
        System.out.println("---------------------------------------------------");

        // ох и уродливо получилось. Но работу делает.
        var testOrm = new TestOrm();

        var testTableObject1 = new TestTable(1, "name1", "description1");
        var testTableObject2 = new TestTable(2, "name2", "description2");
        var testTableObject3 = new TestTable(3, "name3", "description3");

        testOrm.createTable(testTableObject1);

        testOrm.save(testTableObject1);
        testOrm.save(testTableObject2);
        testOrm.save(testTableObject3);

        System.out.println("---------------------------------------------------");
        System.out.println("Program finished!");
    }
}
