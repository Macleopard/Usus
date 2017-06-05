package com.example.shokedbrain.usus;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {
    @BindView(R.id.roomList)
    RecyclerView roomList;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference msgDatabaseReference;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.hide();
        } catch (NullPointerException e) {
        }
        mLayoutManager = new LinearLayoutManager(this);
        roomList.setLayoutManager(mLayoutManager);
        initFirebase();
        Room room = new Room("dasda");
        msgDatabaseReference.push().setValue(room);

        mAdapter = new FirebaseRecyclerAdapter<Room, RoomHolder>(Room.class, R.layout.item_room, RoomHolder.class, msgDatabaseReference) {
            @Override
            protected void populateViewHolder(RoomHolder roomHolder, Room room, int position) {
                roomHolder.setRoomName(room.getRoomName());
            }
        };
        roomList.setAdapter(mAdapter);

    }

    protected void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        msgDatabaseReference = firebaseDatabase.getReference();
    }
}
