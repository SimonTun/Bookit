package com.bookit.demo;

import java.util.List;

public class BookingContent {


    private String date;
    private String startTime;
    private String endTime;
    private List<Content> contents;
    private String textMessage;
    private String employeeFirstName;
    private String employeeLastName;
    private String pictureName;         // 1.jpg for employeeID = 1
    private String videoLink;

    public BookingContent(String date, String startTime, String endTime, List<Content> contents, String textMessage, String employeeFirstName, String employeeLastName, String pictureName, String videoLink) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contents = contents;
        this.textMessage = textMessage;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.pictureName = pictureName;
        this.videoLink = videoLink;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}