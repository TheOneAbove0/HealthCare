package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class customerOTP_verification extends AppCompatActivity {

    private EditText otpET1, otpET2, otpET3, otpET4, otpET5, otpET6;
    private TextView resendBtn;

    String getBackEndOtp;

    //true after every 60 second
    private boolean resendEnabled = false;

    //resend time in seconds
    private final int resendTimer = 60;

    private int selectedETPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_otp_verification);

        otpET1 = findViewById(R.id.otpET1);
        otpET2 = findViewById(R.id.otpET2);
        otpET3 = findViewById(R.id.otpET3);
        otpET4 = findViewById(R.id.otpET4);
        otpET5 = findViewById(R.id.otpET5);
        otpET6 = findViewById(R.id.otpET6);

        resendBtn = findViewById(R.id.resendBtn);

        final Button verifyBtn = findViewById(R.id.verifyBtn);
        final TextView otpEmail = findViewById(R.id.otpEmail);
        final TextView otpMobile = findViewById(R.id.otpMobile);

        //getting email and mobile from nurse signup activity through intent

        final String getEmail = getIntent().getStringExtra("email");
        final String getMobile = getIntent().getStringExtra("mobile");
        getBackEndOtp = getIntent().getStringExtra("backEndOtp");

        // setting email and mobile to textview
        otpEmail.setText(getEmail);
        otpMobile.setText(String.format("+977-%s", (getMobile)));

        otpET1.addTextChangedListener(textWatcher);
        otpET2.addTextChangedListener(textWatcher);
        otpET3.addTextChangedListener(textWatcher);
        otpET4.addTextChangedListener(textWatcher);
        otpET5.addTextChangedListener(textWatcher);
        otpET6.addTextChangedListener(textWatcher);

        //default open keyboard at otpET1
        showKeyBoard(otpET1);

        //start resend count down timer
        startCountDownTimer();

        resendBtn.setOnClickListener(v -> {
            PhoneAuthProvider.getInstance().verifyPhoneNumber("+977"+getMobile, 60L, TimeUnit.SECONDS, customerOTP_verification.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    Toast.makeText(customerOTP_verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String newBackEndOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    getBackEndOtp = newBackEndOtp;
                    Toast.makeText(customerOTP_verification.this, "OTP send successful", Toast.LENGTH_SHORT).show();
                }
            });

            //stat new resend count down timer
            startCountDownTimer();

        });

        verifyBtn.setOnClickListener(v -> {

            final String generateOtp = otpET1.getText().toString() +
                    otpET2.getText().toString() +
                    otpET3.getText().toString() +
                    otpET4.getText().toString() +
                    otpET5.getText().toString() +
                    otpET6.getText().toString();

            if (generateOtp.length() == 6) {
                //handle your otp verification here
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getBackEndOtp, generateOtp);

                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), customer_MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(customerOTP_verification.this, "Enter the Correct OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void showKeyBoard(EditText otpET) {
        otpET.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer() {
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTimer * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code(" + (millisUntilFinished / 1000 + ")"));
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
//                resendBtn.setTextColor(getResources().getColor(R.color.white));
            }
        }.start();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.length() > 0) {

                if (selectedETPosition == 0) {
                    selectedETPosition = 1;
                    showKeyBoard(otpET2);
                } else if (selectedETPosition == 1) {
                    selectedETPosition = 2;
                    showKeyBoard(otpET3);
                } else if (selectedETPosition == 2) {
                    selectedETPosition = 3;
                    showKeyBoard(otpET4);
                } else if (selectedETPosition == 3) {
                    selectedETPosition = 4;
                    showKeyBoard(otpET5);
                } else if (selectedETPosition == 4) {
                    selectedETPosition = 5;
                    showKeyBoard(otpET6);
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_DEL) {

            if (selectedETPosition == 5) {
                selectedETPosition = 4;

                showKeyBoard(otpET5);
            } else if (selectedETPosition == 4) {
                selectedETPosition = 3;

                showKeyBoard(otpET4);
            } else if (selectedETPosition == 3) {
                selectedETPosition = 2;

                showKeyBoard(otpET3);
            } else if (selectedETPosition == 2) {
                selectedETPosition = 1;

                showKeyBoard(otpET2);
            } else if (selectedETPosition == 1) {
                selectedETPosition = 0;

                showKeyBoard(otpET1);
            }
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }

    }
}