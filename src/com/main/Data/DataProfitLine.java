package com.main.Data;

public class DataProfitLine {
    private int id;
    private String name;
    private String profitForSalon;
    private String salary;

    public DataProfitLine(int id, String name, String profitForSalon, String salary) {
        this.id = id;
        this.name = name;
        this.profitForSalon = profitForSalon;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfitForSalon() {
        return profitForSalon;
    }

    public String getSalary() {
        return salary;
    }
}
