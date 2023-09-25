package com.example.healthcare;

import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class view_nurse extends AppCompatActivity {
    TextView fillName, fillAge, fillAddress, fillPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nurse);

        fillName = findViewById(R.id.fillName);
        fillAge = findViewById(R.id.fillAge);
        fillAddress = findViewById(R.id.fillAddress);
        fillPhone = findViewById(R.id.fillPhone);

        final String nName = getIntent().getStringExtra("nName");
        final String nDOB = getIntent().getStringExtra("nDOB");
        final String nAddress = getIntent().getStringExtra("nAddress");
        final String nPhone = getIntent().getStringExtra("nPhone");

        fillName.setText(nName);
        fillAge.setText(nDOB);
        fillAddress.setText(nAddress);
        fillPhone.setText(nPhone);

    }
}