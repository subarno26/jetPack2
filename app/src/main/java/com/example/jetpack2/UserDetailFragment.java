package com.example.jetpack2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jetpack2.databinding.FragmentUserDetailBinding;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class UserDetailFragment extends Fragment {

    private UserViewModel userViewModel;
    public static final int ADD_USER_REQUEST = 1;
    public static final int EDIT_USER_REQUEST = 2;
    private FragmentUserDetailBinding userDetailBinding;
    private UserAdapter adapter;


    public UserDetailFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false);
        View output = userDetailBinding.getRoot();
        userDetailBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userDetailBinding.recyclerView.setHasFixedSize(true);
        adapter = new UserAdapter();
        userDetailBinding.recyclerView.setAdapter(adapter);


        userDetailBinding.buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddUserActivity.class);
                startActivityForResult(intent, ADD_USER_REQUEST);
            }
        });

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setData(users);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               userViewModel.delete(adapter.getUserAt(viewHolder.getAdapterPosition()));

                Toast.makeText(getContext(), "User deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(userDetailBinding.recyclerView);

        adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(getContext(), AddUserActivity.class);
                intent.putExtra(AddUserActivity.EXTRA_ID, user.getId());
                intent.putExtra(AddUserActivity.EXTRA_NAME, user.getName());
                intent.putExtra(AddUserActivity.EXTRA_EMAIL, user.getEmail());
                intent.putExtra(AddUserActivity.EXTRA_PHONE, user.getNumber());
                startActivityForResult(intent, EDIT_USER_REQUEST);


            }
        });


        return output;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_USER_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddUserActivity.EXTRA_NAME);
            String email = data.getStringExtra(AddUserActivity.EXTRA_EMAIL);
            String phone = data.getStringExtra(AddUserActivity.EXTRA_PHONE);

            User user = new User(name, email, phone);
            userViewModel.insert(user);
            Toast.makeText(getContext(), "user saved", Toast.LENGTH_SHORT).show();


        } else if (requestCode == EDIT_USER_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddUserActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getContext(), "User can't be updated", Toast.LENGTH_SHORT).show();
            }
            String name = data.getStringExtra(AddUserActivity.EXTRA_NAME);
            String email = data.getStringExtra(AddUserActivity.EXTRA_EMAIL);
            String phone = data.getStringExtra(AddUserActivity.EXTRA_PHONE);

            User user = new User(name, email, phone);
            user.setId(id);
            userViewModel.update(user);
            Toast.makeText(getContext(), "User Updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getContext(), "User not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
