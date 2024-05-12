package com.example.course_mobile_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_mobile_3.adapter.FriendsAdapter;
import com.example.course_mobile_3.api.client.RetrofitClient;
import com.example.course_mobile_3.api.service.IUserService;
import com.example.course_mobile_3.modelUI.Friend;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendsActivity extends AppCompatActivity {

    ImageButton back;
    RecyclerView friendRecycler;
    FriendsAdapter friendAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends);

        Long idUser = getIntent().getLongExtra("idUser", 0L);

        List<Friend> friendList = new ArrayList<>();

        IUserService iUserService = RetrofitClient.getClient().create(IUserService.class);
        Call<List<Map<String,Object>>> call = iUserService.getAllFriends(idUser);

        call.enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                System.out.println(response);
                if (response.isSuccessful()) {
                    List<Map<String, Object>> friends = response.body();
                    System.out.println(friends);
                    for (Map<String, Object> friend : friends) {

                        Double friendIdDouble = (Double) friend.get("id");
                        String email = (String) friend.get("email");
                        String fullName = (String) friend.get("fullName");
                        String nickname = (String) friend.get("nickname");

                        Long friendId = Math.round(friendIdDouble);
                        friendList.add(new Friend(friendId,fullName, nickname));
                    }

                    // После того, как список заполнен, устанавливаем адаптер RecyclerView
                    setFriendsRecycler(friendList);
                } else {
                    Toast.makeText(FriendsActivity.this, "Нет друзей", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        System.out.println(friendList);
        onListenerOnButton();
    }

    public void setFriendsRecycler(List<Friend> friendList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        friendRecycler = findViewById(R.id.friendRecycle);
        friendRecycler.setLayoutManager(layoutManager);

        friendAdapter = new FriendsAdapter(this,friendList);
        friendRecycler.setAdapter(friendAdapter);
    }

    public void onListenerOnButton() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}