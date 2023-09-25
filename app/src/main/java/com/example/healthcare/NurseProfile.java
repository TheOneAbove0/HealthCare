package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class NurseProfile extends AppCompatActivity {
    TextView kycForm;
    TextView Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_profile);

        kycForm = findViewById(R.id.kycForm);
        Logout = findViewById(R.id.Logout);

        kycForm.setOnClickListener(v->{
            startActivity(new Intent(NurseProfile.this,Nurse_KYC.class));

        });

        Logout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(NurseProfile.this,ChooseActivity.class));
            finish();
        });
    }
}