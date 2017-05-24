package com.example.shokedbrain.usus;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shokedbrain on 23.05.17.
 */

public class MessageHolder extends RecyclerView.ViewHolder {
    private final TextView mMsg;
    private final TextView mUsr;

    public MessageHolder(View view) {
        super(view);
        mUsr = (TextView) view.findViewById(R.id.username);
        mMsg = (TextView) view.findViewById(R.id.message);
    }

    public void setUsr(String text, int color) {
        mUsr.setTextColor(color);
        mUsr.setText(text);
    }

    public void setUsr(String text) {
        mUsr.setTextColor(Color.parseColor("#000000"));
        mUsr.setText(text);
    }

    public void setMsg(String text) {
        mMsg.setText(text);
    }
}
