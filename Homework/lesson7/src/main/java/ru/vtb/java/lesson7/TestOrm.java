package ru.vtb.java.lesson7;

import ru.vtb.java.lesson7.TestOrmException;
import ru.vtb.java.lesson7.annotations.AppColumn;
import ru.vtb.java.lesson7.annotations.AppTable;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestOrm {
    private final String appTableAnno = "AppTable";
    private final String appColumnAnno = "AppColumn";

    private Connection connection;
    private Statement statement;

    public TestOrm() {
        createDbConnection();
    }


    // создаем таблицу по переданному объекту, если он размечен
    public void createTable(Object inputObject) throws Exception {
        // create table_name (id integer, name string)
        var result = new StringBuilder();

        var cl = inputObject.getClass();

        // получим аннотацию таблицы, добавим в результат
        var appTableAnnotation = (AppTable) Arrays.stream(cl.getAnnotations())
                .filter(m -> m.annotationType().getSimpleName().equals(appTableAnno))
                .findAny().orElse(null);
        if (appTableAnnotation == null)
            throw new TestOrmException("Class " + cl.getName() + " has no table annotation.");

        var tableName = appTableAnnotation.value();
        if (tableName == null || tableName == "") {
            tableName = cl.getSimpleName();
        }

        result.append("create table if not exists " + tableName + " (");

        // по полям пробежимся, те, что с аннотациями - добавим в результат
        var allFields = cl.getDeclaredFields();
        for (var singleField : allFields) {
            var tempProcessedFieldInfo = processFieldInfo(singleField);
            if (tempProcessedFieldInfo != null) {
                var tempEntry = tempProcessedFieldInfo.entrySet().iterator().next();
                result.append(tempEntry.getKey() + " " + tempEntry.getValue() + ", ");
            }
        }
        result.setLength(result.length() - 2);

        result.append(");");
        System.out.println(result);

        executeSqlCommand(result.toString());
        System.out.println("Table was successfully created.");
    }

    // сохраняет объект в бд
    public void save(Object inputObject) throws Exception {
        // create table_name (id integer, name string)
        var result = new StringBuilder();

        var cl = inputObject.getClass();

        // получим аннотацию таблицы, добавим в результат
        var appTableAnnotation = (AppTable) Arrays.stream(cl.getAnnotations())
                .filter(m -> m.annotationType().getSimpleName().equals(appTableAnno))
                .findAny().orElse(null);
        if (appTableAnnotation == null)
            throw new TestOrmException("Class " + cl.getName() + " has no table annotation.");

        var tableName = appTableAnnotation.value();
        if (tableName == null || tableName == "") {
            tableName = cl.getSimpleName();
        }

        result.append("insert into " + tableName + " (");

        // по полям пробежимся, те, что с аннотациями - добавим в результат
        var allFields = cl.getDeclaredFields();
        Map<String, Object> fieldAndValues = new HashMap<>();
        for (var singleField : allFields) {
            var tempProcessedFieldInfo = processFieldInfo(singleField);
            if (tempProcessedFieldInfo != null) {
                var tempEntry = tempProcessedFieldInfo.entrySet().iterator().next();
                var tempValue = singleField.get(inputObject);
                if (tempEntry.getValue() == "text")
                    tempValue = "'" + tempValue + "'";

                fieldAndValues.put(tempEntry.getKey(), tempValue);
            }
        }

        String fields = fieldAndValues.keySet().stream().reduce("", (s, s2) -> s + ", " + s2);
        String values = (String) fieldAndValues.values().stream().reduce("", (s, s2) -> s + ", " + s2);

        result.append(fields.substring(2) + ") values (" + values.substring(2) + ");");
        System.out.println(result);

        executeSqlCommand(result.toString());
        System.out.println("Record was successfully inserted.");
    }

    private Map<String, String> processFieldInfo(Field inputField) throws TestOrmException {
        Map<String, String> result = new HashMap<>();

        var columnAnnotation = (AppColumn) Arrays.stream(inputField.getAnnotations())
                .filter(m -> m.annotationType().getSimpleName().equals(appColumnAnno))
                .findAny().orElse(null);
        if (columnAnnotation == null)
            return null;

        var fieldType = inputField.getType().getSimpleName();
        switch (fieldType) {
            case "int":
                result.put(inputField.getName(), "integer");
                break;
            case "String":
                result.put(inputField.getName(), "text");
                break;
            default:
                break;
        }

        return result;
    }

    private void executeSqlCommand(String sqlCommand) throws SQLException {
        try {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // конект к базе создаем
    private void createDbConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:lesson7.db");
            statement = connection.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // конект к базе закрываем
    public void closeDbConnection() {
        try {
            if (statement != null)
                statement.close();

            if (connection != null)
                connection.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
