//package com.example.course_mobile_3.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.os.IBinder;
//import android.util.Log;
//import android.widget.EditText;
//
//import androidx.annotation.Nullable;
//
//import com.example.course_mobile_3.api.client.RetrofitClient;
//import com.example.course_mobile_3.api.service.IFileService;
//import com.example.course_mobile_3.api.service.IUserService;
//import com.google.gson.Gson;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class FileSyncService extends Service {
//
//    private static final String TAG = "FileSyncService";
//    private static final long SYNC_INTERVAL = 2000; // Интервал синхронизации в миллисекундах
//    private static String FILE_PATH; // Путь к файлу для синхронизации
//    private static String text;
//
//    private Timer syncTimer;
//    StringBuffer strBuffer;
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        syncTimer = new Timer();
//        syncTimer.scheduleAtFixedRate(new SyncTask(), 0, SYNC_INTERVAL);
//
//
//
//        try {
//            FileInputStream fileInputStream = openFileInput("db.txt");
//            InputStreamReader reader = new InputStreamReader(fileInputStream);
//            BufferedReader buffered = new BufferedReader(reader);
//            StringBuffer strBuffer = new StringBuffer();
//            String lines;
//            while ((lines = buffered.readLine()) != null){
//                strBuffer.append(lines + "\n");
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        String specificLine = strBuffer.substring(5, 6);
//
//
//        Map<String,Long> data = new HashMap<>();
//        data.put("session_id", Long.valueOf(specificLine));
//
//        Gson gson = new Gson();
//        String json = gson.toJson(data);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);
//
//        IFileService iFileService = RetrofitClient.getClient().create(IFileService.class);
//        Call<ResponseBody> call = iFileService.fileGet(requestBody);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                for (Resource)
//
//
//
//                try {
//                    FileOutputStream fileOutputStream = openFileOutput("", MODE_PRIVATE);
//                    fileOutputStream.write(text.getBytes());
//                    fileOutputStream.close();
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        syncTimer.cancel();
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    private class SyncTask extends TimerTask {
//        @Override
//        public void run() {
//// Получение файла для синхронизации
//            File fileToSync = new File(FILE_PATH);
//
//// Проверка существования файла
//            if (fileToSync.exists()) {
//// Создание объекта Retrofit для общения с сервером
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//// Создание объекта FileService для отправки файла на сервер
//                FileService fileService = retrofit.create(FileService.class);
//
//// Создание объекта RequestBody для файла
//                RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileToSync);
//
//// Создание объекта MultipartBody.Part для передачи файла
//                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileToSync.getName(), fileBody);
//
//// Отправка файла на сервер
//                Call<String> call = fileService.uploadFile(filePart);
//                call.enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        if (response.isSuccessful()) {
//                            Log.d(TAG, "Файл успешно синхронизирован с сервером");
//                        } else {
//                            Log.e(TAG, "Ошибка при синхронизации файла с сервером: " + response.message());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//                        Log.e(TAG, "Ошибка при отправке файла на сервер: " + t.getMessage());
//                    }
//                });
//            } else {
//                Log.e(TAG, "Файл не найден для синхронизации");
//            }
//        }
//    }
//}
