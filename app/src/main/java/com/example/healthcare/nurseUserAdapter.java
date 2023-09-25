package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class nurseUserAdapter extends RecyclerView.Adapter<nurseUserAdapter.userHolder> {



    private ArrayList<NurseUser> nUsers;

    private Context context;
    private OnUserClickListener onUserClickListener;


    public nurseUserAdapter(ArrayList<NurseUser> nUsers, Context context, OnUserClickListener onUserClickListener) {
        this.nUsers = nUsers;
        this.context = context;
        this.onUserClickListener = onUserClickListener;
    }

    interface OnUserClickListener{
        void onUserClicked(int position);
    }

    @NonNull
    @Override
    public userHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nurse_user_holder,parent,false);
        return new userHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull userHolder holder, int position) {
        holder.txtUserName.setText(nUsers.get(position).getFullName());
        Glide.with(context).load(nUsers.get(position).getProfilePicture()).error(R.drawable.account_img).
                placeholder(R.drawable.account_img).into(holder.imageView);


    }



    @Override
    public int getItemCount() {
        return nUsers.size();
    }

    class userHolder extends RecyclerView.ViewHolder{
        TextView txtUserName;
        ImageView imageView;

        public userHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUserClickListener.onUserClicked(getAdapterPosition());
                }
            });
            txtUserName = itemView.findViewById(R.id.txtUsername);
            imageView = itemView.findViewById(R.id.img_pro);
        }
    }
}
