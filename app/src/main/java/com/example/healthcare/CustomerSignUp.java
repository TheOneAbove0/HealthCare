package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class CustomerSignUp extends AppCompatActivity {
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);

        final EditText fullName = findViewById(R.id.fullNameET);
        final EditText email = findViewById(R.id.emailET);
        final EditText mobile = findViewById(R.id.mobileET);
        final AppCompatButton signUpBtn = findViewById(R.id.signUpBtn);
        final EditText password = findViewById(R.id.passwordET);
        final EditText conPassword = findViewById(R.id.conPasswordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final ImageView conPasswordIcon = findViewById(R.id.conPasswordIcon);

        passwordIcon.setOnClickListener(v->{

            //checking if password is showing or not
            if(passwordShowing){
                passwordShowing = false;
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordIcon.setImageResource(R.drawable.show_password);
            }else{
                passwordShowing = true;
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordIcon.setImageResource(R.drawable.password_hide);
            }
            //move the cursor at last of the text
            password.setSelection(password.length());
        });

        conPasswordIcon.setOnClickListener(v->{

            //checking if password is showing or not
            if(conPasswordShowing){
                conPasswordShowing = false;
                conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                conPasswordIcon.setImageResource(R.drawable.show_password);
            }else{
                conPasswordShowing = true;
                conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                conPasswordIcon.setImageResource(R.drawable.password_hide);
            }
            //move the cursor at last of the text
            conPassword.setSelection(conPassword.length());
        });

        signUpBtn.setOnClickListener(v->{
            final String getFullNameTxt = fullName.getText().toString();
            final String getMobileTxt = mobile.getText().toString();
            final String getEmailTxt = email.getText().toString();
            final String getPassword = password.getText().toString();
            final String getConPassword = conPassword.getText().toString();

            final String emailExpression = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

            //validation for name
            if (getFullNameTxt.isEmpty()) {
                fullName.setError("Name is required");

            }
            // validation for email
            if (!(getEmailTxt.matches(emailExpression) && getEmailTxt.length() > 0)) {
                email.setError("Enter valid email address");
            }
            //validation for phone
            if (getMobileTxt.length() == 10) {
                String pNumber = "+977" + mobile.getText().toString();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(pNumber, 60L, TimeUnit.SECONDS, CustomerSignUp.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(CustomerSignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String backEndOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Intent intent = new Intent(CustomerSignUp.this, customerOTP_verification.class);
                        intent.putExtra("mobile", getMobileTxt);
                        intent.putExtra("email", getEmailTxt);
                        intent.putExtra("backEndOtp",backEndOtp);
                        intent.putExtra("password",getPassword);
                        startActivity(intent);
                    }
                });

            } else {
                mobile.setError("Enter valid Phone Number");
            }

            //validation for password
            if (getPassword.length() < 6 && getConPassword.length() < 6) {
                password.setError("Please enter 6 or more than 6 character");
                conPassword.setError("Please enter 6 or more than 6 character");
            }
            //check if password and conPassword are same
            if (!getPassword.equals(getConPassword)) {

                conPassword.setError("Password doesn't match");
            }

            if(!getFullNameTxt.isEmpty() && !getEmailTxt.isEmpty() && getEmailTxt.matches(emailExpression) && getMobileTxt.length() == 10 &&
                    !getPassword.isEmpty() && getPassword.length() >=6 && !getConPassword.isEmpty() && getConPassword.length() >= 6 && getPassword.equals(getConPassword)) {

                // opening OTP verification Activity along with mobile and email
                Intent intent = new Intent(CustomerSignUp.this, customerOTP_verification.class);
                intent.putExtra("mobile", getMobileTxt);
                intent.putExtra("email", getEmailTxt);
                startActivity(intent);
            }
        });
    }
}