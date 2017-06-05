package com.example.shokedbrain.usus;

/**
 * Created by shokedbrain on 22.05.17.
 */

class Message {
    private String user, text, time;

    public Message() {
    }

    public Message(String user, String text, String time) {
        this.user = user;
        this.text = text;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
