package com.bookit.demo;

import java.util.List;
public class Content {
    int id;
    int bookingRequestId;
    private List<SUBJECT> subjects;
    private String textMessage;
    private List<SubjectClass> subjectClasses;



    public Content(int id, int bookingRequestId, List<SUBJECT> subjects, String textMessage) {
        this.id = id;
        this.bookingRequestId = bookingRequestId;
        this.subjects = subjects;
        this.textMessage = textMessage;
    }

    public Content(List<SubjectClass> subjectClasses){
        this.subjectClasses = subjectClasses;
    }

    public Content( int bookingRequestId, List<SUBJECT> subjects, String textMessage) {
        this.bookingRequestId = bookingRequestId;
        this.subjects = subjects;
        this.textMessage = textMessage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingRequestId() {
        return bookingRequestId;
    }

    public void setBookingRequestId(int bookingRequestId) {
        this.bookingRequestId = bookingRequestId;
    }

    public List<SUBJECT> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SUBJECT> subjects) {
        this.subjects = subjects;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
