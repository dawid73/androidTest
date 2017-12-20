package com.example.dhonkisz.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.NotActiveException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private String path = Environment.getExternalStorageDirectory().toString()+"/zapisaneDaneZAplikacji";

    //klucz który definieuje uprawnienie które nas interesuje w AndoidManifest
    private final int MEMORY_ACCESS = 5;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //zapytanie o dostep do plików
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        }else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MEMORY_ACCESS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case MEMORY_ACCESS:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                }
                else {
                    Toast.makeText(getApplicationContext(), "Jeśli nie zostanie wyrażona zgoda na dostęp do pamieci, nie mogę dalej działąć", Toast.LENGTH_LONG).show();
                }

        }

    }

    public void klikniecie(View view) {
        switch (view.getId()){
            case R.id.przycisk1:
                intent = new Intent(MainActivity.this, PierwszeActivity.class);
                startActivity(intent);
                break;
            case R.id.przycisk2:
                intent = new Intent(MainActivity.this, PierwszeActivity.class);
                System.out.println("przycisniety drugi przycisk");
                startActivity(intent);
                break;
            case R.id.logowanie:
                intent = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void zapisz(View view) {
            createDir();
            createFile();

    }

    public void createDir(){
        File folder = new File(path);
        if(!folder.exists()){
            try {
                folder.mkdir();
            }catch (Exception e){
                Log.d("problem", "problem przy utworzeniu katalogu");
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


    public void createFile() {
        File file = new File(path+"/"+System.currentTimeMillis()+".txt");
        FileOutputStream fout;
        OutputStreamWriter myOutWriter;
        try {
            fout = new FileOutputStream(file);
            myOutWriter = new OutputStreamWriter(fout);
            myOutWriter.append("test");
            myOutWriter.close();
            fout.close();
        }catch (Exception e){
            Log.d("problem", "problem przy utworzeniu pliku");
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
