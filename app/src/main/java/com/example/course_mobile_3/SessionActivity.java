package com.example.course_mobile_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SessionActivity extends AppCompatActivity {

    ImageButton back;
    Long otherIdSession;
    EditText editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_session);
        editor = findViewById(R.id.EditorText);

        otherIdSession = getIntent().getLongExtra("otherIdSession",0L);

        RequestScheduler requestScheduler = new RequestScheduler(otherIdSession,editor);
        requestScheduler.startSendingRequests();

        onListenerOnButton();
    }


    public void onListenerOnButton() {
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SessionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}