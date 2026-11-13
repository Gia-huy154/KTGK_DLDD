package com.example.ktgk.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.ktgk.MainActivity;
import com.example.ktgk.R;
import com.example.ktgk.database.DatabaseHelper;
import com.example.ktgk.model.DonDatSan;
import com.example.ktgk.model.SanBong;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DatSanFragment extends Fragment {

    private Spinner spChonSan;
    private TextInputEditText etTenNguoiDat, etSoDienThoai, etNgayThue, etGioBatDau, etSoGioThue;
    private TextView tvTongTienTinh, tvCongThucTinh;
    private MaterialButton btnXacNhanDatSan;

    private DatabaseHelper dbHelper;
    private List<SanBong> danhSachSan = new ArrayList<>();
    private ArrayAdapter<SanBong> adapterSpinnerSan;
    private SanBong sanBongDuocChon = null;
    private final DecimalFormat dinhDangTien = new DecimalFormat("#,###");

    private int idSanPreSelect = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dat_san, container, false);

        spChonSan = view.findViewById(R.id.spChonSan);
        etTenNguoiDat = view.findViewById(R.id.etTenNguoiDat);
        etSoDienThoai = view.findViewById(R.id.etSoDienThoai);
        etNgayThue = view.findViewById(R.id.etNgayThue);
        etGioBatDau = view.findViewById(R.id.etGioBatDau);
        etSoGioThue = view.findViewById(R.id.etSoGioThue);
        tvTongTienTinh = view.findViewById(R.id.tvTongTienTinh);
        tvCongThucTinh = view.findViewById(R.id.tvCongThucTinh);
        btnXacNhanDatSan = view.findViewById(R.id.btnXacNhanDatSan);

        dbHelper = new DatabaseHelper(requireContext());

        thietLapChonNgay();
        thietLapChonGio();
        thietLapTheoDoiSoGio();
        thietLapSpinnerSan();

        btnXacNhanDatSan.setOnClickListener(v -> xuLyDatSan());

        taiDanhSachSanBong();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        taiDanhSachSanBong();
    }

    public void taiDanhSachSanBong() {
        if (dbHelper == null) return;
        danhSachSan = dbHelper.layDanhSachSanBong();

        if (getContext() != null) {
            adapterSpinnerSan = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, danhSachSan);
            adapterSpinnerSan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spChonSan.setAdapter(adapterSpinnerSan);

            if (idSanPreSelect != -1) {
                for (int i = 0; i < danhSachSan.size(); i++) {
                    if (danhSachSan.get(i).getId() == idSanPreSelect) {
                        spChonSan.setSelection(i);
                        break;
                    }
                }
            }
        }
        tinhTongTien();
    }

    public void chonSanPreSelect(SanBong sanBong) {
        if (sanBong != null) {
            this.idSanPreSelect = sanBong.getId();
            if (danhSachSan != null && !danhSachSan.isEmpty()) {
                for (int i = 0; i < danhSachSan.size(); i++) {
                    if (danhSachSan.get(i).getId() == sanBong.getId()) {
                        if (spChonSan != null) {
                            spChonSan.setSelection(i);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void thietLapSpinnerSan() {
        spChonSan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < danhSachSan.size()) {
                    sanBongDuocChon = danhSachSan.get(position);
                    tinhTongTien();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sanBongDuocChon = null;
                tinhTongTien();
            }
        });
    }

    private void thietLapChonNgay() {
        Calendar lich = Calendar.getInstance();
        capNhatTruongNgay(lich.get(Calendar.YEAR), lich.get(Calendar.MONTH), lich.get(Calendar.DAY_OF_MONTH));

        etNgayThue.setOnClickListener(v -> {
            int nam = lich.get(Calendar.YEAR);
            int thang = lich.get(Calendar.MONTH);
            int ngay = lich.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(requireContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
                lich.set(selectedYear, selectedMonth, selectedDay);
                capNhatTruongNgay(selectedYear, selectedMonth, selectedDay);
            }, nam, thang, ngay);
            dialog.show();
        });
    }

    private void capNhatTruongNgay(int nam, int thang, int ngay) {
        String chuoiNgay = String.format(Locale.getDefault(), "%02d/%02d/%04d", ngay, thang + 1, nam);
        etNgayThue.setText(chuoiNgay);
    }

    private void thietLapChonGio() {
        etGioBatDau.setText("14:00");

        etGioBatDau.setOnClickListener(v -> {
            int gio = 14;
            int phut = 0;

            try {
                String chuoiGio = etGioBatDau.getText().toString();
                if (chuoiGio.contains(":")) {
                    String[] parts = chuoiGio.split(":");
                    gio = Integer.parseInt(parts[0]);
                    phut = Integer.parseInt(parts[1]);
                }
            } catch (Exception ignored) {}

            TimePickerDialog dialog = new TimePickerDialog(requireContext(), (view, selectedHour, selectedMinute) -> {
                String chuoiGioDinhDang = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                etGioBatDau.setText(chuoiGioDinhDang);
            }, gio, phut, true);
            dialog.show();
        });
    }

    private void thietLapTheoDoiSoGio() {
        etSoGioThue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tinhTongTien();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void tinhTongTien() {
        if (sanBongDuocChon == null) {
            tvTongTienTinh.setText("0 VNĐ");
            tvCongThucTinh.setText("Chưa chọn sân bóng");
            return;
        }

        String chuoiSoGio = etSoGioThue.getText() != null ? etSoGioThue.getText().toString().trim() : "";
        double soGio = 0;
        try {
            if (!chuoiSoGio.isEmpty()) {
                soGio = Double.parseDouble(chuoiSoGio);
            }
        } catch (NumberFormatException ignored) {}

        double tongTien = sanBongDuocChon.getGiaThueTheoGio() * soGio;
        tvTongTienTinh.setText(dinhDangTien.format(tongTien) + " VNĐ");
        tvCongThucTinh.setText(String.format("Công thức: %s VNĐ × %.1f giờ", dinhDangTien.format(sanBongDuocChon.getGiaThueTheoGio()), soGio));
    }

    private void xuLyDatSan() {
        if (sanBongDuocChon == null) {
            Toast.makeText(requireContext(), "Vui lòng chọn sân bóng!", Toast.LENGTH_SHORT).show();
            return;
        }

        String tenNguoiDat = etTenNguoiDat.getText() != null ? etTenNguoiDat.getText().toString().trim() : "";
        String soDienThoai = etSoDienThoai.getText() != null ? etSoDienThoai.getText().toString().trim() : "";
        String chuoiNgay = etNgayThue.getText() != null ? etNgayThue.getText().toString().trim() : "";
        String chuoiGio = etGioBatDau.getText() != null ? etGioBatDau.getText().toString().trim() : "";
        String chuoiSoGio = etSoGioThue.getText() != null ? etSoGioThue.getText().toString().trim() : "";

        if (tenNguoiDat.isEmpty()) {
            etTenNguoiDat.setError("Vui lòng nhập họ và tên người đặt");
            etTenNguoiDat.requestFocus();
            return;
        }

        if (soDienThoai.isEmpty()) {
            etSoDienThoai.setError("Vui lòng nhập số điện thoại");
            etSoDienThoai.requestFocus();
            return;
        }

        if (chuoiNgay.isEmpty()) {
            etNgayThue.setError("Vui lòng chọn ngày thuê sân");
            return;
        }

        if (chuoiGio.isEmpty()) {
            etGioBatDau.setError("Vui lòng chọn giờ bắt đầu");
            return;
        }

        double soGioThue;
        try {
            soGioThue = Double.parseDouble(chuoiSoGio);
            if (soGioThue <= 0) {
                etSoGioThue.setError("Số giờ thuê phải lớn hơn 0");
                etSoGioThue.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etSoGioThue.setError("Số giờ thuê không hợp lệ");
            etSoGioThue.requestFocus();
            return;
        }

        boolean biTrung = dbHelper.kiemTraTrungLichDat(sanBongDuocChon.getId(), chuoiNgay, chuoiGio, soGioThue, -1);
        if (biTrung) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Lịch đặt bị trùng!")
                    .setMessage("Sân bóng '" + sanBongDuocChon.getTenSan() + "' đã có lịch đặt trùng với khung giờ " + chuoiGio + " ngày " + chuoiNgay + ".\n\nVui lòng chọn sân khác hoặc thay đổi thời gian thuê.")
                    .setPositiveButton("Đã hiểu", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }

        double tongTien = sanBongDuocChon.getGiaThueTheoGio() * soGioThue;

        DonDatSan donMoi = new DonDatSan(
                sanBongDuocChon.getId(),
                sanBongDuocChon.getTenSan(),
                sanBongDuocChon.getMaSan(),
                tenNguoiDat,
                soDienThoai,
                chuoiNgay,
                chuoiGio,
                soGioThue,
                tongTien,
                "Đã đặt"
        );

        long ketQua = dbHelper.themDonDatSan(donMoi);
        if (ketQua > 0) {
            Toast.makeText(requireContext(), "Đặt sân thành công!", Toast.LENGTH_SHORT).show();
            etTenNguoiDat.setText("");
            etSoDienThoai.setText("");

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).lamMoiTatCaTab();
                ((MainActivity) getActivity()).chuyenTab(2);
            }
        } else {
            Toast.makeText(requireContext(), "Lỗi khi lưu đơn đặt sân!", Toast.LENGTH_SHORT).show();
        }
    }
}