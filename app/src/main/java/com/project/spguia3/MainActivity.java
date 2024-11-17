package com.project.spguia3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText etTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etTexto=findViewById(R.id.etTexto);

        String[] archivos=fileList();

        if (existe(archivos,"notas.txt")){
            try {
                InputStreamReader isr=new InputStreamReader(openFileInput("notas.txt"));
                BufferedReader br=new BufferedReader(isr);
                String linea=br.readLine();
                String todo="";
                while (linea!=null){
                    todo=todo+linea+"\n";
                    linea=br.readLine();
                }
                br.close();
                isr.close();
                etTexto.setText(todo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private boolean existe(String[]archivos, String nombre){
        for (int i=0; i<archivos.length; i++){
            if (nombre.equals(archivos[i]));
            return true;
        }
        return false;
    }

    public void grabar(View view){
        try {
            OutputStreamWriter osw=new OutputStreamWriter(openFileOutput("notas.txt", Activity.MODE_PRIVATE));
            osw.write(etTexto.getText().toString());
            osw.flush();
            osw.close();
        }catch (IOException e){
//            throw new RuntimeException();
        }
        Toast t=Toast.makeText(this, "Los datos fueron ingresados", Toast.LENGTH_SHORT);
        t.show();
        finish();
    }
}