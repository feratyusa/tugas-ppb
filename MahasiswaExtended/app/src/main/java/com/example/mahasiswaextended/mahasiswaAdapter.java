package com.example.mahasiswaextended;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class mahasiswaAdapter extends ArrayAdapter<Mahasiswa> {
    private static class ViewHolder{
        TextView nama;
        TextView nrp;
        TextView ipk;
    }

    public mahasiswaAdapter(Context context, int resource, List<Mahasiswa> objects){
        super(context, resource, objects);
    }

    public View getView(int position, View ConvertView, ViewGroup parent){
        Mahasiswa dtmahasiswa = getItem(position);

        if(ConvertView==null){
            ConvertView = LayoutInflater.from(getContext()).inflate(R.layout.item_mahasiswa,parent,false);
        }
        TextView txtNama = (TextView) ConvertView.findViewById(R.id.nama);
        TextView txtNRP = (TextView) ConvertView.findViewById(R.id.nrp);
        TextView txtIPK = (TextView) ConvertView.findViewById(R.id.ipk);

        txtNama.setText(dtmahasiswa.getNama());
        txtNRP.setText(dtmahasiswa.getNrp());
        txtIPK.setText(dtmahasiswa.getIpk());

        return ConvertView;
    }
}
