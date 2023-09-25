package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ChooseActivity extends AppCompatActivity {

    TextView nurseSignUp, customerSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        nurseSignUp = findViewById(R.id.nurseSignUp);
        customerSignUp = findViewById(R.id.customerSignUp);




        nurseSignUp.setOnClickListener(v->{
            startActivity(new Intent(ChooseActivity.this,NurseSignUp.class));

            if(FirebaseAuth.getInstance().getCurrentUser() != null){
                startActivity(new Intent(ChooseActivity.this,MainActivity.class));

            }
        });

        customerSignUp.setOnClickListener(v->{
            startActivity(new Intent(ChooseActivity.this,CustomerSignUp.class));

            if(FirebaseAuth.getInstance().getCurrentUser() != null){
                startActivity(new Intent(ChooseActivity.this,customer_MainActivity.class));

            }
        });

    }
}