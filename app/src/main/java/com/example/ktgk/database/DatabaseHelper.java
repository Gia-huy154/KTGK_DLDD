package com.example.ktgk.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ktgk.model.DonDatSan;
import com.example.ktgk.model.SanBong;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuanLySanBong.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_SAN_BONG = "san_bong";
    public static final String COLUMN_SAN_ID = "id";
    public static final String COLUMN_SAN_MA = "ma_san";
    public static final String COLUMN_SAN_TEN = "ten_san";
    public static final String COLUMN_SAN_DIA_CHI = "dia_chi";
    public static final String COLUMN_SAN_GIA_THUE = "gia_thue_theo_gio";
    public static final String COLUMN_SAN_TRANG_THAI = "trang_thai";

    public static final String TABLE_DON_DAT_SAN = "don_dat_san";
    public static final String COLUMN_DON_ID = "id";
    public static final String COLUMN_DON_ID_SAN = "id_san_bong";
    public static final String COLUMN_DON_TEN_NGUOI_DAT = "ten_nguoi_dat";
    public static final String COLUMN_DON_SO_DIEN_THOAI = "so_dien_thoai";
    public static final String COLUMN_DON_NGAY_THUE = "ngay_thue";
    public static final String COLUMN_DON_GIO_BAT_DAU = "gio_bat_dau";
    public static final String COLUMN_DON_SO_GIO_THUE = "so_gio_thue";
    public static final String COLUMN_DON_TONG_TIEN = "tong_tien";
    public static final String COLUMN_DON_TRANG_THAI = "trang_thai";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSanBongTable = "CREATE TABLE " + TABLE_SAN_BONG + " (" +
                COLUMN_SAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SAN_MA + " TEXT UNIQUE NOT NULL, " +
                COLUMN_SAN_TEN + " TEXT NOT NULL, " +
                COLUMN_SAN_DIA_CHI + " TEXT, " +
                COLUMN_SAN_GIA_THUE + " REAL NOT NULL, " +
                COLUMN_SAN_TRANG_THAI + " TEXT DEFAULT 'Còn trống'" +
                ");";

        String createDonDatSanTable = "CREATE TABLE " + TABLE_DON_DAT_SAN + " (" +
                COLUMN_DON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DON_ID_SAN + " INTEGER NOT NULL, " +
                COLUMN_DON_TEN_NGUOI_DAT + " TEXT NOT NULL, " +
                COLUMN_DON_SO_DIEN_THOAI + " TEXT NOT NULL, " +
                COLUMN_DON_NGAY_THUE + " TEXT NOT NULL, " +
                COLUMN_DON_GIO_BAT_DAU + " TEXT NOT NULL, " +
                COLUMN_DON_SO_GIO_THUE + " REAL NOT NULL, " +
                COLUMN_DON_TONG_TIEN + " REAL NOT NULL, " +
                COLUMN_DON_TRANG_THAI + " TEXT DEFAULT 'Đã đặt', " +
                "FOREIGN KEY(" + COLUMN_DON_ID_SAN + ") REFERENCES " + TABLE_SAN_BONG + "(" + COLUMN_SAN_ID + ") ON DELETE CASCADE" +
                ");";

        db.execSQL(createSanBongTable);
        db.execSQL(createDonDatSanTable);

        chenSanBongMau(db);
        chenDonDatSanMau(db);
    }

    private void chenSanBongMau(SQLiteDatabase db) {
        ContentValues cv1 = new ContentValues();
        cv1.put(COLUMN_SAN_MA, "SAN01");
        cv1.put(COLUMN_SAN_TEN, "Sân Bóng Thống Nhất");
        cv1.put(COLUMN_SAN_DIA_CHI, "139 Lý Thường Kiệt, Q.10, TP.HCM");
        cv1.put(COLUMN_SAN_GIA_THUE, 200000.0);
        cv1.put(COLUMN_SAN_TRANG_THAI, "Còn trống");
        db.insert(TABLE_SAN_BONG, null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put(COLUMN_SAN_MA, "SAN02");
        cv2.put(COLUMN_SAN_TEN, "Sân Cỏ Nhân Tạo Mỹ Đình");
        cv2.put(COLUMN_SAN_DIA_CHI, "Đường Lê Đức Thọ, Nam Từ Liêm, Hà Nội");
        cv2.put(COLUMN_SAN_GIA_THUE, 250000.0);
        cv2.put(COLUMN_SAN_TRANG_THAI, "Còn trống");
        db.insert(TABLE_SAN_BONG, null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(COLUMN_SAN_MA, "SAN03");
        cv3.put(COLUMN_SAN_TEN, "Sân Bóng Tân Bình");
        cv3.put(COLUMN_SAN_DIA_CHI, "Cộng Hòa, Tân Bình, TP.HCM");
        cv3.put(COLUMN_SAN_GIA_THUE, 180000.0);
        cv3.put(COLUMN_SAN_TRANG_THAI, "Còn trống");
        db.insert(TABLE_SAN_BONG, null, cv3);

        ContentValues cv4 = new ContentValues();
        cv4.put(COLUMN_SAN_MA, "SAN04");
        cv4.put(COLUMN_SAN_TEN, "Sân Mini Hoàng Gia");
        cv4.put(COLUMN_SAN_DIA_CHI, "12 Nguyễn Văn Linh, Q.7, TP.HCM");
        cv4.put(COLUMN_SAN_GIA_THUE, 220000.0);
        cv4.put(COLUMN_SAN_TRANG_THAI, "Còn trống");
        db.insert(TABLE_SAN_BONG, null, cv4);
    }

    private void chenDonDatSanMau(SQLiteDatabase db) {
        ContentValues cv1 = new ContentValues();
        cv1.put(COLUMN_DON_ID_SAN, 1);
        cv1.put(COLUMN_DON_TEN_NGUOI_DAT, "Nguyễn Văn An");
        cv1.put(COLUMN_DON_SO_DIEN_THOAI, "0912345678");
        cv1.put(COLUMN_DON_NGAY_THUE, "15/11/2026");
        cv1.put(COLUMN_DON_GIO_BAT_DAU, "14:00");
        cv1.put(COLUMN_DON_SO_GIO_THUE, 2.0);
        cv1.put(COLUMN_DON_TONG_TIEN, 400000.0);
        cv1.put(COLUMN_DON_TRANG_THAI, "Đã đặt");
        db.insert(TABLE_DON_DAT_SAN, null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put(COLUMN_DON_ID_SAN, 2);
        cv2.put(COLUMN_DON_TEN_NGUOI_DAT, "Trần Thị Bình");
        cv2.put(COLUMN_DON_SO_DIEN_THOAI, "0987654321");
        cv2.put(COLUMN_DON_NGAY_THUE, "16/11/2026");
        cv2.put(COLUMN_DON_GIO_BAT_DAU, "17:30");
        cv2.put(COLUMN_DON_SO_GIO_THUE, 1.5);
        cv2.put(COLUMN_DON_TONG_TIEN, 375000.0);
        cv2.put(COLUMN_DON_TRANG_THAI, "Đã đặt");
        db.insert(TABLE_DON_DAT_SAN, null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(COLUMN_DON_ID_SAN, 3);
        cv3.put(COLUMN_DON_TEN_NGUOI_DAT, "Lê Hoàng Cường");
        cv3.put(COLUMN_DON_SO_DIEN_THOAI, "0905123456");
        cv3.put(COLUMN_DON_NGAY_THUE, "14/11/2026");
        cv3.put(COLUMN_DON_GIO_BAT_DAU, "08:00");
        cv3.put(COLUMN_DON_SO_GIO_THUE, 1.0);
        cv3.put(COLUMN_DON_TONG_TIEN, 180000.0);
        cv3.put(COLUMN_DON_TRANG_THAI, "Đã hủy");
        db.insert(TABLE_DON_DAT_SAN, null, cv3);

        ContentValues cvPitch1 = new ContentValues();
        cvPitch1.put(COLUMN_SAN_TRANG_THAI, "Đã được đặt");
        db.update(TABLE_SAN_BONG, cvPitch1, COLUMN_SAN_ID + " = 1", null);

        ContentValues cvPitch2 = new ContentValues();
        cvPitch2.put(COLUMN_SAN_TRANG_THAI, "Đã được đặt");
        db.update(TABLE_SAN_BONG, cvPitch2, COLUMN_SAN_ID + " = 2", null);
    }

    private void kiemTraKiemTraDonMau() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_DON_DAT_SAN, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            if (count == 0) {
                chenDonDatSanMau(db);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DON_DAT_SAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAN_BONG);
        onCreate(db);
    }

    public List<SanBong> layDanhSachSanBong() {
        kiemTraKiemTraDonMau();
        List<SanBong> danhSach = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SAN_BONG + " ORDER BY " + COLUMN_SAN_ID + " DESC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SAN_ID));
                String maSan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SAN_MA));
                String tenSan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SAN_TEN));
                String diaChi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SAN_DIA_CHI));
                double giaThue = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SAN_GIA_THUE));
                String trangThai = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SAN_TRANG_THAI));

                danhSach.add(new SanBong(id, maSan, tenSan, diaChi, giaThue, trangThai));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return danhSach;
    }

    public boolean kiemTraMaSanTonTai(String maSan, int idTruRa) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_SAN_ID + " FROM " + TABLE_SAN_BONG + " WHERE LOWER(" + COLUMN_SAN_MA + ") = LOWER(?) AND " + COLUMN_SAN_ID + " != ?",
                new String[]{maSan.trim(), String.valueOf(idTruRa)});
        boolean tonTai = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return tonTai;
    }

    public long themSanBong(SanBong sanBong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SAN_MA, sanBong.getMaSan().trim().toUpperCase());
        cv.put(COLUMN_SAN_TEN, sanBong.getTenSan().trim());
        cv.put(COLUMN_SAN_DIA_CHI, sanBong.getDiaChi() != null ? sanBong.getDiaChi().trim() : "");
        cv.put(COLUMN_SAN_GIA_THUE, sanBong.getGiaThueTheoGio());
        cv.put(COLUMN_SAN_TRANG_THAI, sanBong.getTrangThai() != null ? sanBong.getTrangThai() : "Còn trống");
        return db.insert(TABLE_SAN_BONG, null, cv);
    }

    public int xoaSanBong(int idSanBong) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DON_DAT_SAN, COLUMN_DON_ID_SAN + " = ?", new String[]{String.valueOf(idSanBong)});
        return db.delete(TABLE_SAN_BONG, COLUMN_SAN_ID + " = ?", new String[]{String.valueOf(idSanBong)});
    }

    public void capNhatTrangThaiSanBong(int idSanBong) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_DON_DAT_SAN + " WHERE " + COLUMN_DON_ID_SAN + " = ? AND " + COLUMN_DON_TRANG_THAI + " = 'Đã đặt'",
                new String[]{String.valueOf(idSanBong)});
        int soLuongDon = 0;
        if (cursor != null && cursor.moveToFirst()) {
            soLuongDon = cursor.getInt(0);
            cursor.close();
        }

        String trangThaiMoi = (soLuongDon > 0) ? "Đã được đặt" : "Còn trống";
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SAN_TRANG_THAI, trangThaiMoi);
        db.update(TABLE_SAN_BONG, cv, COLUMN_SAN_ID + " = ?", new String[]{String.valueOf(idSanBong)});
    }

    public List<DonDatSan> layDanhSachDonDatSan() {
        kiemTraKiemTraDonMau();
        List<DonDatSan> danhSach = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT d.*, s." + COLUMN_SAN_TEN + " AS ten_san_alias, s." + COLUMN_SAN_MA + " AS ma_san_alias " +
                "FROM " + TABLE_DON_DAT_SAN + " d " +
                "LEFT JOIN " + TABLE_SAN_BONG + " s ON d." + COLUMN_DON_ID_SAN + " = s." + COLUMN_SAN_ID + " " +
                "ORDER BY d." + COLUMN_DON_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DON_ID));
                int idSanBong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DON_ID_SAN));
                String tenNguoiDat = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DON_TEN_NGUOI_DAT));
                String soDienThoai = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DON_SO_DIEN_THOAI));
                String ngayThue = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DON_NGAY_THUE));
                String gioBatDau = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DON_GIO_BAT_DAU));
                double soGioThue = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DON_SO_GIO_THUE));
                double tongTien = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DON_TONG_TIEN));
                String trangThai = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DON_TRANG_THAI));

                String tenSanBong = cursor.getString(cursor.getColumnIndexOrThrow("ten_san_alias"));
                String maSanBong = cursor.getString(cursor.getColumnIndexOrThrow("ma_san_alias"));
                if (tenSanBong == null) tenSanBong = "Sân đã bị xóa";
                if (maSanBong == null) maSanBong = "N/A";

                danhSach.add(new DonDatSan(id, idSanBong, tenSanBong, maSanBong, tenNguoiDat, soDienThoai,
                        ngayThue, gioBatDau, soGioThue, tongTien, trangThai));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return danhSach;
    }

    public long themDonDatSan(DonDatSan donDatSan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DON_ID_SAN, donDatSan.getIdSanBong());
        cv.put(COLUMN_DON_TEN_NGUOI_DAT, donDatSan.getTenNguoiDat().trim());
        cv.put(COLUMN_DON_SO_DIEN_THOAI, donDatSan.getSoDienThoai().trim());
        cv.put(COLUMN_DON_NGAY_THUE, donDatSan.getNgayThue().trim());
        cv.put(COLUMN_DON_GIO_BAT_DAU, donDatSan.getGioBatDau().trim());
        cv.put(COLUMN_DON_SO_GIO_THUE, donDatSan.getSoGioThue());
        cv.put(COLUMN_DON_TONG_TIEN, donDatSan.getTongTien());
        cv.put(COLUMN_DON_TRANG_THAI, donDatSan.getTrangThai() != null ? donDatSan.getTrangThai() : "Đã đặt");

        long id = db.insert(TABLE_DON_DAT_SAN, null, cv);
        if (id != -1) {
            capNhatTrangThaiSanBong(donDatSan.getIdSanBong());
        }
        return id;
    }

    public int capNhatTrangThaiDonDat(int idDonDat, String trangThaiMoi) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_DON_ID_SAN + " FROM " + TABLE_DON_DAT_SAN + " WHERE " + COLUMN_DON_ID + " = ?",
                new String[]{String.valueOf(idDonDat)});
        int idSanBong = -1;
        if (cursor != null && cursor.moveToFirst()) {
            idSanBong = cursor.getInt(0);
            cursor.close();
        }

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DON_TRANG_THAI, trangThaiMoi);
        int rows = db.update(TABLE_DON_DAT_SAN, cv, COLUMN_DON_ID + " = ?", new String[]{String.valueOf(idDonDat)});

        if (idSanBong != -1) {
            capNhatTrangThaiSanBong(idSanBong);
        }
        return rows;
    }

    public boolean kiemTraTrungLichDat(int idSanBong, String ngayThue, String gioBatDau, double soGioThue, int idDonTruRa) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_DON_GIO_BAT_DAU + ", " + COLUMN_DON_SO_GIO_THUE +
                " FROM " + TABLE_DON_DAT_SAN +
                " WHERE " + COLUMN_DON_ID_SAN + " = ? AND " + COLUMN_DON_NGAY_THUE + " = ? AND " +
                COLUMN_DON_TRANG_THAI + " = 'Đã đặt' AND " + COLUMN_DON_ID + " != ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idSanBong), ngayThue.trim(), String.valueOf(idDonTruRa)});

        int phutBatDauMoi = chuyenGioThanhPhut(gioBatDau);
        int phutKetThucMoi = phutBatDauMoi + (int) Math.round(soGioThue * 60);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String gioBatDauHienTai = cursor.getString(0);
                double soGioThueHienTai = cursor.getDouble(1);

                int phutBatDauHienTai = chuyenGioThanhPhut(gioBatDauHienTai);
                int phutKetThucHienTai = phutBatDauHienTai + (int) Math.round(soGioThueHienTai * 60);

                if (Math.max(phutBatDauMoi, phutBatDauHienTai) < Math.min(phutKetThucMoi, phutKetThucHienTai)) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return false;
    }

    private int chuyenGioThanhPhut(String gioChuoi) {
        try {
            String[] parts = gioChuoi.trim().split(":");
            int gio = Integer.parseInt(parts[0]);
            int phut = Integer.parseInt(parts[1]);
            return gio * 60 + phut;
        } catch (Exception e) {
            return 0;
        }
    }
}