import java.io.*;
import java.util.*;

public class ProcessStuds {
    private Scanner in = new Scanner(System.in);
    private ArrayList<Student> list;
    private ListIterator<Student> iter;

    public ProcessStuds() { list = new ArrayList<>(); }

    Student enterStud(){
        Student s = new Student();
        System.out.println("Enter ID:");
        s.setId(in.nextInt());
        System.out.println("Enter full name:");
        s.setSurname(in.next());
        s.setName(in.next());
        s.setPatronymic(in.next());
        System.out.println("Enter birthday in format dd mm yyyy:");
        s.setBirthdayD(in.nextInt());
        s.setBirthdayM(in.nextInt());
        s.setBirthdayY(in.nextInt());
        System.out.println("Enter faculty:");
        in.nextLine(); // zaglushka
        s.setFaculty(in.nextLine());
        /*System.out.println("Enter adress:");
        s.setAdress(in.nextLine());
        System.out.println("Enter phone:");
        s.setPhone(in.next());
        System.out.println("Enter course:");
        s.setCourse(in.nextInt());
        System.out.println("Enter group:");
        s.setGroup(in.nextInt());*/
        return s;
    }

    void searchFacul(String f){
        for (Student s: list){
            if (s.getFaculty().equals(f)){
                System.out.println(s.toString());
            }
        }
    }

    void searchYear(int y){
        for (Student s: list){
            if (s.getBirthdayY()>y){
                System.out.println(s.toString());
            }
        }
    }

    void groupList(int g){
        ArrayList<Student> group = new ArrayList<>();
        for (Student s: list){
            if (s.getGroup()==g){
                group.add(s);
            }
        }
        if( list.isEmpty() ) System.out.println("No students from the group");
        else {
            StudNameCompar comparName = new StudNameCompar();
            group.sort(comparName);
            for (Student s: group) {
                System.out.println( s.toString() + "\n--------------------------------\n" );
            }
        }
    }

    void faculAgeList(){
        Comparator<Student> comparStud = new StudFaculCompar().thenComparing(new StudFaculCompar());
        list.sort(comparStud);
        //ComparatorChain chain = new ComparatorChain(); //не работает
        showAll();
    }

    void faculWithUniqueStud(){
        //получить все факультеты
        HashSet<String> facul = new HashSet<>();
        for (Student s: list) {
            facul.add(s.getFaculty());
        }

        //если есть повтор, добавляем факультеты в список на удаление
        HashSet<String> notUnique = new HashSet<>();
        for (int i=0; i<list.size()-1; i++) {
            for (int j=i+1; j<list.size(); j++) {
                if( list.get(i).getId() == list.get(j).getId() ) {
                    notUnique.add( list.get(i).getFaculty() );
                    notUnique.add( list.get(j).getFaculty() );
                }
            }
        }

        //удяляем факультеты, на которых учатся Коляны
        facul.removeAll(notUnique);

        //вывод
        for (String f: facul) {
            System.out.println(f);
        }
    }

    void countUniqueFacul(){
        //получить все факультеты
        HashSet<String> facul = new HashSet<>();
        for (Student s: list) {
            facul.add(s.getFaculty());
        }

        //если есть повтор, добавляем факультеты в список на удаление
        HashSet<String> notUnique = new HashSet<>();
        for (int i=0; i<list.size()-1; i++) {
            for (int j=i+1; j<list.size(); j++) {
                if (list.get(i).getId() == list.get(j).getId()) {
                    notUnique.add(list.get(i).getFaculty());
                    notUnique.add(list.get(j).getFaculty());
                }
            }
        }

        //удяляем факультеты, на которых учатся Коляны
        facul.removeAll(notUnique);

        //что осталось заносим в список
        //если этот фак уже есть - увеличить счётчик
        HashMap<String, Integer> unique = new HashMap<>();
        for (Student s: list) {
            if( facul.contains(s.getFaculty()) ) {
                Integer frequency = unique.get(s.getFaculty());
                unique.put(s.getFaculty(), frequency == null ? 1 : frequency + 1);
            }
        }

        //вывод
        for ( String f: unique.keySet() ) {
            System.out.println(f + " has " + unique.get(f) + " students");
        }
    }

