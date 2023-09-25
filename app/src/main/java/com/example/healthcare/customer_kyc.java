package com.example.healthcare;




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;


import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;


public class customer_kyc extends AppCompatActivity {

    final int PICK_FIRST_IMAGE = 100;
    final int PICK_SECOND_IMAGE = 101;
    final int PICK_THIRD_IMAGE = 102;

    TextView DOB;
    EditText fullName, fatherName, Address, Phone;
    private ImageView pPic, frontPic, backPic, cCalender;
    Button Submit;
    private Uri imagePath;
    private Uri imagePath1, imagePath2 ;
    int year,month,day;
    FirebaseStorage storage;
    FirebaseDatabase database;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_kyc);

        fullName = findViewById(R.id.fullName);
        DOB = findViewById(R.id.DOB);
        fatherName = findViewById(R.id.fatherName);
        Address = findViewById(R.id.Address);
        Phone = findViewById(R.id.Phone);
        pPic = findViewById(R.id.pPic);
        frontPic = findViewById(R.id.frontPic);
        backPic = findViewById(R.id.backPic);
        Submit = findViewById(R.id.Submit);
        cCalender = findViewById(R.id.cCalender);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        final Calendar calender = Calendar.getInstance();



        cCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                year = calender.get(Calendar.YEAR);
                month = calender.get(Calendar.MONTH);
                day = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(customer_kyc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DOB.setText(dayOfMonth + "/" + (month+1) + "/" +year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        pPic.setOnClickListener(v -> {
            Intent photoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(photoIntent,PICK_FIRST_IMAGE);


        });

        frontPic.setOnClickListener(v -> {
            Intent photoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(photoIntent, PICK_SECOND_IMAGE);

        });

        backPic.setOnClickListener(v -> {

            Intent photoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(photoIntent,PICK_THIRD_IMAGE);

        });



        Submit.setOnClickListener(v -> {
            if (fullName.getText().toString().isEmpty()) {
                fullName.setError("Enter FullName");
            }

            if (DOB.getText().toString().isEmpty()) {
                DOB.setError("Enter DOB");
            }

            if(fatherName.getText().toString().isEmpty()){
                fatherName.setError("Enter Father Name");
            }
            if(Address.getText().toString().isEmpty()){
                Address.setError("Enter Address");
            }
            if(Phone.getText().toString().isEmpty() && !(Phone.getText().toString().length() == 10)) {
                Phone.setError("Enter Correct Phone Number");
            }

            if(!fullName.getText().toString().isEmpty() && !DOB.getText().toString().isEmpty() && !Address.getText().toString().isEmpty()
                    && !fatherName.getText().toString().isEmpty() && !Phone.getText().toString().isEmpty()) {

                database.getReference("CustomerUser/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new CustomerUser(fullName.getText().toString(), DOB.getText().toString(),
                        Address.getText().toString(), fatherName.getText().toString(), Phone.getText().toString(), "", "", ""));

                Toast.makeText(this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(customer_kyc.this, customer_MainActivity.class));
            }else{
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }

            uploadImage(pPic);
            uploadImage1(frontPic);
            uploadImage2(backPic);


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK && data != null){
            imagePath = data.getData();
            imagePath1 = data.getData();
            imagePath2 = data.getData();

            Bitmap bitmap = null;
            Bitmap bitmap1 = null;
            Bitmap bitmap2 = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath1);
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath2);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(requestCode == PICK_FIRST_IMAGE) {
                pPic.setImageBitmap(bitmap);
            }else if (requestCode == PICK_SECOND_IMAGE) {
                frontPic.setImageBitmap(bitmap1);
            }else  {
                backPic.setImageBitmap(bitmap2);
            }

        }
    }
    private void uploadImage(ImageView pPic ){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("uploading...");
        progressDialog.show();

        FirebaseStorage.getInstance().getReference("CustomerProfilePic/*"+ UUID.randomUUID().toString()).putFile(imagePath1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(customer_kyc.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                updateProfilePicture(task.getResult().toString());
                            }
                        }
                    });
                }else{
                    Toast.makeText(customer_kyc.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void uploadImage1(ImageView frontPic ){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("uploading...");
        progressDialog.show();

        FirebaseStorage.getInstance().getReference("CustomerCitizenFrontPic/*"+ UUID.randomUUID().toString()).putFile(imagePath1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(customer_kyc.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                updateFrontPic(task.getResult().toString());
                            }
                        }
                    });
                }else{
                    Toast.makeText(customer_kyc.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private void uploadImage2(ImageView backPic ){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("uploading...");
        progressDialog.show();

        FirebaseStorage.getInstance().getReference("CustomerCitizenBackPic/*"+ UUID.randomUUID().toString()).putFile(imagePath2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(customer_kyc.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                updateBackPic(task.getResult().toString());
                            }
                        }
                    });
                }else{
                    Toast.makeText(customer_kyc.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void updateProfilePicture(String url){
        FirebaseDatabase.getInstance().getReference("CustomerUser/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/profilePicture").setValue(url);

    }
    private void updateFrontPic(String url1){
        FirebaseDatabase.getInstance().getReference("CustomerUser/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/citizenFrontPic").setValue(url1);

    }
    private void updateBackPic(String url2){
        FirebaseDatabase.getInstance().getReference("CustomerUser/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/citizenBackPic").setValue(url2);

    }


}



