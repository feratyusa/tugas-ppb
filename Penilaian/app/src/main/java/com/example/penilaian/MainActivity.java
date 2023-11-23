package com.example.penilaian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button hasilButton = findViewById(R.id.buttonHasil);
        hasilButton.setOnClickListener(op);
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.buttonHasil) {
                hitungHasil();
            }
        }
    };

    private double hitungNilaiAngka(String nilai_huruf){
        if( nilai_huruf.equals("A")) return 4.00;
        else if (nilai_huruf.equals("AB")) return 3.50;
        else if (nilai_huruf.equals("B")) return 3.00;
        else if (nilai_huruf.equals("BC")) return 2.50;
        else if (nilai_huruf.equals("C")) return 2.00;
        else if (nilai_huruf.equals("D")) return 1.00;
        else return 0.00;
    }

    private String hitungIPK(Double nilai_angka){
        if(nilai_angka == 4.00) return "A";
        else if(nilai_angka >= 3.50) return "AB";
        else if(nilai_angka >= 3.00) return "B";
        else if(nilai_angka >= 2.50) return "BC";
        else if(nilai_angka >= 2.00) return "C";
        else if(nilai_angka >= 1.00) return "D";
        else return "E";
    }

    private void hitungHasil(){
        EditText nama = findViewById(R.id.inputNama);
        EditText nrp = findViewById(R.id.inputNRP);
        EditText txt_nilai_mate = findViewById(R.id.inputMatematika);
        EditText txt_nilai_dasprog = findViewById(R.id.inputDasprog);
        EditText txt_nilai_strukdat = findViewById(R.id.inputStrukdat);
        TextView hasil = findViewById(R.id.hasil);

        Double nilai_mate = hitungNilaiAngka(txt_nilai_mate.getText().toString());
        Double nilai_dasprog = hitungNilaiAngka(txt_nilai_dasprog.getText().toString());
        Double nilai_strukdat = hitungNilaiAngka(txt_nilai_strukdat.getText().toString());

        String ipk = hitungIPK((nilai_mate+nilai_dasprog+nilai_strukdat) / 3);

        String text_hasil = "Nama: "+nama.getText().toString()+"\n"
                            + "NRP: "+nrp.getText().toString()+"\n"
                            + "Matematika: "+txt_nilai_mate.getText().toString()+"("+nilai_mate+")\n"
                            + "Dasar Pemrograman: "+txt_nilai_dasprog.getText().toString()+"("+nilai_dasprog+")\n"
                            + "Struktur Data: "+txt_nilai_strukdat.getText().toString()+"("+nilai_strukdat+")\n"
                            + "IPK: "+ipk;
        hasil.setText(text_hasil);
    }
}