package oop.lab8;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StudentProcessor {
    private Scanner in = new Scanner(System.in);
    private List<Student> students = new ArrayList<>();

    public boolean addRecord() {
        return students.add(enterStudentData());
    }

    public boolean deleteRecord(int id) {
        return students.removeIf( student -> ( student.getId() == id ) );
    }

    public void readBinaryFile(String fileName) {
        try ( FileInputStream fis = new FileInputStream(fileName) ) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            while ( fis.available() != 0 ) {
                students.addAll( (ArrayList<Student>)ois.readObject() );
            }
            System.out.println("Successfully read!");
        } catch ( IOException | ClassNotFoundException e ) {
            e.printStackTrace();
            System.err.println("Error reading binary file");
        }
    }

    public void writeBinaryFile(String fileName) {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)) ) {
            oos.writeObject(students);
            System.out.println("Successfully written!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing binary file");
        }
    }

    public List<Student> searchFaculty(String faculty) {
        List<Student> res = new ArrayList<>();
        for (Student student : students) {
            if ( student.getFaculty().equals(faculty) ) {
                res.add(student);
            }
        }
        return res;
    }

    public List<Student> searchAfterYear(int year) {
        List<Student> res = new ArrayList<>();
        for (Student student : students) {
            if ( student.getBirthday().getYear() > year ) {
                res.add(student);
            }
        }
        return res;
    }

    public Queue<Student> searchGroup(int group) {
        Queue<Student> res = new PriorityQueue<>( new StudentNameComparator() );
        for (Student student : students) {
            if ( student.getGroup() == group ) {
                res.add(student);
            }
        }
        return res;
    }

    public Map<String, Queue<Student>> facultyAgeList(){
        Map < String, Queue<Student> > map = new TreeMap<>();
        for (Student s: students) {
            if ( map.get(s.getFaculty()) == null ) {
                map.put(s.getFaculty(), new PriorityQueue<>(new StudentAgeComparator()));
            }
        }

        for (Student s: students) {
            map.get(s.getFaculty()).add(s);
        }
        return map;
    }

    public Set<String> facultiesWithUniqueStudents(){
        Set<String> all = new HashSet<>();
        for (Student s: students) {
            all.add(s.getFaculty());
        }

        Set<String> notUnique = new HashSet<>();
        for (int i = 0; i< students.size()-1; i++) {
            for (int j = i+1; j< students.size(); j++) {
                if( students.get(i).getId() == students.get(j).getId() ) {
                    notUnique.add( students.get(i).getFaculty() );
                    notUnique.add( students.get(j).getFaculty() );
                }
            }
        }

        all.removeAll(notUnique);
        return all;
    }

    public Map<String, Integer> numberOfStudentsAtUniqueFaculties(){
        Set<String> set = facultiesWithUniqueStudents();
        Map<String, Integer> map = new HashMap<>();
        for (Student student: students) {
            if( set.contains(student.getFaculty()) ) {
                Integer frequency = map.get(student.getFaculty());
                map.put(student.getFaculty(), frequency == null ? 1 : frequency + 1);
            }
        }
        return map;
    }

    public void printStudents(Collection<Student> students, String text) {
        System.out.println(text);
        int i = 1;
        for (Student student : students) {
            System.out.println((i++)+") "+student);
        }
    }

    public void printAllStudents() {
        printStudents(students, "All records:");
    }

    public void printAllNames() {
        printNames(students);
    }

    public void printNames(Collection<Student> students) {
        for (Student student : students) {
            if ( student != null ) {
                System.out.println("ID: "+student.getId()+", "+student.getFullName());
            } else {
                System.out.println("-");
            }
        }
    }

    public void printNamesAlphabetic(Queue<Student> students) {
        int size = students.size();
        for (int i = 0; i < size; i++) {
            Student student = students.poll();
            if ( student != null ) {
                System.out.println("ID: "+student.getId()+", "+student.getSurname());
            } else {
                System.out.println("-");
            }
        }
    }

    public void printStudentsSortedByBirthday(Queue<Student> students) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        int size = students.size();
        for (int i = 0; i < size; i++) {
            Student student = students.poll();
            if ( student != null ) {
                System.out.println("ID: "+student.getId()+", "+student.getSurname()+", "+formatter.format(student.getBirthday()));
            } else {
                System.out.println("-");
            }
        }
    }

    public void addSomeRecords() {
        Student.StudentBuilder builder = new Student.StudentBuilder();
        students.add(builder.setID(1).setFullName("Petrov Petr Petrovich").setBirthday("01.02.1998").setFaculty("Computer Science").setAddress("Stroiteley 45").setPhone("3456754").setCourse(3).setGroup(3141).build());
        students.add(builder.setID(2).setFullName("Ivanov Ivan Ivanovich").setBirthday("17.09.1999").setFaculty("Computer Science").setAddress("Mira 12").setPhone("778611").setCourse(2).setGroup(2141).build());
        students.add(builder.setID(3).setFullName("Sidorov Sidor Sidorovich").setBirthday("30.05.2000").setFaculty("Software Engineering").setAddress("Yuzhnaya 59").setPhone("001343").setCourse(1).setGroup(1151).build());
        students.add(builder.setID(4).setFullName("Vasiliev Vasiliy Vasilievich").setBirthday("23.01.2000").setFaculty("Computer Science").setAddress("Sobornaya 3").setPhone("765434").setCourse(2).setGroup(2141).build());
        students.add(builder.setID(5).setFullName("Alexeev Alexey Alexeevich").setBirthday("27.04.1999").setFaculty("Design").setAddress("Centralnaya 95").setPhone("70214").setCourse(2).setGroup(2331).build());
        students.add(builder.setID(6).setFullName("Mihaylov Mihail Mihaylovich").setBirthday("08.11.1999").setFaculty("Software Engineering").setAddress("Pogranichnaya 27").setPhone("08467").setCourse(2).setGroup(2141).build());
        students.add(builder.setID(6).setFullName("Mihaylov Mihail Mihaylovich").setBirthday("08.11.1999").setFaculty("Ecology").setAddress("Pogranichnaya 27").setPhone("08467").setCourse(2).setGroup(2111).build());
    }

    private Student enterStudentData() {
        Student.StudentBuilder builder = new Student.StudentBuilder();
        System.out.print("Enter ID: ");
        builder.setID(in.nextInt());
        System.out.print("Enter full name: ");
        builder.setSurname(in.next());
        builder.setFirstName(in.next());
        builder.setSecondName(in.next());
        System.out.print("Enter birthday in format dd.mm.yyyy: ");
        builder.setBirthday(in.next());
        System.out.print("Enter faculty: ");
        in.nextLine();
        builder.setFaculty(in.nextLine());
        System.out.print("Enter adress: ");
        builder.setAddress(in.nextLine());
        System.out.print("Enter phone: ");
        builder.setPhone(in.next());
        System.out.print("Enter course: ");
        builder.setCourse(in.nextInt());
        System.out.print("Enter group: ");
        builder.setGroup(in.nextInt());
        return builder.build();
    }
}