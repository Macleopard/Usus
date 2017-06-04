package com.example.shokedbrain.usus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
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
    private Button btn_log, btn_reg;
    private EditText email, pass;
    private Intent signUp, main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        signUp = new Intent(this, SignUpActivity.class);
        main = new Intent(this, MainActivity.class);
        email = (EditText) findViewById(R.id.email_input);
        pass = (EditText) findViewById(R.id.pass_input);

        initFirebase();

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
        btn_log = (Button) findViewById(R.id.btn_login);
        btn_reg = (Button) findViewById(R.id.btn_reg);

        btn_log.setOnClickListener(v -> login());
        btn_reg.setOnClickListener(v -> {
            startActivity(signUp);
        });


    }

    public void login() {
        String log = email.getText().toString();
        String password = pass.getText().toString();
        try {
            mAuth.signInWithEmailAndPassword(log, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                main.putExtra("username", email.getText().toString());
                                startActivity(main);
                                pass.setText("");

                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
        } catch (IllegalArgumentException e) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.empty_error), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void initFirebase() {
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
