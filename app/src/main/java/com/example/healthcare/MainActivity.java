package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<patient_user> cUsers;
    private ProgressBar progressBar;
    private customerUsersAdapter cUsersAdapter;


    customerUsersAdapter.OnUserClickListener onUserClickListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    String myImgUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        cUsers = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCUsers();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        onUserClickListener = new customerUsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClicked(int position) {

                startActivity(new Intent(MainActivity.this,view_customer.class));

                Toast.makeText(MainActivity.this, "Tapped on User"+cUsers.get(position).getPatientName(), Toast.LENGTH_SHORT).show();
            }
        };
        getCUsers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menu){
            startActivity(new Intent(MainActivity.this,NurseProfile.class));
        }
        return super.onOptionsItemSelected(item);
    }


    private void getCUsers(){
        cUsers.clear();
        FirebaseDatabase.getInstance().getReference("PatientReport").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    cUsers.add(dataSnapshot.getValue(patient_user.class));

                }
                cUsersAdapter = new customerUsersAdapter(cUsers,MainActivity.this,onUserClickListener);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(cUsersAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

//                for(patient_user patient_user : cUsers){
//                    if(patient_user.getPatientPhone().equals(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())){
//                        myImgUrl = patient_user.getpProfilePicture();
//                    }
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}