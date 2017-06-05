package com.example.shokedbrain.usus;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shokedbrain on 23.05.17.
 */

public class MessageHolder extends RecyclerView.ViewHolder {
    private final TextView mMsg;
    private final TextView mUsr;
    private final TextView mTime;

    public MessageHolder(View view) {
        super(view);
        mUsr = (TextView) view.findViewById(R.id.username_field);
        mMsg = (TextView) view.findViewById(R.id.message_field);
        mTime = (TextView) view.findViewById(R.id.time_field);
    }

    void setUsr(String text, int color) {
        mUsr.setTextColor(color);
        mUsr.setText(text);
    }

    void setUsr(String text) {
        mUsr.setTextColor(Color.parseColor("#000000"));
        mUsr.setText(text);
    }

    void setMsg(String text) {
        mMsg.setText(text);
    }

    void setTime(String text) {
        mTime.setText(text);
    }

    public void rightSide() {
        mUsr.setGravity(Gravity.END);
        mMsg.setGravity(Gravity.END);
        mTime.setGravity(Gravity.END);
    }

    public void leftSide() {
        mUsr.setGravity(Gravity.START);
        mMsg.setGravity(Gravity.START);
        mTime.setGravity(Gravity.START);
    }
}