    void inputTextFile(String fileName){
        try (FileReader fr = new FileReader(fileName)) {
            Scanner scan = new Scanner(fr);
            while ( scan.hasNextLine() ){
                Student s = new Student();
                String[] tmp = scan.nextLine().split(",");
                String[] birthday = tmp[4].split("=")[1].split("\\.");
                s.setId(Integer.parseInt(tmp[0].split("=")[1]));
                s.setSurname(tmp[1].split("=")[1]);
                s.setName(tmp[2].split("=")[1]);
                s.setPatronymic(tmp[3].split("=")[1]);
                s.setBirthdayD(Integer.parseInt(birthday[0]));
                s.setBirthdayM(Integer.parseInt(birthday[1]));
                s.setBirthdayY(Integer.parseInt(birthday[2]));
                s.setFaculty(tmp[5].split("=")[1]);
                s.setAdress(tmp[6].split("=")[1]);
                s.setPhone(tmp[7].split("=")[1]);
                s.setCourse(Integer.parseInt(tmp[8].split("=")[1]));
                s.setGroup(Integer.parseInt(tmp[9].split("=")[1].split("}")[0]));
                list.add(s);
            }
            System.out.println("Successfully read!");
        } catch (IOException ex) {
            System.err.println("Error reading file");
        }
    }

    void outputTextFile(String fileName){
        try (FileWriter fw = new FileWriter(fileName)) {
            iter = list.listIterator();
            while (iter.hasNext()){
                fw.write( iter.next().toString() );
            }
            System.out.println("Successfully written!");
        } catch (IOException ex) {
            System.err.println("Error writing file");
        }
    }

    public void inputBinaryFile(String fileName){
        try(FileInputStream fis = new FileInputStream(fileName)){
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(fis.available() != 0){
                list.add( (Student)ois.readObject() );
            }
            System.out.println("Successfully read!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error reading binary file");
        }
    }

    void outputBinaryFile(String fileName){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            iter = list.listIterator();
            while (iter.hasNext()){
                oos.writeObject( iter.next() );
            }
            oos.close();
            System.out.println("Successfully written!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing binary file");
        }
    }

    public void showAll(){
        list.forEach(System.out::println);
    }

    public void menu(){
        while (true) {
            System.out.println("" +
                    "1) Read from file\n" +
                    "2) Read from binary file\n" +
                    "3) Write to file\n" +
                    "4) Write to binary file\n" +
                    "5) Add record\n" +
                    "6) Delete record\n" +
                    "7) Show all records\n" +
                    "8)  A - Faculty list\n" +
                    "9)  B - Students after year\n" +
                    "10) C - Group list\n" +
                    "11) D - Sort by faculty and age\n" +
                    "12) E - Faculties with unique students\n" +
                    "13) F - E+number of students\n" +
                    "0) Exit");
            byte answ = in.nextByte();
            switch (answ) {
                case 0: System.exit(0);

                case 1: {
                    System.out.println("Enter name of file:");
                    inputTextFile(in.next());
                    break;
                }

                case 2: {
                    long start = System.currentTimeMillis();

                    System.out.println("Enter name of file: ");
                    inputBinaryFile(in.next());

                    System.out.println((System.currentTimeMillis() - start));

                    break;
                }

                case 3: {
                    System.out.println("Enter name of file: ");
                    outputTextFile(in.next());
                    break;
                }

                case 4: {
                    System.out.println("Enter name of file: ");
                    outputBinaryFile(in.next());
                    break;
                }

                case 5: {
                    list.add( enterStud() );
                    break;
                }

                case 6: {
                    if (list.isEmpty()) {
                        System.out.println("Nothing to delete");
                    } else {
                        System.out.println("Enter ID: ");
                        int id = in.nextInt();
                        for (int i = list.size() - 1; i >= 0; i--) {
                            Student s = list.get(i);
                            if (s.getId() == id) {
                                list.remove(list.get(i));
                                System.out.println("Element number " + i + " is successfully deleted");
                            }
                        }
                        break;
                    }
                }

                case 7: {
                    showAll();
                    break;
                }

                case 8: {
                    if(list.isEmpty()){
                        System.out.println("Nothing to find");
                    } else {
                        System.out.println("Enter faculty: ");
                        in.nextLine();  // zaglushka
                        searchFacul(in.nextLine());
                    }
                    break;
                }

                case 9: {
                    if(list.isEmpty()){
                        System.out.println("Nothing to find");
                    } else {
                        System.out.println("Enter year: ");
                        searchYear(in.nextInt());
                    }
                    break;
                }

                case 10: {
                    if(list.isEmpty()){
                        System.out.println("Nothing to find");
                    } else {
                        System.out.println("Enter group: ");
                        groupList(in.nextInt());
                    }
                    break;
                }

                case 11: {
                    faculAgeList();
                    break;
                }

                case 12: {
                    faculWithUniqueStud();
                    break;
                }

                case 13: {
                    countUniqueFacul();
                    break;
                }

                default: {
                    System.out.println("Wrong number. Try again!");
                }
            }
        }
    }
}
