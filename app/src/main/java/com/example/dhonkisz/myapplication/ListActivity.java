package com.example.dhonkisz.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;

public class ListActivity extends AppCompatActivity {
    String include = "\n\n\n///";
    private String path = Environment.getExternalStorageDirectory().toString()+"/zapisaneDaneZAplikacji/";
    TextView tekst_lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        tekst_lista = (TextView) findViewById(R.id.tekst_lista);
        tekst_lista.setText(getAllContent());
    }

    public String getAllContent(){
        try {
            File file = new File(path);
            String[] lista = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    return s.endsWith(".txt");
                }
            });
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<lista.length; i++){
                sb.append(Files.toString(new File(path+lista[i]), Charsets.UTF_8));
            sb.append(include);
            }
            return  sb.toString();
        } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                return "";
            }

    }
}
