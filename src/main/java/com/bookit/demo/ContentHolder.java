package com.bookit.demo;

import java.util.ArrayList;
import java.util.List;

public class ContentHolder {
    int id;
    int bookingRequestId;
    private List<Content> contents= new ArrayList<>();
    private String textMessage;


    public ContentHolder(int id, int bookingRequestId, List<Content> contents, String textMessage) {
        this.id = id;
        this.bookingRequestId = bookingRequestId;
        this.contents = contents;
        this.textMessage=textMessage;
    }

    public ContentHolder(int bookingRequestId, List<Content> contents, String textMessage) {
        this.bookingRequestId = bookingRequestId;
        this.contents = contents;
        this.textMessage=textMessage;

    }

    public ContentHolder(){

    }
    public ContentHolder(int bookingRequestId){
        this.bookingRequestId=bookingRequestId;

    }

    public int getBookingRequestId() {
        return bookingRequestId;
    }

    public void setBookingRequestId(int bookingRequestId) {
        this.bookingRequestId = bookingRequestId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "bookingRequestId=" + bookingRequestId +
                ", Content=" + contents +
                ", TextMessage=" + textMessage +
                '}';
    }
}
