package com.example.domaci3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText name, username, password;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.txtNameReg);
        username = findViewById(R.id.txtUserReg);
        password = findViewById(R.id.txtPassReg);

        dbHelper = new DBHelper(this);
    }

    public void createAcc(View view) {

        if(!name.getText().toString().isEmpty() && !username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

            if(dbHelper.select(username.getText().toString()) != null){
                Toast.makeText(this, "Taj username je zauzet",Toast.LENGTH_LONG).show();
                return;
            }

            User u = new User(name.getText().toString(), username.getText().toString(), password.getText().toString());
            dbHelper.addRow(u);

            Toast.makeText(this,"Registrovanje uspesno", Toast.LENGTH_LONG).show();
        }

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}