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
import android.util.Patterns;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MyActivity";
    private EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email_input);
        pass = (EditText) findViewById(R.id.pass_input);
        mAuth = FirebaseAuth.getInstance(); // Initialization instance
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in");
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
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
                if (!isValidEmail(inp)) {
                    email.setError(getString(R.string.wrongFormat_email));
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
                if (TextUtils.isEmpty(inp) || inp.length() < 6) {
                    pass.setError(getString(R.string.emptyPass_error));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btn_log = (Button) findViewById(R.id.btn_login);
        try {
            View.OnClickListener oclBtnLog = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String log = email.getText().toString();
                    String password = pass.getText().toString();
                    mAuth.signInWithEmailAndPassword(log, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Logged!", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "No!", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                            });

                }
            };
            btn_log.setOnClickListener(oclBtnLog);
        } catch (IllegalArgumentException e) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.empty_error), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    public static boolean isValidEmail(String a) {
        CharSequence target = a;
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
