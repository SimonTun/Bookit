package com.bookit.demo;

import java.util.ArrayList;
import java.util.List;

public class BookingRequest {
    int id;
    int customerId;
    private List<Content> contents= new ArrayList<>();
    private String textMessage;


    public BookingRequest(int id, int customerId, List<Content> contents, String textMessage) {
        this.id = id;
        this.customerId = customerId;
        this.contents = contents;
        this.textMessage=textMessage;
    }

    public BookingRequest( int customerId, List<Content> contents, String textMessage) {
        this.customerId = customerId;
        this.contents = contents;
        this.textMessage=textMessage;

    }

    public BookingRequest (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
                "customerId=" + customerId +
                ", Content=" + contents +
                ", TextMessage=" + textMessage +
                '}';
    }
}
