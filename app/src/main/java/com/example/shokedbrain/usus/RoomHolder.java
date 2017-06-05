package com.example.shokedbrain.usus;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shokedbrain on 05.06.17.
 */

public class RoomHolder extends RecyclerView.ViewHolder {
    private final TextView roomName;

    public RoomHolder(View itemView) {
        super(itemView);
        roomName = (TextView) itemView.findViewById(R.id.room_field);
    }

    public void setRoomName(String name) {
        roomName.setText(name);
    }
}
