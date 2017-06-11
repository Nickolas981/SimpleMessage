package com.example.nickolas.simplemessage;

import java.util.Date;



public class Message {

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


    public Message() {

    }

    public Message(String email, String body) {
        this.email = email;
        this.body = body;
        timeMessage = new Date().getTime();
    }

    @Override
    public String toString() {
        return email + ": " + body;
    }
}
