package com.example.shokedbrain.usus;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

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
        msgDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                List<String> roomsNames = Stream.of(data).map(entry -> entry.getKey()).collect(Collectors.toList());
                initList(roomsNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    protected void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        msgDatabaseReference = firebaseDatabase.getReference();
    }

    private void initList(List<String> roomsNames) {
        Intent main = new Intent(this, RoomActivity.class);
        String username = getIntent().getStringExtra("username");
        mAdapter = new FirebaseRecyclerAdapter<Room, RoomHolder>(Room.class, R.layout.item_room, RoomHolder.class, msgDatabaseReference) {
            @Override
            protected void populateViewHolder(RoomHolder roomHolder, Room room, int position) {
                roomHolder.setRoomName(roomsNames.get(position));
                roomHolder.mView.setOnClickListener(v -> {
                    //Toast.makeText(getApplicationContext(),roomsNames.get(position),Toast.LENGTH_SHORT).show();
                    main.putExtra("room_name", roomsNames.get(position));
                    main.putExtra("user", username);
                    startActivity(main);
                });
            }
        };
        roomList.setAdapter(mAdapter);
    }
}
