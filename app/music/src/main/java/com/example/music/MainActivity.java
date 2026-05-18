package com.example.music;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button button;
    ImageView imageView;
    Context context;
    SeekBar seekBar;
    SwitchCompat switchLooping;
    TextView textViewTime;
    boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyLanguageFromPrefs();
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        seekBar = findViewById(R.id.seekBar);
        textViewTime = findViewById(R.id.textViewTime);
        //c1
        switchLooping = findViewById(R.id.switchLooping);
        if (MyService.mediaPlayer != null) {
            MyService.mediaPlayer.setLooping(true);
        }
        switchLooping.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (MyService.mediaPlayer != null) {
                MyService.mediaPlayer.setLooping(isChecked);
            }
        });
        String imageUrl = "https://static-cse.canva.com/blob/1379502/1600w-1Nr6gsUndKw.jpg";
        MyThread myThread = new MyThread(imageUrl, context);
        myThread.start();
        button.setOnClickListener(view -> {
//            Tải ảnh ở Main Thread
//            try {
//                String imageUrl = "https://cdnphoto.dantri.com.vn/dKwEgIQxv3uy11cnK2u6MzeHakA=/thumb_w/1920/2024/02/08/truong-dinh-minh-1707392151116.jpeg";
//                URL url = new URL(imageUrl);
//                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//                connection.connect();
//                InputStream is = connection.getInputStream();
//                Bitmap bitmap = BitmapFactory.decodeStream(is);
//                imageView.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }

//            Tải ảnh ở Background Thread
//            String imageUrl = "https://cdnphoto.dantri.com.vn/dKwEgIQxv3uy11cnK2u6MzeHakA=/thumb_w/1920/2024/02/08/truong-dinh-minh-1707392151116.jpeg";
//            MyThread myThread = new MyThread(imageUrl, context);
//            myThread.start();

//            Sử dụng TheadPool để tải 6 ảnh
//            String[] imageUrls = {
//                    "https://cdnphoto.dantri.com.vn/dKwEgIQxv3uy11cnK2u6MzeHakA=/thumb_w/1920/2024/02/08/truong-dinh-minh-1707392151116.jpeg",
//                    "https://cdnphoto.dantri.com.vn/MYYEaximEv2miV5mHaPwIqMGCbs=/thumb_w/1920/2024/02/08/khanh-5-1707392071293.jpeg",
//                    "https://cdnphoto.dantri.com.vn/yMgz_lQ5ENSQ6Rt4IqMt9McMBD4=/thumb_w/1920/2024/02/08/nguyen-chi-nam-1707392151775.jpeg",
//                    "https://cdnphoto.dantri.com.vn/5DzKjBbhd0jlQYYiesx8l8wAIpE=/thumb_w/1920/2024/02/08/khanh-phan-1707392069747.jpeg",
//                    "https://cdnphoto.dantri.com.vn/5H40ie2jFK8vG7ZQr1coIN8cBTk=/thumb_w/1920/2024/02/08/chi-nam-1-1707392151754.jpeg",
//                    "https://cdnphoto.dantri.com.vn/8StTUh5H2ksYjb-On20KRSoV-24=/thumb_w/1920/2024/02/08/truong-minh-2-1707392151040.jpeg",
//                    "https://cdnphoto.dantri.com.vn/BUvPkO7gHuLPldq4ho3TO0BkSl4=/thumb_w/1920/2024/02/08/nguyen-chi-nam-1707408239678.jpeg",
//                    "https://cdnphoto.dantri.com.vn/y1e9r3JFky_sdgiADX4s1KxHAmc=/thumb_w/1920/2024/02/08/41783510043353411433580056253162888351545955n-1707392151094.jpeg"
//            };
//            int numberOfCores = Runtime.getRuntime().availableProcessors();
//            ExecutorService executorService = Executors.newFixedThreadPool(numberOfCores);
//            for (String url : imageUrls) {
//                executorService.submit(new MyThread(url, context));
//            }

//          Tạo Worker chạy 1 lần
//            WorkRequest builder = new OneTimeWorkRequest.Builder(MyWorker.class).build();
//            WorkManager workManager = WorkManager.getInstance(context);
//            workManager.enqueue(builder);

//          Tạo Worker chạy định kỳ
//            PeriodicWorkRequest.Builder builder = new PeriodicWorkRequest.Builder(MyWorker.class, 2, TimeUnit.MINUTES);
//            WorkManager workManager = WorkManager.getInstance(context);
//            workManager.enqueue(builder.build());

//          Khởi động Service để phát nhạc
//            startService(new Intent(this, MyService.class));

//          Hiển thị Notification
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
//                        .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Tiêu đề")
//                        .setContentText("Nội dung")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setSound(null);
//                notificationManager.notify(1, builder.build());
//            }

            if (isPlaying) {
                if (MyService.mediaPlayer != null && MyService.mediaPlayer.isPlaying()) {
                    MyService.mediaPlayer.pause();
                    isPlaying = false;
                    button.setText("Play");
                }
            } else {
                startService(new Intent(this, MyService.class));
                isPlaying = true;
                button.setText("Pause");
                updateSeekBar();
            }


        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && MyService.mediaPlayer != null) {
                    MyService.mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });

    }
    private void applyLanguageFromPrefs() {
        SharedPreferences prefs = getSharedPreferences("music_prefs", MODE_PRIVATE);
        String lang = prefs.getString("language", "vi");

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration(getResources().getConfiguration());
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
    private String formatTime(int millis) {
        int seconds = (millis / 1000) % 60;
        int minutes = (millis / 1000) / 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    //c3
    private void savePreferences() {
        SharedPreferences prefs = getSharedPreferences("music_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Lưu trạng thái lặp
        editor.putBoolean("is_looping", switchLooping.isChecked());

        // Lưu vị trí hiện tại (chỉ lưu khi nhạc đang chạy)
        if (MyService.mediaPlayer != null) {
            editor.putInt("current_position", MyService.mediaPlayer.getCurrentPosition());
        }

        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences("music_prefs", MODE_PRIVATE);

        // Khôi phục trạng thái lặp
        boolean isLooping = prefs.getBoolean("is_looping", true); // mặc định true
        switchLooping.setChecked(isLooping);
        if (MyService.mediaPlayer != null) {
            MyService.mediaPlayer.setLooping(isLooping);
        }

        // Khôi phục vị trí bản nhạc
        int savedPosition = prefs.getInt("current_position", 0); // mặc định 0
        if (MyService.mediaPlayer != null && savedPosition > 0) {
            MyService.mediaPlayer.seekTo(savedPosition);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences(); // Khôi phục khi mở app lên
        updateSeekBar();
    }

    private void updateSeekBar() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MyService.mediaPlayer != null && MyService.mediaPlayer.isPlaying()) {
                    isPlaying = true;
                    int current = MyService.mediaPlayer.getCurrentPosition();
                    int duration = MyService.mediaPlayer.getDuration();
                    seekBar.setMax(duration);
                    seekBar.setProgress(current);
                    // Cập nhật TextView thời gian
                    textViewTime.setText(formatTime(current) + " / " + formatTime(duration));
                }
                handler.postDelayed(this, 1000);
            }
        }, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePreferences();// lưu lại
        if (isPlaying && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setAction(Intent.ACTION_MAIN);
            notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Music")
                    .setContentText("Nhạc đang phát")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSound(null)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            notificationManager.notify(1, builder.build());
        }
    }

}