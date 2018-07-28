import java.util.Comparator;

class StudNameCompar implements Comparator<Student> {
    public int compare(Student a, Student b){
        if (a.getSurname().equals(b.getSurname()) ) {
            if (a.getName().equals(b.getName())) {
                return a.getPatronymic().compareTo(b.getPatronymic());
            } else {
                return a.getName().compareTo(b.getName());
            }
        } else {
            return a.getSurname().compareTo(b.getSurname());
        }
    }
}

class StudAgeCompar implements Comparator<Student> {
    public int compare(Student a, Student b) {
        if (a.getBirthdayY() == b.getBirthdayY()) {
            if (a.getName().equals(b.getName())) {
                /*return a.getBirthdayD() > b.getBirthdayD() ? 1 : -1;*/
                if (a.getBirthdayD() > b.getBirthdayD()) return 1;
                else if (a.getBirthdayD() < b.getBirthdayD()) return -1;
                else return 0;
            } else {
                return a.getBirthdayM() > b.getBirthdayM() ? 1 : -1;
            }
        } else {
            return a.getBirthdayY() > b.getBirthdayY() ? 1 : -1;
        }
    }
}

class StudFaculCompar implements Comparator<Student> {
    public int compare(Student a, Student b) {
        return a.getFaculty().compareTo(b.getFaculty());
    }
}