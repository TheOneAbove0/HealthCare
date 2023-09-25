package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.firestore.auth.User;

public class view_customer extends AppCompatActivity {
    TextView fillName, fillAge, fillAddress, fillProblem, fillPhone, fillFromHireDate, fillToHireDate, fillFromTime, fillToTime;
    Button takeOffer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);
        fillName = findViewById(R.id.fillName);
        fillAge = findViewById(R.id.fillAge);
        fillAddress = findViewById(R.id.fillAddress);
        fillProblem = findViewById(R.id.fillProblem);
        fillPhone = findViewById(R.id.fillPhone);
        fillFromHireDate = findViewById(R.id.fillFromHireDate);
        fillToHireDate = findViewById(R.id.fillToHireDate);
        fillFromTime = findViewById(R.id.fillFromTime);
        fillToTime = findViewById(R.id.fillToTime);

        takeOffer = findViewById(R.id.takeOffer);

        final String pName = getIntent().getStringExtra("pName");
        final String pAge = getIntent().getStringExtra("pAge");
        final String pAddress = getIntent().getStringExtra("pAddress");
        final String pProblem = getIntent().getStringExtra("pProblem");
        final String pPhone = getIntent().getStringExtra("pPhone");
        final String fromHireDate = getIntent().getStringExtra("fromHireDate");
        final String toHireDate = getIntent().getStringExtra("toHireDate");
        final String fromHireTime = getIntent().getStringExtra("fromHireTime");
        final String toHireTime = getIntent().getStringExtra("toHireTime");

        fillName.setText(pName);
        fillAge.setText(pAge);
        fillAddress.setText(pAddress);
        fillProblem.setText(pProblem);
        fillPhone.setText(pPhone);
        fillFromHireDate.setText(fromHireDate);
        fillToHireDate.setText(toHireDate);
        fillFromTime.setText(fromHireTime);
        fillToTime.setText(toHireTime);


        takeOffer.setOnClickListener(v->{
            Toast.makeText(this, "Offer Accepted", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(view_customer.this,MainActivity.class));
        });


    }
}