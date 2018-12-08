package com.example.tayor.servpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tayor.servpro.DAL.DatabaseLayer;
import com.example.tayor.servpro.Model.AESCrypt;
import com.example.tayor.servpro.Model.Provider;

public class RegisterActivity extends AppCompatActivity {
EditText companyName_et,email_et,phoneNumber_et,line1_et,line2_et,country_et,city_et,postal_et,password_et,confirmPassword_et;
Button register_bt;
DatabaseLayer databaseLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseLayer = new DatabaseLayer(this);
        mapIds();
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    void mapIds(){
        companyName_et = findViewById(R.id.company_name);
        email_et = findViewById(R.id.email);
        phoneNumber_et = findViewById(R.id.phone_number);
        line1_et = findViewById(R.id.line_1);
        line2_et = findViewById(R.id.line_2);
        country_et = findViewById(R.id.Country);
        city_et = findViewById(R.id.City);
        postal_et = findViewById(R.id.Postal);
        password_et = findViewById(R.id.password);
        confirmPassword_et = findViewById(R.id.confirm_password);
        register_bt = findViewById(R.id.register);
    }
     void register(){

         String companyName = companyName_et.getText().toString();
         String email = email_et.getText().toString();
         String phoneNumber = phoneNumber_et.getText().toString();
         String line1 = line1_et.getText().toString();
         String line2 = line2_et.getText().toString();
         String country = country_et.getText().toString();
         String city = city_et.getText().toString();
         String postal = postal_et.getText().toString();
         String password = password_et.getText().toString();
         String confirmPassword = confirmPassword_et.getText().toString();

         if(companyName.isEmpty())
             Toast.makeText(RegisterActivity.this, "Company name required", Toast.LENGTH_SHORT).show();
         else if(email.isEmpty())
             Toast.makeText(RegisterActivity.this, "Email Address required", Toast.LENGTH_SHORT).show();
         else if(phoneNumber.isEmpty())
             Toast.makeText(RegisterActivity.this, "Phone Number required", Toast.LENGTH_SHORT).show();
         else if(line1.isEmpty())
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
             String passwordEncrypt = "";
             try {
                 passwordEncrypt = AESCrypt.encrypt(password);
             } catch (Exception ex){
                 Log.d("pass encrypt",ex.getMessage());
             }
             Provider provider = new Provider();
             provider.setCompany_name(companyName);
             provider.setEmail(email);
             provider.setPhone(phoneNumber);
             provider.setLine1(line1);
             provider.setLine2(line2);
             provider.setCountry(country);
             provider.setCity(city);
             provider.setPostal(postal);
             provider.setPassword(passwordEncrypt);
             if(databaseLayer.insertProvider(provider)) {
                 Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                 startActivity(intent);
             } else
                 Toast.makeText(this, "Error registering provider, kindly review your data", Toast.LENGTH_SHORT).show();
         }
     }
}
