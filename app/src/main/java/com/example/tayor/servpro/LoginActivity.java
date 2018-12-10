package com.example.tayor.servpro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{

    boolean VISIBLE_PASSWORD = false;

    private EditText mEmailView;
    private EditText mPasswordView;
    private TextView mTextView;
    private Button btn_login;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        // email and password
        mEmailView =  findViewById(R.id.email);
        mPasswordView =  findViewById(R.id.password);
        //get the button
        mTextView = findViewById(R.id.rigisterLink);
        btn_login = findViewById(R.id.email_sign_in_button);
        //button action
        btn_login.setOnClickListener(this);
        mTextView.setOnClickListener(this);

        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),Main_screen.class));
        }

    }

    @Override
    public void onClick(View v) {
        if(v==btn_login){
            logInUser();
        }
        if(v==mTextView){
            startActivity(new Intent(this,RegisterActivity.class));
        }

    }

    private void logInUser() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter your Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter your Password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("LogIn successfully...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),Main_screen.class));
                        }
                    }
                });
    }
}

