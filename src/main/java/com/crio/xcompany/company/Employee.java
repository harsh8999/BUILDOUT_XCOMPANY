package com.crio.xcompany.company;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String name;
    private Gender gender;
    private Employee manager;
    private List<Employee> directReports;

    public Employee(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.manager = null;
        this.directReports = new ArrayList<>();
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public void assignManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getDirectReports() {
        return directReports;
    }

    public void addDirectReport(Employee employee) {
        directReports.add(employee);
    }

    public void removeDirectReport(Employee employee) {
        directReports.remove(employee);
    }
    
    public List<Employee> getTeamMates() {
        if(manager == null) {
            return new ArrayList<>();
        }
        List<Employee> teamMates = new ArrayList<>(manager.getDirectReports());
        teamMates.remove(this);
        return teamMates;
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", gender=" + gender + "]";
    }   
}
