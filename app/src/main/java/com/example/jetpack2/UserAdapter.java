package com.example.jetpack2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jetpack2.databinding.UserLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private List<User> users = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserLayoutBinding userLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.user_layout,parent,false);
        return new UserHolder(userLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = users.get(position);
        holder.userLayoutBinding.setUser(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public void setData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public User getUserAt(int position) {
        return users.get(position);
    }



    class UserHolder extends RecyclerView.ViewHolder {
        UserLayoutBinding userLayoutBinding;

        public UserHolder(@NonNull UserLayoutBinding itemView) {
            super(itemView.getRoot());

            userLayoutBinding = itemView;
            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(users.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
