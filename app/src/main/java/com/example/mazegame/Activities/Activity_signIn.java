package com.example.mazegame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mazegame.DataManager;
import com.example.mazegame.R;
import com.example.mazegame.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class Activity_signIn extends AppCompatActivity {

    private MaterialButton main_BTN_sign_in;
    private ExtendedFloatingActionButton main_FAB_sign_up;
    private TextInputEditText main_EDT_password;
    private TextInputEditText main_EDT_email;
    private EditText main_txt_error;
    private final DataManager dataManager = DataManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findsView();

        main_FAB_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        main_BTN_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int exist;
                String email = main_EDT_email.getText().toString();
                String password = main_EDT_password.getText().toString();
                exist = dataManager.findUserInDB(email,password);
                if (exist == 0) {
                    main_txt_error.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(Activity_signIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (exist == 1) {
                    Toast.makeText(Activity_signIn.this, "The email is not registered in the system", Toast.LENGTH_SHORT).show();
                    main_EDT_email.getText().clear();
                    main_EDT_password.getText().clear();
                    main_txt_error.setVisibility(View.VISIBLE);
                }
                if (exist == 2) {
                    Toast.makeText(Activity_signIn.this, "Wrong password please try again", Toast.LENGTH_SHORT).show();
                    main_EDT_email.getText().clear();
                    main_EDT_password.getText().clear();
                    main_txt_error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void findsView(){
        main_FAB_sign_up = findViewById(R.id.main_FAB_sign_up);
        main_BTN_sign_in = findViewById(R.id.main_BTN_sign_in);
        main_EDT_password =findViewById(R.id.main_EDT_password);
        main_EDT_email = findViewById(R.id.main_EDT_email);
    }
}