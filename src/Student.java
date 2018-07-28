import java.io.Serializable;

public class Student implements Serializable {
    private int id = 0;
    private String surname = "0", name = "0", patronymic = "0";
    private int[] birthday = new int[3];
    private String faculty = "0", adress = "0", phone= "0";
    private int course = 0, group = 0;

    public Student(int id, String surname, String name, String patronymic, int[] birthday,
                    String faculty, String adress, String phone, int course, int group){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.faculty = faculty;
        this.adress = adress;
        this.phone = phone;
        this.course = course;
        this.group = group;
    }

    public Student(){ }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", surname=" + surname +
                ", name=" + name +
                ", patronymic=" + patronymic +
                ", birthday=" + birthday[0]+"."+birthday[1]+"."+birthday[2] +
                ", faculty=" + faculty +
                ", adress=" + adress +
                ", phone=" + phone +
                ", course=" + course +
                ", group=" + group +
                "}\n";
    }

    public int getId() {
        return id;
    }

    public String getSurname() { return surname; }

    public String getName() { return name; }

    public String getPatronymic() { return patronymic; }

    public int getBirthdayD() { return birthday[0]; }

    public int getBirthdayM() { return birthday[1]; }

    public int getBirthdayY() { return birthday[2]; }

    public String getFaculty() { return faculty; }

    public String getAdress() { return adress; }

    public String getPhone() { return phone; }

    public int getCourse() { return course; }

    public int getGroup() { return group; }


    public void setId(int id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setBirthdayD(int day) {
        this.birthday[0] = day;
    }

    public void setBirthdayM(int month) {
        this.birthday[1] = month;
    }

    public void setBirthdayY(int year) {
        this.birthday[2] = year;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void setGroup(int group) {
        this.group = group;
    }


}