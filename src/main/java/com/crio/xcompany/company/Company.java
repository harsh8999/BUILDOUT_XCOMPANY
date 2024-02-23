package com.crio.xcompany.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Company{
    private String companyName;
    private Employee founder;
    private Map<String,Employee> employeeBook;
    // manager -> reports
    private Map<String, List<Employee>> directReports;     
    

    private Company(String companyName, Employee founder) {
        this.companyName = companyName;
        this.founder = founder;
        employeeBook = new HashMap<String,Employee>();
        employeeBook.put(founder.getName(), founder);
        directReports = new HashMap<String, List<Employee>>();
    }
    

    public static Company create(String companyName, Employee founder){
        return new Company(companyName,founder);
    } 


    public String getCompanyName() {
        return companyName;
    }
    
    public void registerEmployee(String employeeName, Gender gender) {
        Employee employee = new Employee(employeeName, gender);
        employeeBook.put(employee.getName(), employee);
    }

    public Employee getEmployee(String employeeName) {
        return employeeBook.get(employeeName);
    }

    public void deleteEmployee(String employeeName) {
        Employee employee = employeeBook.get(employeeName);
        if(employee != null) {
            employeeBook.remove(employeeName);
            // Remove the employee from their manager's direct reports
            Employee manager = employee.getManager();
            if(manager != null) {
                manager.removeDirectReport(employee);
            }
        }
    }

    public void assignManager(String employeeName, String managerName) {
        Employee employee = employeeBook.get(employeeName);
        Employee manager = employeeBook.get(managerName);

        if(employee != null && manager != null) {
            // Assign the manager to the employee
            employee.assignManager(manager);
            addDirectReport(managerName, employee);
        }
        
    }

    public void addDirectReport(String managerName, Employee directReport) {
        directReports.computeIfAbsent(managerName, key -> new ArrayList<>()).add(directReport);
    }

    public List<Employee> getDirectReports(String managerName) {
        List<Employee> directReportsList = directReports.get(managerName);
        return directReportsList != null ? directReportsList : new ArrayList<>();
    }

    public List<Employee> getTeamMates(String employeeName) {
        Employee employee = employeeBook.get(employeeName);
        if(employee == null) {
            return new ArrayList<>();
        }
        Employee manager = employee.getManager();
        if(manager != null) {
            List<Employee> list = (ArrayList<Employee>) getDirectReports(manager.getName());
            list.add(0, manager);
            return list;

        }
        return new ArrayList<>();
    }

    public List<List<Employee>> getEmployeeHierarchy(String managerName) {

        List<List<Employee>> hierarchy = new ArrayList<>();
        Map<String, Employee> visited = new HashMap<>();
        Queue<Employee> queue = new LinkedList<>();

        Employee manager = employeeBook.get(managerName);

        if(manager == null) {
            return hierarchy; // Manager not found
        }

        queue.add(manager);
        visited.put(managerName, manager);

        while(!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Employee> currentLevel = new ArrayList<>();

            for(int i = 0; i < levelSize; i++) {
                Employee currentEmployee = queue.poll();
                currentLevel.add(currentEmployee);

                List<Employee> directReports = getDirectReports(currentEmployee.getName());
                for(Employee directReport : directReports) {
                    if(!visited.containsKey(directReport.getName())) {
                        queue.add(directReport);
                        visited.put(directReport.getName(), directReport);
                    }
                }
            }

            hierarchy.add(currentLevel);
        }

        return hierarchy;
    }

}
