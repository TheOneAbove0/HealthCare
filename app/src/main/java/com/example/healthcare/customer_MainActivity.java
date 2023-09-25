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


public class customer_MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<NurseUser> nUsers;
    private ProgressBar progressBar;
    private nurseUserAdapter nUsersAdapter;


    nurseUserAdapter.OnUserClickListener onUserClickListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    String myImgUrl;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler);
        nUsers = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeLayout);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNUsers();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        
        onUserClickListener = new nurseUserAdapter.OnUserClickListener() {
            @Override
            public void onUserClicked(int position) {
                startActivity(new Intent(customer_MainActivity.this,view_nurse.class));


                Toast.makeText(customer_MainActivity.this, "Tapped on User "+nUsers.get(position).getFullName(), Toast.LENGTH_SHORT).show();
            }
        };
        getNUsers();

    }

    private void getNUsers() {
        nUsers.clear();
        FirebaseDatabase.getInstance().getReference("NurseUser").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    nUsers.add(dataSnapshot.getValue(NurseUser.class));

                }
                nUsersAdapter = new nurseUserAdapter(nUsers,customer_MainActivity.this,onUserClickListener);
                recyclerView.setLayoutManager(new LinearLayoutManager(customer_MainActivity.this));
                recyclerView.setAdapter(nUsersAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                for(NurseUser NurseUser : nUsers){
                    if(NurseUser.getPhone().equals(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())){
                        myImgUrl =NurseUser.getProfilePicture();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menu){
            startActivity(new Intent(customer_MainActivity.this,customer_profile.class));
        }
        return super.onOptionsItemSelected(item);
    }

}