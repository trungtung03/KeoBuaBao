package com.example.rac2;

public class Shop {
    int soLuong;
    int gia;

    public int getSoLuong() {
        return soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public Shop(int soLuong, int gia) {
        this.soLuong = soLuong;
        this.gia = gia;
    }
}
