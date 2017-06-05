package com.example.shokedbrain.usus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by shokedbrain on 05.06.17.
 */

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageHolder> {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");
    Context context;

    // Стандартный конструктор, в который передаем контекст для проверки имени пользователя
    public MessageAdapter(Class<Message> message, int layout, Class<MessageHolder> messageHolder, Query ref, Context context) {
        super(message, layout, messageHolder, ref);
        this.context = context;
    }

    protected void populateViewHolder(MessageHolder messageHolder, Message message, int position) {
        Intent intent = ((Activity) context).getIntent();
        if (message.getUser().indexOf(intent.getStringExtra("username")) != -1) {
            messageHolder.setUsr(message.getUser(), Color.parseColor("#e6005c"));
            messageHolder.rightSide();
        } else {
            messageHolder.setUsr(message.getUser());
            messageHolder.leftSide();
        }
        messageHolder.setMsg(message.getText());
        messageHolder.setTime(df.format(calendar.getTime()));
    }
}
