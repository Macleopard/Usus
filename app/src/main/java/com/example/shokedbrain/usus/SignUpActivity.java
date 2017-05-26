package com.example.shokedbrain.usus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText email, pass, repeatPass;
    private Button regButton;
    private FirebaseAuth mAuth;
    private Intent login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        login = new Intent(this, LoginActivity.class);
        initFirebase();
        email = (EditText) findViewById(R.id.email_field);
        pass = (EditText) findViewById(R.id.pass_field);
        repeatPass = (EditText) findViewById(R.id.repeatpsw_field);
        regButton = (Button) findViewById(R.id.regButton);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isValidEmail(email.getText().toString()))
                    pass.setError(getString(R.string.wrongFormat_email));

                if (TextUtils.isEmpty(email.getText().toString()) || email.getText().toString().length() < 6)
                    pass.setError(getString(R.string.emptyEmail_error));

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
                if (TextUtils.isEmpty(pass.getText().toString()) || pass.getText().toString().length() < 6)
                    pass.setError(getString(R.string.emptyPass_error));
                if (!pass.getText().toString().equals(repeatPass.getText().toString()))
                    pass.setError(getString(R.string.password_nomatch));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        repeatPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(repeatPass.getText().toString()) || repeatPass.getText().toString().length() < 6)
                    repeatPass.setError(getString(R.string.emptyPass_error));
                if (!repeatPass.getText().toString().equals(pass.getText().toString()))
                    repeatPass.setError(getString(R.string.password_nomatch));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regButton.setOnClickListener(v -> login());

    }

    public void login() {
        try {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(login);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (IllegalArgumentException e) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.empty_error), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public static boolean isValidEmail(String a) {
        CharSequence target = a;
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
