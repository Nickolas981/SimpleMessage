package com.example.nickolas.simplemessage;

/**
 * Created by Nickolas on 12.06.2017.
 */

public class User {
    String name, email, id;

    public User(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.id = uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public User() {
    }
}
