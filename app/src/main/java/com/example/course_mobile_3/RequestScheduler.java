package com.example.course_mobile_3;

import android.widget.EditText;

import com.example.course_mobile_3.api.client.RetrofitClient;
import com.example.course_mobile_3.api.service.IFileService;
import com.example.course_mobile_3.api.service.IUserService;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestScheduler {
    private final ScheduledExecutorService scheduler;
    private final Long idSession;
    private final EditText editor;
    private boolean downloadCompleted = false;
    String file_text;

    public RequestScheduler(Long idSession, EditText editor) {
        this.idSession = idSession;
        this.editor = editor;
        this.scheduler = Executors.newScheduledThreadPool(1); // Создаем пул потоков для выполнения задач
    }

    public void startSendingRequests() {
        scheduler.scheduleWithFixedDelay(this::download, 0,1000,TimeUnit.MILLISECONDS);
        scheduler.scheduleWithFixedDelay(this::sendRequest, 500, 1000, TimeUnit.MILLISECONDS);
    }

    private void sendRequest() {


        Map<String, String> data = new HashMap<>();
        data.put("session_id", String.valueOf(idSession));
        data.put("text", editor.getText().toString());

        Gson gson = new Gson();
        String json = gson.toJson(data);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);






        IFileService ifileService = RetrofitClient.getClient().create(IFileService.class);
        Call<Map<String,Object>> updateFile = ifileService.updateFile(requestBody);
        updateFile.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {

            }
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });


    }
    public void download() {

        IFileService iFileService = RetrofitClient.getClient().create(IFileService.class);
        Call<Map<String,String>> downloadFile = iFileService.downloadFile(idSession);
        downloadFile.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()){
                    Map<String, String> data = response.body();
                    file_text = data.get("string");
//                    int cursor = 0;
//                    if (!file_text.isEmpty()){
//                        cursor = editor.getSelectionStart();
//                    }
                    editor.setText(file_text);
//                    editor.setSelection(cursor);
                    downloadCompleted = true;
                } else {
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


    public void stopSendingRequests() {
        // Остановка планировщика при необходимости
        scheduler.shutdown();
    }
}