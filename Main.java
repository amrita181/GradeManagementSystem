import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String department;
    private String position;
    private double salary;
    private Date joinDate;

    public Employee(String id, String name, String department,
                    String position, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.joinDate = new Date();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public Date getJoinDate() { return joinDate; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return String.format(
                "ID: %s | Name: %s | Department: %s | Position: %s | Salary: ₹%.2f | Joined: %s",
                id, name, department, position, salary,
                sdf.format(joinDate));
    }
}

class EmployeeManagementSystem {

    private ArrayList<Employee> employees;
    private HashMap<String, Employee> employeeMap;
    private Scanner scanner;

    private static final String DATA_FILE = "employees.dat";

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        employeeMap = new HashMap<>();
        scanner = new Scanner(System.in);
        loadEmployeesFromFile();
    }

    // ADD EMPLOYEE
    public void addEmployee() {
        System.out.println("\n=== ADD EMPLOYEE ===");

        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        if (employeeMap.containsKey(id)) {
            System.out.println("Employee ID already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        System.out.print("Enter Position: ");
        String position = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double salary = Double.parseDouble(scanner.nextLine());

        Employee emp = new Employee(id, name, department, position, salary);

        employees.add(emp);
        employeeMap.put(id, emp);

        saveEmployeesToFile();

        System.out.println("Employee added successfully!");
    }

    // DISPLAY ALL EMPLOYEES
    public void displayAllEmployees() {
        System.out.println("\n=== EMPLOYEE LIST ===");

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    // SEARCH MENU
    public void searchEmployee() {

        System.out.println("\n1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Department");

        System.out.print("Enter choice: ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                searchById();
                break;

            case 2:
                searchByName();
                break;

            case 3:
                searchByDepartment();
                break;

            default:
                System.out.println("Invalid choice!");
        }
    }

    // SEARCH BY ID
    private void searchById() {

        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        Employee emp = employeeMap.get(id);

        if (emp != null) {
            System.out.println(emp);
        } else {
            System.out.println("Employee not found.");
        }
    }

    // SEARCH BY NAME
    private void searchByName() {

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().toLowerCase();

        ArrayList<Employee> result = new ArrayList<>();

        for (Employee emp : employees) {
            if (emp.getName().toLowerCase().contains(name)) {
                result.add(emp);
            }
        }

        displaySearchResults(result);
    }

    // SEARCH BY DEPARTMENT
    private void searchByDepartment() {

        System.out.print("Enter Department: ");
        String dept = scanner.nextLine().toLowerCase();

        ArrayList<Employee> result = new ArrayList<>();

        for (Employee emp : employees) {
            if (emp.getDepartment().toLowerCase().contains(dept)) {
                result.add(emp);
            }
        }

        displaySearchResults(result);
    }

    // DISPLAY SEARCH RESULTS
    private void displaySearchResults(List<Employee> result) {

        if (result.isEmpty()) {
            System.out.println("No matching employees found.");
            return;
        }

        System.out.println("\nSearch Results:");

        for (Employee emp : result) {
            System.out.println(emp);
        }
    }

    // DELETE EMPLOYEE
    public void deleteEmployee() {

        System.out.print("Enter Employee ID to delete: ");
        String id = scanner.nextLine();

        Employee emp = employeeMap.remove(id);

        if (emp != null) {
            employees.remove(emp);
            saveEmployeesToFile();
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    // SAVE FILE
    private void saveEmployeesToFile() {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {

            oos.writeObject(employees);

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    // LOAD FILE
    @SuppressWarnings("unchecked")
    private void loadEmployeesFromFile() {

        File file = new File(DATA_FILE);

        if (!file.exists())
            return;

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(DATA_FILE))) {

            employees = (ArrayList<Employee>) ois.readObject();

            for (Employee emp : employees) {
                employeeMap.put(emp.getId(), emp);
            }

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }

    // MAIN MENU
    public void start() {

        while (true) {

            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    addEmployee();
                    break;

                case 2:
                    displayAllEmployees();
                    break;

                case 3:
                    searchEmployee();
                    break;

                case 4:
                    deleteEmployee();
                    break;

                case 5:
                    saveEmployeesToFile();
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

// MAIN CLASS
public class Main {

    public static void main(String[] args) {

        EmployeeManagementSystem ems =
                new EmployeeManagementSystem();

        ems.start();
    }
}