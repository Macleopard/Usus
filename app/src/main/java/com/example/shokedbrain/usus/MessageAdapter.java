package com.example.shokedbrain.usus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageHolder> {
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");
    private String user;

    // Стандартный конструктор, в который передаем контекст для проверки имени пользователя

    public MessageAdapter(Class<Message> message, int layout, Class<MessageHolder> messageHolder, Query ref, String user) {
        super(message, layout, messageHolder, ref);
        this.user = user;
    }

    protected void populateViewHolder(MessageHolder messageHolder, Message message, int position) {
        if (message.getUser().contains(user))
            messageHolder.setUsr(message.getUser(), Color.parseColor("#e6005c"));
        else
            messageHolder.setUsr(message.getUser());
        messageHolder.setMsg(message.getText());
        messageHolder.setTime(df.format(calendar.getTime()));
    }
}
