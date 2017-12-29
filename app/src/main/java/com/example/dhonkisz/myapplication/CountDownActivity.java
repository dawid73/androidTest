package com.example.dhonkisz.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CountDownActivity extends AppCompatActivity {

    EditText message, title, seconds;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        title = (EditText) findViewById(R.id.editTextTitle);
        message = (EditText) findViewById(R.id.editTextMessage);
        seconds = (EditText) findViewById(R.id.editTextSeconds);

        btn = (Button) findViewById(R.id.btnStartCount);
    }

    public void zacznijOdliczanie(View view) {
        long startTime = Long.parseLong(seconds.getText().toString()) * 1000;
        new CountDownTimer(startTime, 1000) {
            @Override
            public void onTick(long l) {
                seconds.setEnabled(false);
                btn.setEnabled(false);
                seconds.setText(""+l/1000);
            }

            @Override
            public void onFinish() {
                createNotyfication();
                seconds.setEnabled(true);
                btn.setEnabled(true);
            }
        }.start();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotyfication(){
        Notification notification = new Notification.Builder(this)
                .setContentTitle(title.getText())
                .setContentText(message.getText())
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setSmallIcon(R.drawable.ico)
                //.setChannelId(channelId)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
