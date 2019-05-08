package oop.lab8;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Student implements Serializable {
    private int id;
    private String surname, firstName, secondName;
    private LocalDate birthday;
    private String faculty, address, phone;
    private int course, group;
    private static final long serialVersionUID = 1;

    private Student(StudentBuilder builder) {
        id = builder.id;
        surname = builder.surname;
        firstName = builder.firstName;
        secondName = builder.secondName;
        birthday = builder.birthday;
        faculty = builder.faculty;
        address = builder.address;
        phone = builder.phone;
        course = builder.course;
        group = builder.group;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return "Student {" +
                "id=" + id +
                ", surname=" + surname +
                ", firstName=" + firstName +
                ", secondName=" + secondName +
                ", birthday=" +  formatter.format(birthday) +
                ", faculty=" + faculty +
                ", address=" + address +
                ", phone=" + phone +
                ", course=" + course +
                ", group=" + group +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getGroup() {
        return group;
    }


    public static class StudentBuilder {
        private int id, course, group;
        private LocalDate birthday;
        private String surname="-", firstName="-", secondName="-", faculty="-", address="-", phone="-";

        public StudentBuilder setID(int id) {
            this.id = id;
            return this;
        }

        public StudentBuilder setFullName(String fullName) {
            String[] arr = fullName.split(" ");
            this.surname = arr[0];
            this.firstName = arr[1];
            this.secondName = arr[2];
            return this;
        }

        public StudentBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public StudentBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentBuilder setSecondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public StudentBuilder setBirthday(String birthday) {
            this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return this;
        }

        public StudentBuilder setFaculty(String faculty) {
            this.faculty = faculty;
            return this;
        }

        public StudentBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public StudentBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public StudentBuilder setCourse(int course) {
            this.course = course;
            return this;
        }

        public StudentBuilder setGroup(int group) {
            this.group = group;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}