package com.example.myapplication;

public class Persegi {
    private int panjang;
    private int lebar;
    private int luas;

    public Persegi(int panjang, int lebar) {
        this.panjang = panjang;
        this.lebar = lebar;
        this.luas = panjang*lebar;
    }

    @Override
    public String toString() {
        return  "\n-----" +
                "\nPanjang = " + this.panjang +
                "\nLebar = " + this.lebar +
                "\nLuas = " + this.luas;
    }
}
