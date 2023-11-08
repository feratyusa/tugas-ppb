package com.example.mahasiswaextended;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ImageView addButton, searchButton;
    private mahasiswaAdapter mAdapter;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper dbopen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView);
        addButton = findViewById(R.id.tambahButton);
        addButton.setOnClickListener(operasi);
        searchButton = findViewById(R.id.cariButton);
        searchButton.setOnClickListener(operasi);

        ArrayList<Mahasiswa> listMahasiswa = new ArrayList<Mahasiswa>();
        mAdapter = new mahasiswaAdapter(this, 0, listMahasiswa);

        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView nama = (TextView) view.findViewById(R.id.nama);
                TextView nrp = (TextView) view.findViewById(R.id.nrp);
                TextView ipk = (TextView) view.findViewById(R.id.ipk);
                edit_mahasiswa_dialog(nama.getText().toString(), nrp.getText().toString(), ipk.getText().toString());
            }
        });

        dbopen = new SQLiteOpenHelper(this, "mahasiswa.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {}
            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
        };

        dbku = dbopen.getWritableDatabase();
        dbku.execSQL("create table if not exists mahasiswa(nama TEXT, nrp TEXT, ipk REAL)");
        refresh_list_view();
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.tambahButton){
                tambah_data_dialog();
            } else if (view.getId() == R.id.cariButton) {
                search_mahasiswa_dialog();
            }
        }
    };
    private void refresh_list_view(){
        mAdapter.clear();
        Cursor cur = dbku.rawQuery("select * from mahasiswa", null);
        Toast.makeText(this, "Data sejumlah " + cur.getCount(), Toast.LENGTH_LONG).show();
        int i=0;
        if(cur.getCount() > 0) cur.moveToFirst();
        while(i < cur.getCount()){
            insert_mahasiswa_to_adapter(cur.getString(cur.getColumnIndex("nama")),
                    cur.getString(cur.getColumnIndex("nrp")),
                    cur.getDouble(cur.getColumnIndex("ipk")));
            cur.moveToNext();
            i++;
        }
    }
    private void add_mahasiswa(String nama, String nrp, Double ipk){
        ContentValues datanya = new ContentValues();
        datanya.put("nama", nama);
        datanya.put("nrp", nrp);
        datanya.put("ipk", ipk);
        dbku.insert("mahasiswa", null, datanya);
        insert_mahasiswa_to_adapter(nama, nrp, ipk);
    }
    private void insert_mahasiswa_to_adapter(String nama, String nrp, Double ipk){
        Mahasiswa m = new Mahasiswa(nama, nrp, ipk);
        mAdapter.add(m);
    }
    private void edit_mahasiswa(String nama, String nrp, Double ipk){
        ContentValues datanya = new ContentValues();
        datanya.put("nama", nama);
        datanya.put("nrp", nrp);
        datanya.put("ipk", ipk);
        dbku.update("mahasiswa", datanya, "nrp='"+nrp+"'",null);
    }
    private void delete_mahasiswa(String nrp){
        dbku.delete("mahasiswa", "nrp='"+nrp+"'", null);
    }
    private void tambah_data_dialog(){
        AlertDialog.Builder buat = new AlertDialog.Builder(this);
        buat.setTitle("Tambah Mahasiswa");

        View vAdd = LayoutInflater.from(this).inflate(R.layout.add_mahasiswa,null);
        final EditText nama = (EditText) vAdd.findViewById(R.id.inputNama);
        final EditText nrp = (EditText) vAdd.findViewById(R.id.inputNRP);
        final EditText ipk = (EditText) vAdd.findViewById(R.id.inputIPK);

        buat.setView(vAdd);

        buat.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                add_mahasiswa(nama.getText().toString(), nrp.getText().toString(), Double.parseDouble(ipk.getText().toString()));
                Toast.makeText(getBaseContext(), "Data Tersimpan", Toast.LENGTH_LONG).show();
                dialogInterface.dismiss();
            }
        });
        buat.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        buat.show();
    }

    private void search_mahasiswa_dialog() {
        AlertDialog.Builder cari = new AlertDialog.Builder(this);
        cari.setTitle("Cari Mahasiswa");

        View vSearch = LayoutInflater.from(this).inflate(R.layout.search_mahasiswa, null);
        final EditText nrp = (EditText) vSearch.findViewById(R.id.input_s_nrp);
        final TextView hasil = (TextView) vSearch.findViewById(R.id.view_s_mahasiswa);
        final ImageView btn = (ImageView) vSearch.findViewById(R.id.cari_s_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur = dbku.rawQuery("select * from mahasiswa where nrp='" + nrp.getText().toString() + "'", null);
                if (cur.getCount() == 0) {
                    hasil.setText("Tidak ada Data yang Ditemukan");
                } else if (cur.getCount() == 1) {
                    cur.moveToFirst();
                    Mahasiswa m = new Mahasiswa(cur.getString(cur.getColumnIndex("nama")),
                            cur.getString(cur.getColumnIndex("nrp")),
                            cur.getDouble(cur.getColumnIndex("ipk")));
                    hasil.setText(m.toString());
                } else {
                    hasil.setText("Something is wrong");
                }
            }
        });

        cari.setView(vSearch);

        cari.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        cari.show();
    }
    private void edit_mahasiswa_dialog(String e_nama, String e_nrp, String e_ipk){
        AlertDialog.Builder edit = new AlertDialog.Builder(this);
        edit.setTitle("Edit Mahasiswa");

        View vAdd = LayoutInflater.from(this).inflate(R.layout.edit_mahasiswa,null);
        final EditText nama = (EditText) vAdd.findViewById(R.id.input_e_Nama);
        final TextView nrp = (TextView) vAdd.findViewById(R.id.input_e_NRP);
        final EditText ipk = (EditText) vAdd.findViewById(R.id.input_e_IPK);

        nama.setText(e_nama);
        nrp.setText(e_nrp);
        ipk.setText(e_ipk);

        edit.setView(vAdd);

        edit.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edit_mahasiswa(nama.getText().toString(), nrp.getText().toString(), Double.parseDouble(ipk.getText().toString()));
                Toast.makeText(getBaseContext(), "Data Terupdate", Toast.LENGTH_LONG).show();
                refresh_list_view();
                dialogInterface.dismiss();
            }
        });
        edit.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete_mahasiswa(nrp.getText().toString());
                Toast.makeText(MainActivity.this, "Data Terhapus", Toast.LENGTH_LONG).show();
                refresh_list_view();
                dialogInterface.dismiss();
            }
        });
        edit.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        edit.show();
    }

    @Override
    protected void onStop() {
        dbku.close();
        dbopen.close();
        super.onStop();
    }
}