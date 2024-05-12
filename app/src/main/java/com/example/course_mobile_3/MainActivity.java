package com.example.course_mobile_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.course_mobile_3.api.client.RetrofitClient;
import com.example.course_mobile_3.api.service.IFileService;
import com.example.course_mobile_3.api.service.IUserService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.course_mobile_3.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private EditText editor;
    StringBuffer strBuffer;
    Long idUser, idSession, otherIdSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editor = findViewById(R.id.EditorText);

        try {
            FileInputStream fileInputStream = openFileInput("db.txt");
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer strBuffer = new StringBuffer();
            ArrayList<String> arrayList = new ArrayList<>();

            String line;
            int lineNumber = 0;
            while ((line = buffer.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 6 && !line.trim().isEmpty()) {
                    arrayList.add(line);
                }
                if (lineNumber == 8 && !line.trim().isEmpty()) {
                    arrayList.add(line);
                }
            }

            String strIdUser = arrayList.get(1);
//            System.out.println(strIdUser);
            idUser = Long.valueOf(strIdUser);
            System.out.println(strBuffer);

            while ((line = buffer.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 6 && !line.trim().isEmpty()) {
                    strBuffer.append(line);
                    break;
                }
            }

            String strIdSession = arrayList.get(0);
            System.out.println(strIdSession);
            idSession = Long.parseLong(strIdSession);

            RequestScheduler requestScheduler = new RequestScheduler(idSession,editor);
            requestScheduler.startSendingRequests();
            buffer.close();
            reader.close();
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            Intent intent = new Intent(MainActivity.this,SignInUpActivity.class);
            startActivity(intent);
            finishAffinity();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addListenerOnButton();
    }

    public void addListenerOnButton(){
        ImageButton menu = findViewById(R.id.open_menu);
        drawer = findViewById(R.id.drawer_layout);

        menu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!drawer.isDrawerOpen(GravityCompat.START)) drawer.openDrawer(GravityCompat.START);
                        else drawer.closeDrawer(GravityCompat.END);
                    }
                }
        );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.nav_folder)
        {
            Map<String, String> data = new HashMap<>();
            data.put("user_id", String.valueOf(idUser));

            Gson gson = new Gson();
            String json = gson.toJson(data);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

            IUserService iUserService = RetrofitClient.getClient().create(IUserService.class);
            Call<Map<String,Object>> call = iUserService.getOtherSession(requestBody);

            call.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (response.isSuccessful()) {
                        Map<String, Object> data = response.body();
                        Object messageObject = data.get("message");
                        if (messageObject != null) {
                            String message = messageObject.toString();
                            System.out.println(message);
                        } else {
                            Long otherIdSession = (data.get("session_member_id") instanceof Number ? ((Number) data.get("session_member_id")).longValue() : -1);
                            String idSession_txt = otherIdSession.toString();

                            try {
                                FileOutputStream fileOutputStream = openFileOutput("db.txt", MODE_APPEND);
                                OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
                                BufferedWriter buffer = new BufferedWriter(writer);

                                String name = "otherIdSession";

                                buffer.write(name);
                                buffer.newLine(); // Добавляем символ новой строки, если это нужно
                                buffer.write(idSession_txt);

                                buffer.close();
                                fileOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(MainActivity.this,SessionActivity.class);
                            intent.putExtra("otherIdSession", otherIdSession);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

            Toast.makeText(this, "Кнопка папка нажата", Toast.LENGTH_SHORT).show();
            
        } else if (id == R.id.nav_friends) {
            Intent intent = new Intent(MainActivity.this,FriendsActivity.class);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
        } else if (id == R.id.nav_sign_out) {
            Intent intent = new Intent(MainActivity.this, SignInUpActivity.class);
            startActivity(intent);
            finishAffinity();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}