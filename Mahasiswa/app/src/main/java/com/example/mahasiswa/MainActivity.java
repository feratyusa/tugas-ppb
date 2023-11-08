package com.example.mahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nama, nrp;
    private TextView result;
//    private Button simpan, update, ambil, hapus;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper Opendb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = findViewById(R.id.inputNama);
        nrp = findViewById(R.id.inputNRP);

        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {}
            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
        };
        dbku = Opendb.getWritableDatabase();
        dbku.execSQL("create table if not exists " +
                        "mhs(nama TEXT, nrp TEXT);");
    }

    public void simpanFunction(View v){
        ContentValues dataku = new ContentValues();
        dataku.put("nama", nama.getText()+"");
        dataku.put("nrp", nrp.getText()+"");
        dbku.insert("mhs", null, dataku);
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_LONG).show();
    }
    public void ambilFunction(View v){
        Cursor cur = dbku.rawQuery("select * from mhs where nrp='"+
                                        nrp.getText().toString() + "'", null);
        if (cur.getCount() > 0) {
            Toast.makeText(this, "Data ditemukan sejumlah "+cur.getCount(), Toast.LENGTH_LONG).show();
            cur.moveToFirst();
            nama.setText(cur.getString(cur.getColumnIndex("nama")));
        }
        else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_LONG).show();
        }
    }
    public void updateFunction(View v){
        ContentValues dataku = new ContentValues();
        dataku.put("nama", nama.getText()+"");
        dataku.put("nrp", nrp.getText()+"");
        dbku.update("mhs",dataku, "nrp='"+nrp.getText().toString()+"'",null);
        Toast.makeText(this, "Data Terupdate", Toast.LENGTH_LONG).show();
    }
    public void hapusFunction(View v){
        dbku.delete("mhs", "nrp='"+nrp.getText().toString()+"'",null);
        Toast.makeText(this, "Data Terhapus", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        dbku.close();
        Opendb.close();
        super.onStop();
    }
}