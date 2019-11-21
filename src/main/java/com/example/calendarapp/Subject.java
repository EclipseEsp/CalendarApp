package com.example.calendarapp;

public class Subject extends Homework{
    public String subjectName;

    public Subject(){
    }

    public Subject(String details, String duedate, String file, String subjectName) {
        super(details, duedate, file);
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return ("Subjects: " + this.getSubjectName());
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
