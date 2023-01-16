package com.example.mazegame.Activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mazegame.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Activity_setting extends AppCompatActivity {


    private MaterialButton setting_BTN_about;
    private MaterialButton setting_BTN_terms;
    private MaterialButton setting_BTN_policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initButtons();
    }
    public void findView() {
        setting_BTN_about = findViewById(R.id.setting_BTN_about);
        setting_BTN_terms = findViewById(R.id.setting_BTN_terms);
        setting_BTN_policy = findViewById(R.id.setting_BTN_policy);

    }

    public void initButtons(){

        setting_BTN_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHtmlTextDialog(Activity_setting.this, "about.html");
            }
        });

        setting_BTN_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHtmlTextDialog(Activity_setting.this, "terms_of_use.html");
            }
        });

        setting_BTN_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHtmlTextDialog(Activity_setting.this, "privacy_policy.html");
            }
        });
    }

    public static void openHtmlTextDialog(Activity activity, String fileNameInAssets) {
        String str = "";
        InputStream is = null;

        try {
            is = activity.getAssets().open(fileNameInAssets);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            str = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(activity);
        materialAlertDialogBuilder.setPositiveButton("Close", null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            materialAlertDialogBuilder.setMessage(Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY));
        } else {
            materialAlertDialogBuilder.setMessage(Html.fromHtml(str));
        }

        AlertDialog al = materialAlertDialogBuilder.show();
        TextView alertTextView = al.findViewById(android.R.id.message);
        alertTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
