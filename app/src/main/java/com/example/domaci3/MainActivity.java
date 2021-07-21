package com.example.domaci3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Switch s;
    SharedPreferences themeSharedPreferences;
    SharedPreferences loginSharedPreferences;
    DBHelper dbHelper;
    NotificationManagerCompat compat;
    int brojac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txtUser);
        password = findViewById(R.id.txtPass);

        themeSharedPreferences = this.getSharedPreferences("themeTable", MODE_PRIVATE);
        s = findViewById(R.id.switchMode);
        brojac = 0;

        s.setChecked(themeSharedPreferences.getBoolean("checked", false));
        AppCompatDelegate.setDefaultNightMode(themeSharedPreferences.getInt("mode", 1));
        s.setText(themeSharedPreferences.getString("title", "Light mode"));
        compat = NotificationManagerCompat.from(this);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor themeEditor = themeSharedPreferences.edit();
                if(isChecked){
                    themeEditor.putBoolean("checked", true);
                    themeEditor.putInt("mode", AppCompatDelegate.MODE_NIGHT_YES);
                    themeEditor.putString("title", "Dark mode");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    themeEditor.putBoolean("checked", false);
                    themeEditor.putInt("mode", AppCompatDelegate.MODE_NIGHT_NO);
                    themeEditor.putString("title", "Light mode");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                themeEditor.apply();
            }
        });

        loginSharedPreferences = this.getSharedPreferences("loginTable", MODE_PRIVATE);

        if(!loginSharedPreferences.getString("username","defaultValue").equals("defaultValue")){
            Intent i = new Intent(this, WorkingPage.class);
            startActivity(i);
        }
       dbHelper = new DBHelper(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void login(View view) {
        if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

            User u = dbHelper.select(username.getText().toString());
            if(u != null) {
                if(u.password.equals(password.getText().toString())){
                    //Toast.makeText(this,"Uspesno logovanje",Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor loginEditor = loginSharedPreferences.edit();
                    loginEditor.putString("name", u.name);
                    loginEditor.putString("username", u.username);
                    loginEditor.putString("password",u.password);

                    loginEditor.apply();

                    showGoodNotification(("Login uspesan, dobrodosao " + u.name), "Login");

                    Intent i = new Intent(this, WorkingPage.class);

                    startActivity(i);
                }else{

                    showBadNotification("Pogresan password", "Login problem");
                    //Toast.makeText(this, "Pogresan password", Toast.LENGTH_LONG).show();
                }
            }else {
                showBadNotification("Ne postoji username", "Login problem");
                //Toast.makeText(this, "Ne postoji username", Toast.LENGTH_LONG).show();
            }
        }else{
            showBadNotification("Niste uneli sve podatke", "Login problem");
           // Toast.makeText(this,"Niste uneli sve podatke", Toast.LENGTH_LONG).show();
        }
    }

    public void openRegisterPage(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
    public void showGoodNotification(String message, String title){
        Notification notification1 = new NotificationCompat.Builder(this, AppConfig.CHANNEL1)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();

        compat.notify(++brojac, notification1);
    }
    public void showBadNotification(String message, String title){

        Notification notification2 = new NotificationCompat.Builder(this, AppConfig.CHANNEL2)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();
        compat.notify(++brojac, notification2);
    }
}