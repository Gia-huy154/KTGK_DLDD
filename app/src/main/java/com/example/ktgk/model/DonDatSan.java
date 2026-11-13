package com.example.ktgk.model;

import java.io.Serializable;

public class DonDatSan implements Serializable {
    private int id;
    private int idSanBong;
    private String tenSanBong;
    private String maSanBong;
    private String tenNguoiDat;
    private String soDienThoai;
    private String ngayThue;     // "dd/MM/yyyy"
    private String gioBatDau;    // "HH:mm"
    private double soGioThue;
    private double tongTien;
    private String trangThai;    // "Đã đặt" hoặc "Đã hủy"

    public DonDatSan() {
    }

    public DonDatSan(int id, int idSanBong, String tenSanBong, String maSanBong, String tenNguoiDat,
                     String soDienThoai, String ngayThue, String gioBatDau,
                     double soGioThue, double tongTien, String trangThai) {
        this.id = id;
        this.idSanBong = idSanBong;
        this.tenSanBong = tenSanBong;
        this.maSanBong = maSanBong;
        this.tenNguoiDat = tenNguoiDat;
        this.soDienThoai = soDienThoai;
        this.ngayThue = ngayThue;
        this.gioBatDau = gioBatDau;
        this.soGioThue = soGioThue;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public DonDatSan(int idSanBong, String tenSanBong, String maSanBong, String tenNguoiDat,
                     String soDienThoai, String ngayThue, String gioBatDau,
                     double soGioThue, double tongTien, String trangThai) {
        this.idSanBong = idSanBong;
        this.tenSanBong = tenSanBong;
        this.maSanBong = maSanBong;
        this.tenNguoiDat = tenNguoiDat;
        this.soDienThoai = soDienThoai;
        this.ngayThue = ngayThue;
        this.gioBatDau = gioBatDau;
        this.soGioThue = soGioThue;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSanBong() {
        return idSanBong;
    }

    public void setIdSanBong(int idSanBong) {
        this.idSanBong = idSanBong;
    }

    public String getTenSanBong() {
        return tenSanBong;
    }

    public void setTenSanBong(String tenSanBong) {
        this.tenSanBong = tenSanBong;
    }

    public String getMaSanBong() {
        return maSanBong;
    }

    public void setMaSanBong(String maSanBong) {
        this.maSanBong = maSanBong;
    }

    public String getTenNguoiDat() {
        return tenNguoiDat;
    }

    public void setTenNguoiDat(String tenNguoiDat) {
        this.tenNguoiDat = tenNguoiDat;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public double getSoGioThue() {
        return soGioThue;
    }

    public void setSoGioThue(double soGioThue) {
        this.soGioThue = soGioThue;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}