package com.example.calendarapp;

public class Homework {
    public String details;
    public String duedate;
    public String file;

    public Homework() {
    }

    public Homework(String details, String duedate, String file) {
        this.details = details;
        this.duedate = duedate;
        this.file = file;
    }

    @Override
    public String toString() {
        return ("Homework(details): " + this.getDetails());
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
