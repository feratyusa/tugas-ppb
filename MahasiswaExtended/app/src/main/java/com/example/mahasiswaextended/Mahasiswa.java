package com.example.mahasiswaextended;

public class Mahasiswa {
    private String nama;
    private String nrp;
    private double ipk;
    public Mahasiswa(String nama, String nrp, double ipk){
        this.nama = nama;
        this.nrp = nrp;
        this.ipk = ipk;
    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public String getIpk() {
        return Double.toString(ipk);
    }

    public void setIpk(float ipk) {
        this.ipk = ipk;
    }

    public String toString(){
        return("Nama: "+this.nama+"\n"
                +"NRP: "+this.nrp+"\n"
                +"IPK: "+this.ipk+"\n");
    }
}
