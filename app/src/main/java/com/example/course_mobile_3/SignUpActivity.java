package com.example.course_mobile_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.course_mobile_3.api.client.RetrofitClient;
import com.example.course_mobile_3.api.service.IUserService;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    ImageButton next;
    EditText fullname, nickname;
    String login, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        login = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        registered();

    }

    public void registered(){
        next = findViewById(R.id.next);
        fullname = findViewById(R.id.fullname);
        nickname = findViewById(R.id.nickname);


        next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if((!nickname.getText().toString().equals(""))) {

                            Map<String, String> data = new HashMap<>();
                            data.put("email",login);
                            data.put("password", password);
                            data.put("fullName", fullname.getText().toString());
                            data.put("nickname", nickname.getText().toString());

                            Gson gson = new Gson();
                            String json = gson.toJson(data);
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

                            IUserService iUserService = RetrofitClient.getClient().create(IUserService.class);
                            Call<Map<String,String>> call = iUserService.reg(requestBody);

                            call.enqueue(new Callback<Map<String,String>>() {
                                @Override
                                public void onResponse(Call<Map<String,String>> call, Response<Map<String,String>> response) {
                                    if (response.body().get("message").equals("Done!")) {
                                        Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    } else if (response.body().get("message").equals("Email already!")) {
                                        Toast.makeText(SignUpActivity.this, "Email уже занят", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Map<String,String>> call, Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            });
                        } else {
                            Toast.makeText(SignUpActivity.this,"Введите никнейм", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}