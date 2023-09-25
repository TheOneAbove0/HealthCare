package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class customer_profile extends AppCompatActivity {
    TextView kycForm, patientForm;
    TextView Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        kycForm = findViewById(R.id.kycForm);
        Logout = findViewById(R.id.Logout);
        patientForm = findViewById(R.id.patientForm);

        kycForm.setOnClickListener(v->{
            startActivity(new Intent(customer_profile.this,customer_kyc.class));

        });
        patientForm.setOnClickListener(v->{
            startActivity(new Intent(customer_profile.this,patient_form.class));
        });

        Logout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(customer_profile.this,ChooseActivity.class));
            finish();
        });
    }
}