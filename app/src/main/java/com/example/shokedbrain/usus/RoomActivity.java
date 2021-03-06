package com.example.shokedbrain.usus;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomActivity extends AppCompatActivity {
    @BindView(R.id.msgListView)
    RecyclerView msgListView;
    @BindView(R.id.msgEditText)
    EditText msgEditText;
    @BindView(R.id.sendButton)
    ImageButton msgSendBtn;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter mAdapter;
    // БД Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference msgDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.hide();
        } catch (NullPointerException e) {
        }
        mLayoutManager = new LinearLayoutManager(this);
        msgListView.setLayoutManager(mLayoutManager);
        initFirebase();
        String user = getIntent().getStringExtra("user");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");

        mAdapter = new MessageAdapter(Message.class, R.layout.item_message, MessageHolder.class, msgDatabaseReference, user);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mLayoutManager.smoothScrollToPosition(msgListView, null, mAdapter.getItemCount());
            }
        });
        msgListView.setAdapter(mAdapter);
        msgSendBtn.setOnClickListener(v -> {
            Message message = new Message(getIntent().getStringExtra("user"), msgEditText.getText().toString(), df.format(calendar.getTime()));
            msgDatabaseReference.push().setValue(message);
            msgEditText.setText("");
        });
        msgEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(msgEditText.getText().toString()))
                    msgSendBtn.setEnabled(false);
                else
                    msgSendBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    protected void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        msgDatabaseReference = firebaseDatabase.getReference().child(getIntent().getStringExtra("room_name"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
