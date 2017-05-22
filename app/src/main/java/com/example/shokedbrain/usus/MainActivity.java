package com.example.shokedbrain.usus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.empty;

public class MainActivity extends AppCompatActivity {

    private ListView msgListView;
    private EditText msgEditText;
    private Button msgSendBtn;
    private MessageAdapter messageAdapter;
    // БД Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference msgDatabaseReference;
    private static final String TAG = "MyActivity";
    private ChildEventListener childEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msgSendBtn = (Button) findViewById(R.id.sendButton);
        msgEditText = (EditText) findViewById(R.id.msgEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        msgDatabaseReference = firebaseDatabase.getReference().child("messages");

        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Message message = dataSnapshot.getValue(Message.class);
                    messageAdapter.add(message);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            msgDatabaseReference.addChildEventListener(childEventListener);
        }
        /*
        msgDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                messageAdapter.add(message); // NULL ВЫДАЕТ
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/
        List<Message> messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, R.layout.item_message, messages);
        msgListView.setAdapter(messageAdapter);

        msgSendBtn.setOnClickListener(v -> {
            Message message = new Message(msgEditText.getText().toString()); // Отправка сообщений в БД
            msgDatabaseReference.push().setValue(message);
            msgEditText.setText(" ");
        });

        msgSendBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(msgEditText.getText().toString()))
                    msgSendBtn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
