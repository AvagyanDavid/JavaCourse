package com.example.course_mobile_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInUpActivity extends AppCompatActivity {

    ImageButton login_btn, create;
    EditText email,password,repeat_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);

        login();
    }

    public void login(){
        login_btn = findViewById(R.id.login);
        create = findViewById(R.id.create);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repeat_password = findViewById(R.id.repeatPassword);
        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".SignInActivity");
                        startActivity(intent);
                    }
                }
        );
        create.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((!email.getText().toString().equals("")) && (password.getText().toString().equals(repeat_password.getText().toString()))) {
                            Intent intent = new Intent(".SignUpActivity");

                            intent.putExtra("email", email.getText().toString());
                            intent.putExtra("password",password.getText().toString());
                            startActivity(intent);

                        } else {
                            Toast.makeText(SignInUpActivity.this, "Ошибка в логине или пароле", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}