package com.example.transaksi_penjualan_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText pembeli, barang, jumlah_barang, harga_barang, uang_bayar;
    private TextView v_pembeli, v_barang, v_jumlah_barang, v_harga_barang, v_uang_bayar, v_total_belanja,
            v_uang_kembalian, v_bonus, v_keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Edit Text
        pembeli = findViewById(R.id.pembeli);
        barang = findViewById(R.id.barang);
        jumlah_barang = findViewById(R.id.jumlah_barang);
        harga_barang = findViewById(R.id.harga);
        uang_bayar = findViewById(R.id.uang_bayar);

        // View Text
        v_pembeli = findViewById(R.id.valuePembeli);
        v_barang = findViewById(R.id.valueBarang);
        v_jumlah_barang = findViewById(R.id.valueJumlahBarang);
        v_harga_barang = findViewById(R.id.valueHargaBarang);
        v_uang_bayar = findViewById(R.id.valueUangBayar);
        v_total_belanja = findViewById(R.id.valueTotalBelanja);
        v_bonus = findViewById(R.id.valueBonus);
        v_uang_kembalian = findViewById(R.id.valueUangKembalian);
        v_keterangan = findViewById(R.id.valueKeterangan);
    }

    private String getBonus(int number){
        switch (number){
            case 0:
                return "HardDisk 1TB";
            case 1:
                return "SSD 512GB";
            case 2:
                return "Mouse Pad";
        }
        return "";
    }

    public void onProses(View v){
        int jumlahBarang = Integer.parseInt(jumlah_barang.getText()+"");
        int hargaBarang = Integer.parseInt(harga_barang.getText()+"");
        int uangBayar = Integer.parseInt(uang_bayar.getText()+"");
        double totalBelanja = jumlahBarang * hargaBarang;
        double uangKembalian =uangBayar - totalBelanja;

        v_pembeli.setText(pembeli.getText());
        v_barang.setText(barang.getText());
        v_jumlah_barang.setText(jumlah_barang.getText());
        v_harga_barang.setText(harga_barang.getText());
        v_uang_bayar.setText(uang_bayar.getText());
        v_total_belanja.setText(String.valueOf(totalBelanja));
        v_uang_kembalian.setText(String.valueOf(uangKembalian));
        // Generate random bonus
        Random randNum = new Random();
        v_bonus.setText(getBonus(randNum.nextInt(3)));
        v_keterangan.setText("Tunggu kembalian");

    }

    public void onHapusData(View v){
        String kosong = "Kosong";
        v_pembeli.setText(kosong);
        v_barang.setText(kosong);
        v_jumlah_barang.setText(kosong);
        v_harga_barang.setText(kosong);
        v_uang_bayar.setText(kosong);
        v_total_belanja.setText(kosong);
        v_uang_kembalian.setText(kosong);
        v_bonus.setText(kosong);
        v_keterangan.setText(kosong);

    }

    public void onKeluar(View v){
        finish();
        System.exit(0);
    }

}