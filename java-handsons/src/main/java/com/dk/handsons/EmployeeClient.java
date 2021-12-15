package com.dk.handsons;

import com.dk.handsons.model.Employee;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dinesh
 * @version 1.0
 * @since 12/02/2021
 * <p>
 * The EmployeeClient demonstrate to store unique employees in the collection
 * </p>
 */
public class EmployeeClient {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Set<Employee> employeeSet = getEmployees();
        iterate(employeeSet);
    }

    /**
     *
     * @param employeeSet
     */
    private static void iterate(Set<Employee> employeeSet) {
        for (Employee employee : employeeSet) {
            System.out.println("Id: " + employee.getId() + " Name: " + employee.getName());
        }
    }


    /**
     * <p>
     * This method is used to get the collection of employees
     * </p>
     *
     * @return the Employee list
     */
    private static Set<Employee> getEmployees() {
        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(new Employee("E001", "Dinesh", "dinesh@gmail.com", 10000));
        employeeSet.add(new Employee("E001", "Dinesh", "dinesh@gmail.com", 10000));
        employeeSet.add(new Employee("E001", "Dinesh", "dinesh@gmail.com", 10000));
        employeeSet.add(new Employee("E002", "Dinesh", "dinesh@gmail.com", 10000));
        return employeeSet;
    }
}
