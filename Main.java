import java.util.*;
import java.io.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {

        loadStudents();

        while (true) {

            System.out.println("\n=================================");
            System.out.println("  STUDENT MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input!");
                continue;
            }

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    saveStudents();
                    System.out.println("Data saved successfully.");
                    System.out.println("Thank you!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ================= ADD STUDENT =================

    static void addStudent() {

        int id;

        while (true) {

            System.out.print("Enter Student ID: ");

            try {
                id = Integer.parseInt(sc.nextLine());

                if (id <= 0) {
                    System.out.println("ID must be positive.");
                    continue;
                }

                boolean exists = false;

                for (Student s : students) {
                    if (s.id == id) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("Student ID already exists.");
                    continue;
                }

                break;

            } catch (Exception e) {
                System.out.println("Invalid ID.");
            }
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        int age;

        while (true) {

            System.out.print("Enter Student Age: ");

            try {
                age = Integer.parseInt(sc.nextLine());

                if (age <= 0) {
                    System.out.println("Age must be positive.");
                    continue;
                }

                break;

            } catch (Exception e) {
                System.out.println("Invalid Age.");
            }
        }

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        students.add(new Student(id, name, age, course));

        saveStudents();

        System.out.println("Student added successfully.");
    }
    // ================= VIEW STUDENTS =================

    static void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("\nNo students found.");
            return;
        }

        System.out.println("\n================================================================");
        System.out.printf("%-10s %-20s %-10s %-20s\n",
                "ID", "NAME", "AGE", "COURSE");
        System.out.println("----------------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-10d %-20s %-10d %-20s\n",
                    s.id, s.name, s.age, s.course);
        }

        System.out.println("================================================================");
    }

    // ================= SEARCH STUDENT =================

    static void searchStudent() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        System.out.print("Enter Student ID to search: ");

        try {

            int id = Integer.parseInt(sc.nextLine());

            for (Student s : students) {

                if (s.id == id) {

                    System.out.println("\nStudent Found");
                    System.out.println("--------------------------");
                    System.out.println("ID     : " + s.id);
                    System.out.println("Name   : " + s.name);
                    System.out.println("Age    : " + s.age);
                    System.out.println("Course : " + s.course);

                    return;
                }
            }

            System.out.println("Student not found.");

        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    // ================= UPDATE STUDENT =================

    static void updateStudent() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        System.out.print("Enter Student ID to update: ");

        try {

            int id = Integer.parseInt(sc.nextLine());

            for (Student s : students) {

                if (s.id == id) {

                    System.out.print("Enter New Name: ");
                    s.name = sc.nextLine();

                    while (true) {

                        System.out.print("Enter New Age: ");

                        try {

                            int age = Integer.parseInt(sc.nextLine());

                            if (age <= 0) {
                                System.out.println("Age must be positive.");
                                continue;
                            }

                            s.age = age;
                            break;

                        } catch (Exception e) {
                            System.out.println("Invalid Age.");
                        }
                    }

                    System.out.print("Enter New Course: ");
                    s.course = sc.nextLine();

                    saveStudents();

                    System.out.println("Student updated successfully.");

                    return;
                }
            }

            System.out.println("Student not found.");

        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }
    // ================= DELETE STUDENT =================

    static void deleteStudent() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        System.out.print("Enter Student ID to delete: ");

        try {

            int id = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < students.size(); i++) {

                if (students.get(i).id == id) {

                    System.out.print("Are you sure you want to delete? (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        students.remove(i);
                        saveStudents();
                        System.out.println("Student deleted successfully.");
                    } else {
                        System.out.println("Delete cancelled.");
                    }

                    return;
                }
            }

            System.out.println("Student not found.");

        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    // ================= SAVE STUDENTS =================

    static void saveStudents() {

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Student s : students) {
                writer.println(s.id + "," + s.name + "," + s.age + "," + s.course);
            }

        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // ================= LOAD STUDENTS =================

    static void loadStudents() {

        File file = new File(FILE_NAME);

        if (!file.exists())
            return;

        try (Scanner fileScanner = new Scanner(file)) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                if (data.length == 4) {

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    int age = Integer.parseInt(data[2]);
                    String course = data[3];

                    students.add(new Student(id, name, age, course));
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }
    // ================= STUDENT CLASS =================

    static class Student {

        int id;
        String name;
        int age;
        String course;

        Student(int id, String name, int age, String course) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.course = course;
        }
    }
}