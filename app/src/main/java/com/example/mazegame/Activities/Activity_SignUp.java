package com.example.mazegame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
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
    private TextInputEditText main_EDT_name , main_EDT_password;
    private CheckBox premium;
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
                String password = main_EDT_password.getText().toString();
                boolean premium_account = premium.isChecked();
                userToStore.setName(userName);
                userToStore.setPassword(password);
                userToStore.setPremium(premium_account);
                dataManager.setCurrentUser(userToStore);
                dataManager.storeUserInDB(userToStore);
                Toast.makeText(getApplicationContext(), "saved successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Activity_SignUp.this, MainActivity.class));
                finish();
            }
        });


    }

    public void findViews(){

        main_FAB_sign_up = findViewById(R.id.main_FAB_sign_up);
        main_EDT_name  = findViewById(R.id.main_EDT_name);
        main_EDT_password = findViewById(R.id.main_EDT_password);
        premium = findViewById(R.id.premium_account);
    }
}
