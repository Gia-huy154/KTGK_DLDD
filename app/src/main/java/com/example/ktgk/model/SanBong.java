package com.example.ktgk.model;

import java.io.Serializable;

public class SanBong implements Serializable {
    private int id;
    private String maSan;
    private String tenSan;
    private String diaChi;
    private double giaThueTheoGio;
    private String trangThai; // "Còn trống" hoặc "Đã được đặt"

    public SanBong() {
    }

    public SanBong(int id, String maSan, String tenSan, String diaChi, double giaThueTheoGio, String trangThai) {
        this.id = id;
        this.maSan = maSan;
        this.tenSan = tenSan;
        this.diaChi = diaChi;
        this.giaThueTheoGio = giaThueTheoGio;
        this.trangThai = trangThai;
    }

    public SanBong(String maSan, String tenSan, String diaChi, double giaThueTheoGio, String trangThai) {
        this.maSan = maSan;
        this.tenSan = tenSan;
        this.diaChi = diaChi;
        this.giaThueTheoGio = giaThueTheoGio;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSan() {
        return maSan;
    }

    public void setMaSan(String maSan) {
        this.maSan = maSan;
    }

    public String getTenSan() {
        return tenSan;
    }

    public void setTenSan(String tenSan) {
        this.tenSan = tenSan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public double getGiaThueTheoGio() {
        return giaThueTheoGio;
    }

    public void setGiaThueTheoGio(double giaThueTheoGio) {
        this.giaThueTheoGio = giaThueTheoGio;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return tenSan + " (" + maSan + ")";
    }
}