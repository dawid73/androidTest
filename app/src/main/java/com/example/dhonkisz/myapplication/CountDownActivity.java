package com.example.dhonkisz.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CountDownActivity extends AppCompatActivity {

    EditText message, title, seconds;
    Button btn;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        title = (EditText) findViewById(R.id.editTextTitle);
        message = (EditText) findViewById(R.id.editTextMessage);
        seconds = (EditText) findViewById(R.id.editTextSeconds);

        btn = (Button) findViewById(R.id.btnStartCount);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(activity, description, Toast.LENGTH_LONG).show();
            }

        });
        webView.loadUrl("http://www.google.pl");
        setContentView(webView);
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
