package com.example.jetpack2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddUserActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.jetpack2.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.jetpack2.EXTRA_NAME";
    public static final String EXTRA_EMAIL = "com.example.jetpack2.EXTRA_EMAIL";
    public static final String EXTRA_PHONE = "com.example.jetpack2.EXTRA_PHONE";

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        editTextName = findViewById(R.id.edit_text_title);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPhone = findViewById(R.id.edit_text_phone);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit User");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
            editTextPhone.setText(intent.getStringExtra(EXTRA_PHONE));
        }
        else {
            setTitle("Add User");
        }
    }


    private void saveUser() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String phone = editTextPhone.getText().toString();

        if(name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()){
            Toast.makeText(this,"Please insert details first",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent  =new Intent();
        intent.putExtra(EXTRA_NAME,name);
        intent.putExtra(EXTRA_EMAIL,email);
        intent.putExtra(EXTRA_PHONE,phone);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            intent.putExtra(EXTRA_ID,id);

        }

        setResult(RESULT_OK,intent);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_user:
                saveUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
