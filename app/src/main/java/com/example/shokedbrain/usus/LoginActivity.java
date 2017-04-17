package com.example.shokedbrain.usus;

import android.app.Activity;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MyActivity";
    private EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.email_input);
        pass = (EditText)findViewById(R.id.pass_input);
        mAuth = FirebaseAuth.getInstance(); // Initialization instance
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    Log.d(TAG,"onAuthStateChanged:signed_in");
                }
                else
                {
                    Log.d(TAG,"onAuthStateChanged:signed_out");
                }
            }
        };

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inp = email.getText().toString();
                if (TextUtils.isEmpty(inp)){
                    email.setError(getString(R.string.emptyEmail_error));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inp = pass.getText().toString();
                if (TextUtils.isEmpty(inp)){
                    pass.setError(getString(R.string.emptyPass_error));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btn_log = (Button)findViewById(R.id.btn_login);
        View.OnClickListener oclBtnLog = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),"DEV", Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        btn_log.setOnClickListener(oclBtnLog);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }
}
