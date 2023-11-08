package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText bilangan_satu, bilangan_dua;
    private TextView hasil_operasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bilangan_satu = findViewById(R.id.bilanganSatu);
        bilangan_dua = findViewById(R.id.bilanganDua);
        hasil_operasi = findViewById(R.id.viewHasil);
    }

    public void operasi(View v){
        float bil1, bil2, hasil=0;

        bil1 = (!(bilangan_satu.getText() + "").equals("")) ? Float.parseFloat(bilangan_satu.getText()+"") : 0;
        bil2 = (!(bilangan_dua.getText() + "").equals("")) ? Float.parseFloat(bilangan_dua.getText()+"") : 0;
        if(v.getId() == R.id.plus){
            hasil = bil1 + bil2;
        }
        else if(v.getId() == R.id.minus){
            hasil = bil1 - bil2;
        }
        else if(v.getId() == R.id.times){
            hasil = bil1 * bil2;
        }
        else if(v.getId() == R.id.divide){
            hasil = bil1 / bil2;
        }

        Button btn = findViewById(v.getId());

        String textString = bil1 + " " + btn.getText() + " " + bil2 + " = " + hasil;
        hasil_operasi.setText(textString);
    }
}