package com.example.domaci3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WorkingPage extends AppCompatActivity {

    SharedPreferences userSharedPreference;
    TextView welcome;
    EditText pass1;
    EditText pass2;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_page);
        dbHelper = new DBHelper(this);
        userSharedPreference = this.getSharedPreferences("loginTable", MODE_PRIVATE);
        welcome = findViewById(R.id.txtWelcome);
        welcome.setText(welcome.getText().toString() + userSharedPreference.getString("name", "Marko Markovic"));

        pass1 = findViewById(R.id.txtChangePass1);
        pass2 = findViewById(R.id.txtChangePass2);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void changePass(View view){
        if(!pass1.getText().toString().isEmpty() && !pass2.getText().toString().isEmpty()){
            if(pass1.getText().toString().equals(pass2.getText().toString())){

                dbHelper.changePass(userSharedPreference.getString("username", "x"), pass1.getText().toString());
                deleteSharedPreferences();

            }else{

                Toast.makeText(this,"Passwordi se ne podudaraju", Toast.LENGTH_LONG).show();
            }
        }else{

            Toast.makeText(this,"Niste uneli sve", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteAcc(View view) {
        dbHelper.deleteAcc(userSharedPreference.getString("username","x"));
        deleteSharedPreferences();
    }

    private void deleteSharedPreferences(){
        SharedPreferences.Editor loginEditor = userSharedPreference.edit();
        loginEditor.remove("name");
        loginEditor.remove("username");
        loginEditor.remove("password");

        loginEditor.apply();

        Toast.makeText(this,"Uspesno", Toast.LENGTH_LONG).show();


        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void logOut(View view) {
        deleteSharedPreferences();
    }


}