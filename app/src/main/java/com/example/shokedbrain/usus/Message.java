package com.example.shokedbrain.usus;

/**
 * Created by shokedbrain on 22.05.17.
 */

public class Message {
    private String text;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
