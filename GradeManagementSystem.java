import java.util.Scanner;

public class GradeManagementSystem {
    
    private static final int MAX_STUDENTS = 100;
    private static final int SUBJECT_COUNT = 5;

    private static String[] studentNames;
    private static double[][] studentMarks;
    private static int studentCount = 0;

    private static final String[] SUBJECTS = {
            "Mathematics",
            "Science",
            "English",
            "History",
            "Computer"
    };

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        initializeArrays();
        boolean running = true;

        while (running) {
            System.out.println("\n=== GRADE MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Student Marks");
            System.out.println("2. View All Students");
            System.out.println("3. Calculate Averages");
            System.out.println("4. Find Top Performer");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = getValidInt(1, 6);

            switch (choice) {
                case 1:
                    addStudentMarks();
                    break;

                case 2:
                    viewAllStudents();
                    break;

                case 3:
                    calculateAverages();
                    break;

                case 4:
                    findTopPerformers();
                    break;

                case 5:
                    generateReport();
                    break;

                case 6:
                    running = false;
                    System.out.println("Thank you for using Grade Management System!");
                    break;
            }
        }

        scanner.close();
    }

    private static void initializeArrays() {
        studentNames = new String[MAX_STUDENTS];
        studentMarks = new double[MAX_STUDENTS][SUBJECT_COUNT];
    }

    private static void addStudentMarks() {

        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Maximum student limit reached!");
            return;
        }

        System.out.println("\n=== ADD STUDENT MARKS ===");
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        studentNames[studentCount] = name;

        System.out.println("\nEnter marks for 5 subjects (0 - 100):");

        for (int i = 0; i < SUBJECT_COUNT; i++) {
            System.out.print(SUBJECTS[i] + ": ");
            studentMarks[studentCount][i] = getValidMark();
        }

        studentCount++;
        System.out.println("Student marks added successfully!");
    }

    private static void viewAllStudents() {

        System.out.println("\n=== ALL STUDENTS ===");

        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }

        System.out.printf(
                "%-20s %-10s %-10s %-10s %-10s %-10s %-10s%n",
                "Name",
                "Math",
                "Science",
                "English",
                "History",
                "Computer",
                "Average"
        );

        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < studentCount; i++) {

            double average = calculateStudentAverage(i);

            System.out.printf(
                    "%-20s %-10.2f %-10.2f %-10.2f %-10.2f %-10.2f %-10.2f%n",
                    studentNames[i],
                    studentMarks[i][0],
                    studentMarks[i][1],
                    studentMarks[i][2],
                    studentMarks[i][3],
                    studentMarks[i][4],
                    average
            );
        }
    }

    private static double calculateStudentAverage(int studentIndex) {

        double sum = 0;

        for (int i = 0; i < SUBJECT_COUNT; i++) {
            sum += studentMarks[studentIndex][i];
        }

        return sum / SUBJECT_COUNT;
    }

    private static void calculateAverages() {

        System.out.println("\n=== STUDENT AVERAGES ===");

        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }

        for (int i = 0; i < studentCount; i++) {

            double average = calculateStudentAverage(i);

            System.out.printf(
                    "%s -> Average: %.2f | Grade: %s%n",
                    studentNames[i],
                    average,
                    getGrade(average)
            );
        }
    }

    private static String getGrade(double average) {

        if (average >= 90)
            return "A+";
        else if (average >= 80)
            return "A";
        else if (average >= 70)
            return "B";
        else if (average >= 60)
            return "C";
        else if (average >= 50)
            return "D";
        else
            return "F";
    }

    private static void findTopPerformers() {

        System.out.println("\n=== TOP PERFORMER ===");

        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }

        int topIndex = 0;
        double highestAverage = calculateStudentAverage(0);

        for (int i = 1; i < studentCount; i++) {

            double average = calculateStudentAverage(i);

            if (average > highestAverage) {
                highestAverage = average;
                topIndex = i;
            }
        }

        System.out.println("Student Name : " + studentNames[topIndex]);
        System.out.printf("Average Marks: %.2f%n", highestAverage);
        System.out.println("Grade        : " + getGrade(highestAverage));
    }

    private static void generateReport() {

        System.out.println("\n=== CLASS REPORT ===");

        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }

        double totalAverage = 0;

        for (int i = 0; i < studentCount; i++) {

            double average = calculateStudentAverage(i);
            totalAverage += average;

            System.out.println("--------------------------------");
            System.out.println("Student : " + studentNames[i]);
            System.out.printf("Average : %.2f%n", average);
            System.out.println("Grade   : " + getGrade(average));
        }

        System.out.println("--------------------------------");
        System.out.printf("Class Average: %.2f%n",
                totalAverage / studentCount);
    }

    private static int getValidInt(int min, int max) {

        while (true) {
            try {

                int value = scanner.nextInt();
                scanner.nextLine();

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.printf(
                        "Please enter a number between %d and %d: ",
                        min,
                        max
                );

            } catch (Exception e) {

                System.out.print("Invalid input! Enter a number: ");
                scanner.nextLine();
            }
        }
    }

    private static double getValidMark() {

        while (true) {
            try {

                double mark = scanner.nextDouble();
                scanner.nextLine();

                if (mark >= 0 && mark <= 100) {
                    return mark;
                }

                System.out.print("Marks must be between 0 and 100: ");

            } catch (Exception e) {

                System.out.print("Invalid input! Enter a valid mark: ");
                scanner.nextLine();
            }
        }
    }
}
    

