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

    public String getName() {
        return name;
    }
    public String getUid() {
        return uid;
    }

    private String email;
    private String body;
    private String name;


    private String uid;
    private long timeMessage;

    public MessageModel() {

    }

    public MessageModel(String email, String body) {
        this.email = email;
        this.body = body;

        this.name = Firebasse.getUser().name;
        timeMessage = new Date().getTime();
        uid = Firebasse.getuId();
    }

    @Override
    public String toString() {
        return email + ": " + body;
    }


    @Override
    public boolean equals(Object obj) {
        MessageModel m = (MessageModel) obj;

        return m.name == name && m.email == m.email
                &&m.body == body && uid.equals(m.uid)
                &&m.timeMessage == timeMessage;
    }
}
