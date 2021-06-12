package ru.vtb.java.lesson7;

import ru.vtb.java.lesson7.annotations.AppColumn;
import ru.vtb.java.lesson7.annotations.AppTable;

@AppTable("TestTable")
public class TestTable {
    @AppColumn()
    public int id;

    @AppColumn()
    public String name;

    @AppColumn()
    public String description;

    public int state;

    public TestTable() {}

    public TestTable(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
