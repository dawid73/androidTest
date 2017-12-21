package com.example.dhonkisz.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements CommonColors {

    private String path = Environment.getExternalStorageDirectory().toString()+"/zapisaneDaneZAplikacji";

    //klucz który definieuje uprawnienie które nas interesuje w AndoidManifest
    final private int MULTIPLE_PERMISSIONS = 12345;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            insertPermissionsWrapper();

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(MainActivity.this, "Nie przydzielono któregoś pozwolenia", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertPermissionsWrapper(){List<String> permissionsNeeded = new ArrayList<>();

        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("zapisu pamięci wewnętrznej");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("odczytu pamięci wewnętrznej");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "Potrzebujemy dostępu do " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                message = message + " celem prawidłowego działania aplikacji";
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    MULTIPLE_PERMISSIONS);
            return;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Anuluj", null)
                .create()
                .show();
    }

    @TargetApi(23)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }


    @Override
    public void setNaviBarColor() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }
}
