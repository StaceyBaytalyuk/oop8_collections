package oop.lab8;

import java.util.Comparator;

class StudentNameComparator implements Comparator<Student> {
    public int compare(Student a, Student b){
        if (a.getSurname().equals(b.getSurname()) ) {
            if (a.getFirstName().equals(b.getFirstName())) {
                return a.getSecondName().compareTo(b.getSecondName());
            } else {
                return a.getFirstName().compareTo(b.getFirstName());
            }
        } else {
            return a.getSurname().compareTo(b.getSurname());
        }
    }
}

class StudentAgeComparator implements Comparator<Student> {
    public int compare(Student a, Student b) {
        return a.getBirthday().compareTo(b.getBirthday());
    }
}

class StudentFacultyComparator implements Comparator<Student> {
    public int compare(Student a, Student b) {
        return a.getFaculty().compareTo(b.getFaculty());
    }
}