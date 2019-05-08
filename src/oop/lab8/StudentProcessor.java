package oop.lab8;

import java.io.*;
import java.util.*;

public class StudentProcessor {
    private Scanner in = new Scanner(System.in);
    private ArrayList<Student> students = new ArrayList<>();

    public TreeMap < String, PriorityQueue<Student> > facultyAgeList(){
        TreeMap < String, PriorityQueue<Student> > list = new TreeMap<>();

        // add keys
        for (Student s: students) {
            if ( list.get(s.getFaculty()) == null ) {
                list.put(s.getFaculty(), new PriorityQueue<>(new StudentAgeComparator()));
            }
        }

        //add values
        for (Student s: students) {
            list.get(s.getFaculty()).add(s);
        }
        return list;
    }

    //TODO fix
    public HashSet<String> facultiesWithUniqueStudents(){
        //получить все факультеты
        HashSet<String> list = new HashSet<>();
        for (Student s: students) {
            list.add(s.getFaculty());
        }

        //если есть повтор, добавляем факультеты в список на удаление
        HashSet<String> notUnique = new HashSet<>();

        for (int i = 0; i< students.size()-1; i++) {
            for (int j = i+1; j< students.size(); j++) {
                //if( students.get(i).equals(students.get(j)) ) {
                if( students.get(i).getId() == students.get(j).getId() ) {
                    System.out.println("FOUND DUPLICATE!!!!!   "+students.get(i).getFaculty());
                    System.out.println(students.get(i));
                    notUnique.add( students.get(i).getFaculty() );
                    notUnique.add( students.get(j).getFaculty() );
                }
            }
        }

        list.removeAll(notUnique);
        return list;
    }

    public HashMap<String, Integer> numberOfStudentsAtUniqueFaculties(){
        HashSet<String> list = facultiesWithUniqueStudents();

        //если этот факультет уже есть - увеличить счётчик
        HashMap<String, Integer> map = new HashMap<>();
        for (Student s: students) {
            if( list.contains(s.getFaculty()) ) {
                Integer frequency = map.get(s.getFaculty());
                map.put(s.getFaculty(), frequency == null ? 1 : frequency + 1);
            }
        }
        return map;
    }

    public void showAll(){
        students.forEach(System.out::println);
    }

    public boolean addRecord() {
        return students.add(enterStudentData());
    }

    public boolean deleteRecord(int id) {
        return students.removeIf( student -> ( student.getId() == id ) );
    }

    public Student findStudent(int id) {
        Student student = new Student.StudentBuilder().setID(id).build();
        int index = students.indexOf(student);
        if ( index != -1 ) {
            return students.get(index);
        } else {
            return null;
        }
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

    public void printAllStudents() {
        printStudents(students, "All records:");
    }

    public void printStudents(Collection<Student> students, String text) {
        System.out.println(text);
        int i = 1;
        for (Student student : students) {
            System.out.println((i++)+") "+student);
        }
    }

    public void printAllSurnames() {
        printSurnames(students);
    }

    public void printSurnames(Collection<Student> students) {
        for (Student student : students) {
            if ( student != null ) {
                System.out.println(student.getId()+" "+student.getSurname());
            } else {
                System.out.println("-");
            }
        }
    }

    public ArrayList<Student> searchFaculty(String faculty) {
        ArrayList<Student> res = new ArrayList<>();
        for (Student student : students) {
            if ( student.getFaculty().equals(faculty) ) {
                res.add(student);
            }
        }
        return res;
    }

    public ArrayList<Student> searchAfterYear(int year) {
        ArrayList<Student> res = new ArrayList<>();
        for (Student student : students) {
            if ( student.getBirthday().getYear() > year ) {
                res.add(student);
            }
        }
        return res;
    }

    public PriorityQueue<Student> searchGroup(int group) {
        PriorityQueue<Student> res = new PriorityQueue<>( new StudentNameComparator() );
        for (Student student : students) {
            if ( student.getGroup() == group ) {
                res.add(student);
            }
        }
        return res;
    }

    public void addSomeRecords() {
        Student.StudentBuilder builder = new Student.StudentBuilder();
        students.add(builder.setID(1).setFullName("Petrov Petr Petrovich").setBirthday("01.02.1998").setFaculty("Computer Science").setAddress("Stroiteley 45").setPhone("3456754").setCourse(3).setGroup(3141).build());
        students.add(builder.setID(2).setFullName("Ivanov Ivan Ivanovich").setBirthday("17.09.1999").setFaculty("Computer Science").setAddress("Mira 12").setPhone("778611").setCourse(2).setGroup(2141).build());
        students.add(builder.setID(3).setFullName("Sidorov Sidor Sidorovich").setBirthday("30.05.2000").setFaculty("Software Engineering").setAddress("Yuzhnaya 59").setPhone("001343").setCourse(1).setGroup(1151).build());
        students.add(builder.setID(4).setFullName("Vasiliev Vasiliy Vasilievich").setBirthday("23.01.2000").setFaculty("Computer Science").setAddress("Sobornaya 3").setPhone("765434").setCourse(2).setGroup(2141).build());
        students.add(builder.setID(5).setFullName("Mihaylov Mihail Mihaylovich").setBirthday("08.11.1999").setFaculty("Computer Science").setAddress("Pogranichnaya 27").setPhone("08467").setCourse(2).setGroup(2141).build());
        students.add(builder.setID(5).setFullName("Mihaylov Mihail Mihaylovich").setBirthday("08.11.1999").setFaculty("Ecology").setAddress("Pogranichnaya 27").setPhone("08467").setCourse(2).setGroup(2111).build());
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