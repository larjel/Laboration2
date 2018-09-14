package userio;

import employee.management.EmployeeStatistics;
import employee.management.EmployeeManager;
import employee.Employee;
import employee.Programmer;
import employee.Secretary;
import employee.Technician;

public class MenuHandler {

    // Wrapper class for Scanner. Handles exceptions & buffer flushing.
    private final SystemInput SI;
    // Handles Employee list and operations on the list
    private final EmployeeManager EM;
    // Handles calculation and displaying of employee statistics
    private final EmployeeStatistics ES;

    /**
     * Constructor
     */
    public MenuHandler() {
        SI = new SystemInput();
        EM = new EmployeeManager();
        ES = new EmployeeStatistics(EM);
    }

    /**
     * Main menu handler
     *
     * @return true if to continue, false if to exit
     */
    public boolean mainMenu() {
        System.out.println("1. Employee management (add, update, delete)");
        System.out.println("2. Employee statistics");
        System.out.println("3. Exit");

        int choice = SI.getInt();
        switch (choice) {
            case 1: // Employee management
                while (employeeManagementMenu()) {
                }
                break;
            case 2: // Employee statistics
                while (employeeStatisticsMenu()) {
                }
                break;
            case 3: // Exit
                return false;
            default:
                System.out.println("Invalid menu choice! Try again.");
                break;
        }
        return true;
    }

    /**
     * Employee management menu
     *
     * @return true if to continue, false if to exit
     */
    private boolean employeeManagementMenu() {
        System.out.println("1. Register employee");
        System.out.println("2. Delete employee");
        System.out.println("3. Update name of employee");
        System.out.println("4. Update the birthdate of employee");
        System.out.println("5. Update the department of employee");
        System.out.println("6. Update Salary of employee");
        System.out.println("7. Search employee by name");
        System.out.println("8. Search employee number");
        System.out.println("9. Search employee by department");
        System.out.println("10. Display all employees");
        System.out.println("0. Back to main menu");

        int choice = SI.getInt();
        switch (choice) {
            case 0: // Exit
                return false;
            case 1: // Register employee
                registerEmployee();
                break;
            case 2: { // Delete employee
                deleteEmployee();
                break;
            }
            case 3: // Update name of employee
            case 4: // Update the birthdate of employee
            case 5: // Update the department of employee
            case 6: // Update Salary of employee
                updateEmployee(choice);
                break;
            case 7: // Search employee by name
            case 8: // Search employee number
            case 9: // Search employee by department
                searchEmployee(choice);
                break;
            case 10: // Display all employees
                EM.displayAll();
                break;
            default:
                System.out.println("Invalid menu choice! Try again.");
                break;
        }
        return true;
    }

    private void deleteEmployee() {
        System.out.print("ID of employee to delete: ");
        int employeeId = SI.getInt();
        if (EM.delete(employeeId)) {
            System.out.println("Employee successfully deleted.");
        } else {
            System.out.println("Error! Employee deletion failed.");
        }
    }

    /**
     * Search for an employee
     *
     * @param searchAction What field to use for search
     */
    private void searchEmployee(int searchAction) {
        switch (searchAction) {
            case 7: { // Search employee by name
                System.out.print("First name: ");
                String fname = SI.getString().trim();
                System.out.print("Last name: ");
                String lname = SI.getString().trim();
                EM.search(null, fname + " " + lname, null);
                break;
            }
            case 8: { // Search employee number
                System.out.print("Employee number/ID: ");
                int employeeId = SI.getInt();
                EM.search(employeeId, null, null);
                break;
            }
            case 9: { // Search employee by department
                System.out.print("Profession/department (");
                System.out.print(Secretary.ID + "=Secretary, " + Technician.ID + "=Technician, " + Programmer.ID + "=Programmer): ");
                int profession = SI.getInt();
                EM.search(null, null, profession);
                break;
            }
            default:
                break;
        }
    }

    /**
     * Update employee information
     *
     * @param updateAction What field to update
     */
    private void updateEmployee(int updateAction) {
        System.out.print("ID of employee to update: ");
        int employeeId = SI.getInt();

        boolean success;
        switch (updateAction) {
            case 3: { // Update name of employee
                System.out.print("New first name: ");
                String fname = SI.getString().trim();
                System.out.print("New last name: ");
                String lname = SI.getString().trim();
                success = EM.update(employeeId, fname + " " + lname, null, null, null, null);
                break;
            }
            case 4: { // Update the birthdate of employee
                System.out.print("New (corrected) birthday (YYMMDD): ");
                String birthday = SI.getString().trim();
                success = EM.update(employeeId, null, birthday, null, null, null);
                break;
            }
            case 5: { // Update the department of employee
                System.out.print("New profession/department (");
                System.out.print(Secretary.ID + "=Secretary, " + Technician.ID + "=Technician, " + Programmer.ID + "=Programmer): ");
                int profession = SI.getInt();
                success = EM.update(employeeId, null, null, profession, null, null);
                break;
            }
            case 6: { // Update Salary of employee
                System.out.print("Salary: ");
                int salary = SI.getInt();
                success = EM.update(employeeId, null, null, null, salary, null);
                break;
            }
            default:
                success = false;
                break;
        }

        if (success) {
            System.out.println("Employee successfully updated.");
        } else {
            System.out.println("Error! Employee update failed.");
        }
    }

    /**
     * Register employee
     */
    private void registerEmployee() {
        System.out.print("First name: ");
        String fname = SI.getString().trim();
        System.out.print("Last name: ");
        String lname = SI.getString().trim();
        System.out.print("Birthday (YYMMDD): ");
        String birthday = SI.getString().trim();
        System.out.print("Salary: ");
        int salary = SI.getInt();
        System.out.print("Profession/department (");
        System.out.print(Secretary.ID + "=Secretary, " + Technician.ID + "=Technician, " + Programmer.ID + "=Programmer): ");
        int profession = SI.getInt();
        System.out.print("Gender (1=Man, 2=Woman, 3=Undefined): ");
        int gender = SI.getInt();
        String genderString = Employee.GENDER_UNDEFINED;
        if (gender == 1) {
            genderString = Employee.GENDER_MAN;
        } else if (gender == 2) {
            genderString = Employee.GENDER_WOMAN;
        }
        if (EM.add(fname + " " + lname, birthday, profession, salary, genderString)) {
            System.out.println("Employee successfully registered!");
        } else {
            System.out.println("Error! Failure to register employee.");
        }
    }

    /**
     * Employee statistics menu
     *
     * @return true if to continue, false if to exit
     */
    private boolean employeeStatisticsMenu() {
        System.out.println("1. Average wage at the company");
        System.out.println("2. Maximum salary in the company");
        System.out.println("3. Minimum salary in the company");
        System.out.println("4. Total bonus");
        System.out.println("5. Women in percentage in the company");
        System.out.println("6. Men percentage of the various work role categories");
        System.out.println("0. Back to Main Menu");

        int choice = SI.getInt();
        switch (choice) {
            case 0: // Back to Main Menu
                return false;
            case 1: // Average wage at the company
                ES.displayAverageSalary();
                break;
            case 2: // Maximum salary in the company
                ES.displayMaxSalary();
                break;
            case 3: // Minimum salary in the company
                ES.displayMinSalary();
                break;
            case 4: // Total bonus
                ES.displayTotalBonus();
                break;
            case 5: // Women in percentage in the company
                ES.displayWomenStaffPercentage();
                break;
            case 6: // Men percentage of the various work role categories
                ES.displayMenPercentageOfWorkRole();
                break;
            default:
                System.out.println("Invalid menu choice! Try again.");
                break;
        }
        return true;
    }

}