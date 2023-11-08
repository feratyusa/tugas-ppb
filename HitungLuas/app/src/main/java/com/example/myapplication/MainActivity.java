package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText txtPanjang, txtLebar;
    TextView txtRes;
    ArrayList <Persegi> ap = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //code ini untuk mapping dari XML
        txtPanjang = findViewById(R.id.panjang);
        txtLebar = findViewById(R.id.lebar);
        txtRes = findViewById(R.id.textResult);
    }

    public void klikOk(View v){

        //ambil nilai
        int panjang= Integer.parseInt(txtPanjang.getText()+"");
        int lebar = Integer.parseInt(txtLebar.getText()+"");
//        String sjk = jk?"Laki-laki":"Wanita";
        ap.add(new Persegi(panjang, lebar));
        String result = "";
        for(Persegi p : ap){
            result += p;
        }
        txtRes.setText(result);
    }
}