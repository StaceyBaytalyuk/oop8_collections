package oop.lab8;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private Scanner in = new Scanner(System.in);
    private StudentProcessor processor = new StudentProcessor();

    private void run(){
        printMenu();
        while (true) {
            int answer = menu();
            switch (answer) {
                case 0: System.exit(0);

                case 1: {
                    System.out.print("Enter name of file: ");
                    processor.readBinaryFile(in.next());
                    break;
                }
                case 2: {
                    System.out.print("Enter name of file: ");
                    processor.writeBinaryFile(in.next());
                    break;
                }

                case 3: {
                    if ( processor.addRecord() ) System.out.println("Successfully added!");
                    else System.out.println("Can't add record");
                    break;
                }

                case 4: {
                    System.out.print("Enter ID: ");
                    if ( processor.deleteRecord(in.nextInt()) ) System.out.println("Successfully deleted!");
                    else System.out.println("Student not found");
                    break;
                }

                case 5: {
                    processor.addSomeRecords();
                    processor.printAllStudents();
                    break;
                }

                case 6: {
                    processor.printAllStudents();
                    break;
                }

                case 7: {
                    processor.printAllNames();
                    break;
                }

                case 8: {
                    A();
                    break;
                }

                case 9: {
                    B();
                    break;
                }

                case 10: {
                    C();
                    break;
                }

                case 11: {
                    D();
                    break;
                }

                case 12: {
                    E();
                    break;
                }

                case 13: {
                    F();
                    break;
                }

                default: System.out.println("Wrong number. Try again!");
            }
        }
    }

    private void A() {
        System.out.print("Enter faculty: ");
        in.nextLine();
        String faculty = in.nextLine();
        List<Student> students = processor.searchFaculty(faculty);
        if ( students.size() > 0 ) {
            System.out.println(faculty);
            processor.printNames(students);
        } else {
            System.out.println("No students from "+faculty);
        }
    }

    private void B() {
        System.out.print("Enter year: ");
        int year = in.nextInt();
        List<Student> students = processor.searchAfterYear(year);
        if ( students.size() > 0 ) {
            System.out.println("Born after "+year);
            processor.printNames(students);
        } else {
            System.out.println("No students born after "+year);
        }
    }

    private void C() {
        System.out.print("Enter group: ");
        int group = in.nextInt();
        Queue<Student> students = processor.searchGroup(group);
        if ( students.size() > 0 ) {
            System.out.println("Group "+group);
            processor.printNamesAlphabetic(students);
        } else {
            System.out.println("No students from "+group);
        }
    }
    private void D() {
        Map<String, Queue<Student>> map = processor.facultyAgeList();
        if ( map.size() > 0 ) {
            for (String faculty : map.keySet()) {
                System.out.println(faculty);
                int size = map.get(faculty).size();
                for (int i = 0; i < size; i++) {
                    processor.printStudentsSortedByBirthday(map.get(faculty));
                }
                System.out.println();
            }
        } else {
            System.out.println("No students found");
        }
    }

    private void E() {
        Set<String> list = processor.facultiesWithUniqueStudents();
        if ( list.size() > 0 ) {
            System.out.println("Faculties with unique students:");
            int count = 1;
            for (String faculty : list) {
                System.out.println((count++) + ") "+faculty);
            }
        } else {
            System.out.println("No faculties with unique students");
        }
    }

    private void F() {
        Map<String, Integer> list = processor.numberOfStudentsAtUniqueFaculties();
        if ( list.size() > 0 ) {
            System.out.println("Faculties with unique students:");
            int count = 1;
            for (String faculty : list.keySet()) {
                System.out.println((count++) + ") "+faculty + " - " + list.get(faculty) + " students");
            }
        } else {
            System.out.println("No faculties with unique students");
        }
    }

    private int menu() {
        System.out.println();
        System.out.print("Choose number from menu: ");
        return in.nextInt();
    }

    private void printMenu() {
        System.out.println("" +
                "1) Read from file\n" +
                "2) Write to file\n" +
                "3) Add record\n" +
                "4) Delete record\n" +
                "5) Generate records\n" +
                "6) Show all records\n" +
                "7) Show all records (short form)\n" +
                "8)  A - Faculty list\n" +
                "9)  B - Students born after year\n" +
                "10) C - Group list\n" +
                "11) D - Sort by faculty and age\n" +
                "12) E - Faculties with unique students\n" +
                "13) F - E+number of students\n" +
                "0) Exit");
    }
}