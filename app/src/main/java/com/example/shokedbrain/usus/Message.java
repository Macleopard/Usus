package com.example.shokedbrain.usus;

/**
 * Created by shokedbrain on 22.05.17.
 */

public class Message {
    private String user, text;

    public Message() {
    }

    public Message(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
