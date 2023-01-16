package com.example.mazegame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mazegame.DataManager;
import com.example.mazegame.R;
import com.example.mazegame.User;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class Activity_SignUp extends AppCompatActivity {

    private final DataManager dataManager = DataManager.getInstance();
    private User userToStore;
    private ExtendedFloatingActionButton main_FAB_sign_up;
    private TextInputEditText main_EDT_name ,main_EDT_email , main_EDT_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViews();

        main_FAB_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userToStore = new User();
                UUID uuid = UUID.randomUUID();
                userToStore.setUserId(uuid.toString());
                String userName = main_EDT_name.getText().toString();
                String email = main_EDT_email.getText().toString();
                String password = main_EDT_password.getText().toString();

                if (emailValidator(email)) {
                    userToStore.setEmail(email);
                    userToStore.setName(userName);
                    userToStore.setPassword(password);
                    userToStore.setPremium(true);
                    dataManager.setCurrentUser(userToStore);
                    dataManager.storeUserInDB(userToStore);
                    Toast.makeText(getApplicationContext(), "saved successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Activity_SignUp.this, MainActivity.class));
                    finish();
                }
            }
        });


    }

    public boolean emailValidator(String email) {
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            Toast.makeText(this, "Enter valid Email address please", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void findViews(){

        main_FAB_sign_up = findViewById(R.id.main_FAB_sign_up);
        main_EDT_name  = findViewById(R.id.main_EDT_name);
        main_EDT_email = findViewById(R.id.main_EDT_email);
        main_EDT_password = findViewById(R.id.main_EDT_password);
    }
}
