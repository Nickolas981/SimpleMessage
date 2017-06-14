package com.example.nickolas.simplemessage;

import java.util.Date;



public class MessageModel {

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    public long getTimeMessage() {
        return timeMessage;
    }

    private String email, body;
    private long timeMessage;


    public MessageModel() {

    }

    public MessageModel(String email, String body) {
        this.email = email;
        this.body = body;
        timeMessage = new Date().getTime();
    }

    @Override
    public String toString() {
        return email + ": " + body;
    }
}
