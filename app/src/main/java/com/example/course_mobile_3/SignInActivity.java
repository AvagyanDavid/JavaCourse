package com.example.course_mobile_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.course_mobile_3.api.body.User;
import com.example.course_mobile_3.api.client.RetrofitClient;
import com.example.course_mobile_3.api.service.IUserService;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    ImageButton signUp, signIn;
    EditText email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        login();
    }

    public void login(){
        signUp = findViewById(R.id.signUp);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, String> data = new HashMap<>();
                        data.put("email",email.getText().toString());
                        data.put("password", password.getText().toString());

                        Gson gson = new Gson();
                        String json = gson.toJson(data);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

                        IUserService iUserService = RetrofitClient.getClient().create(IUserService.class);
                        Call<Map<String,Object>> call = iUserService.login(requestBody);

                        call.enqueue(new Callback<Map<String,Object>>() {
                            @Override
                            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                                if (response.isSuccessful()) {
                                    Map<String, Object> data = response.body();
                                    Object messageObject = data.get("message");
                                    if (messageObject != null) {
                                        String message = messageObject.toString();
                                        if (data.get("message").equals("Неверный email"))
                                            Toast.makeText(SignInActivity.this, "Неверный email", Toast.LENGTH_LONG).show();
                                        else if (data.get("message").equals("Неверный пароль")) {
                                            Toast.makeText(SignInActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        String nickname_txt = data.get("nickname").toString();
                                        String fullName_txt = data.get("fullName").toString();
                                        String session_txt = data.get("session_id").toString();
                                        String id_txt = data.get("id").toString();
                                        String email_txt = data.get("email").toString();

                                        try {
                                            FileOutputStream fileOutputStream = openFileOutput("db.txt", MODE_PRIVATE);
                                            fileOutputStream.write("nickname".getBytes());
                                            fileOutputStream.write("\n".getBytes());
                                            fileOutputStream.write(nickname_txt.getBytes());
                                            fileOutputStream.write("\n".getBytes());
                                            fileOutputStream.write("fullName".getBytes());
                                            fileOutputStream.write("\n".getBytes());
                                            fileOutputStream.write(fullName_txt.getBytes());
                                            fileOutputStream.write("\n".getBytes());
                                            fileOutputStream.write("session_id".getBytes());
                                            fileOutputStream.write("\n".getBytes());
                                            fileOutputStream.write(session_txt.getBytes());
                                            fileOutputStream.write("\n".getBytes());
                                            fileOutputStream.write("id".getBytes());
                                            fileOutputStream.write("\n".getBytes());
                                            fileOutputStream.write(id_txt.getBytes());
                                            fileOutputStream.write("\n".getBytes());
                                            fileOutputStream.write("email".getBytes());
                                            fileOutputStream.write("\n".getBytes());
                                            fileOutputStream.write(email_txt.getBytes());
                                            fileOutputStream.close();
                                        } catch (FileNotFoundException e) {
                                            throw new RuntimeException(e);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }


                                        /// сохранение данных в файл о пользователе
                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    }
                                } else System.out.println(response.code());
                            }

                            @Override
                            public void onFailure(Call<Map<String,Object>> call, Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
                    }
                }
        );

        signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".SignInUpActivity");
                        startActivity(intent);
                    }
                }
        );
    }

}