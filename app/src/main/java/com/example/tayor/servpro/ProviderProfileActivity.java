package com.example.tayor.servpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProviderProfileActivity extends BaseActivity {
ImageView editProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_profile);
        editProfile = findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadEditIntent();
            }
        });
    }

    private void loadEditIntent() {
        Intent intent = new Intent(ProviderProfileActivity.this,EditProfileActivity.class);
        startActivity(intent);
    }
}
