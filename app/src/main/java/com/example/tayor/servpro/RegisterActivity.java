package com.example.tayor.servpro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tayor.servpro.Model.Provider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText companyName_et, email_et,phoneNumber_et, address_et,country_et,
                     city_et,postal_et,password_et,confirmPassword_et;
    private Button register_bt;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //find all values by id
        mapIds();
        //get firbase instance
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.register_progress);
        progressBar.setVisibility(View.GONE);
        //button onClick method
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        //get the text
        final String companyName = companyName_et.getText().toString();
        final String email = email_et.getText().toString();
        final String phoneNumber = phoneNumber_et.getText().toString();
        final String address = address_et.getText().toString();
        final String country = country_et.getText().toString();
        final String city = city_et.getText().toString();
        final String postal = postal_et.getText().toString();
        final String password = password_et.getText().toString();
        String confirmPassword = confirmPassword_et.getText().toString();
        //check if the entered information is empty
        if(companyName.isEmpty())
            Toast.makeText(RegisterActivity.this, "Company name required", Toast.LENGTH_SHORT).show();
        else if(email.isEmpty())
            Toast.makeText(RegisterActivity.this, "Email Address required", Toast.LENGTH_SHORT).show();
        else if(phoneNumber.isEmpty())
            Toast.makeText(RegisterActivity.this, "Phone Number required", Toast.LENGTH_SHORT).show();
        else if(address.isEmpty())
            Toast.makeText(RegisterActivity.this, "Address Line required", Toast.LENGTH_SHORT).show();
        else if(country.isEmpty())
            Toast.makeText(RegisterActivity.this, "Country required", Toast.LENGTH_SHORT).show();
        else if(city.isEmpty())
            Toast.makeText(RegisterActivity.this, "City required", Toast.LENGTH_SHORT).show();
        else if(postal.isEmpty())
            Toast.makeText(RegisterActivity.this, "Postal code required", Toast.LENGTH_SHORT).show();
        else if(password.isEmpty())
            Toast.makeText(RegisterActivity.this, "You have to choose a password", Toast.LENGTH_SHORT).show();
        else if(confirmPassword.isEmpty())
            Toast.makeText(RegisterActivity.this, "Re-enter password", Toast.LENGTH_SHORT).show();
        else if(!(password.equals(confirmPassword)))
            Toast.makeText(RegisterActivity.this, "Password entered doesn't match, re-try", Toast.LENGTH_SHORT).show();
        else{
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()) {
                                Provider provider = new Provider(companyName, email, phoneNumber, address, country, city, postal, password);

                                FirebaseDatabase.getInstance().getReference("providersAccount")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(provider).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this,"Registered!",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterActivity.this,Main_screen.class));
                                        }
                                    }
                                });

                            }else{
                                Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),Main_screen.class));
        }

    }

    public void mapIds(){
        companyName_et = findViewById(R.id.company_name);
        email_et = findViewById(R.id.email);
        phoneNumber_et = findViewById(R.id.phone_number);
        address_et = findViewById(R.id.address);
        country_et = findViewById(R.id.Country);
        city_et = findViewById(R.id.City);
        postal_et = findViewById(R.id.Postal);
        password_et = findViewById(R.id.password);
        confirmPassword_et = findViewById(R.id.confirm_password);
        register_bt = findViewById(R.id.register);
    }
}
