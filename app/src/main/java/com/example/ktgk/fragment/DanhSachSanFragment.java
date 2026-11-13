package com.example.ktgk.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktgk.MainActivity;
import com.example.ktgk.R;
import com.example.ktgk.adapter.SanBongAdapter;
import com.example.ktgk.database.DatabaseHelper;
import com.example.ktgk.model.SanBong;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DanhSachSanFragment extends Fragment implements SanBongAdapter.SuKienSanBongListener {

    private RecyclerView rvDanhSachSan;
    private TextView tvDanhSachSanRong;
    private EditText etTimKiemSan;
    private ExtendedFloatingActionButton fabThemSanBong;

    private DatabaseHelper dbHelper;
    private SanBongAdapter adapterSanBong;
    private List<SanBong> danhSachTatCaSan = new ArrayList<>();
    private List<SanBong> danhSachSanDaLoc = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_san, container, false);

        rvDanhSachSan = view.findViewById(R.id.rvDanhSachSan);
        tvDanhSachSanRong = view.findViewById(R.id.tvDanhSachSanRong);
        etTimKiemSan = view.findViewById(R.id.etTimKiemSan);
        fabThemSanBong = view.findViewById(R.id.fabThemSanBong);

        dbHelper = new DatabaseHelper(requireContext());

        rvDanhSachSan.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapterSanBong = new SanBongAdapter(requireContext(), danhSachSanDaLoc, this);
        rvDanhSachSan.setAdapter(adapterSanBong);

        fabThemSanBong.setOnClickListener(v -> hienThiDialogThemSan());

        etTimKiemSan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locDanhSachSan(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        taiDanhSachSan();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        taiDanhSachSan();
    }

    public void taiDanhSachSan() {
        if (dbHelper == null) return;
        danhSachTatCaSan = dbHelper.layDanhSachSanBong();
        locDanhSachSan(etTimKiemSan != null ? etTimKiemSan.getText().toString() : "");
    }

    private void locDanhSachSan(String tuKhoa) {
        danhSachSanDaLoc.clear();
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            danhSachSanDaLoc.addAll(danhSachTatCaSan);
        } else {
            String tuKhoaThuong = tuKhoa.trim().toLowerCase();
            for (SanBong san : danhSachTatCaSan) {
                if (san.getTenSan().toLowerCase().contains(tuKhoaThuong) ||
                    san.getMaSan().toLowerCase().contains(tuKhoaThuong) ||
                    (san.getDiaChi() != null && san.getDiaChi().toLowerCase().contains(tuKhoaThuong))) {
                    danhSachSanDaLoc.add(san);
                }
            }
        }
        adapterSanBong.setDanhSachSanBong(danhSachSanDaLoc);

        if (danhSachSanDaLoc.isEmpty()) {
            tvDanhSachSanRong.setVisibility(View.VISIBLE);
            rvDanhSachSan.setVisibility(View.GONE);
        } else {
            tvDanhSachSanRong.setVisibility(View.GONE);
            rvDanhSachSan.setVisibility(View.VISIBLE);
        }
    }

    private void hienThiDialogThemSan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_them_san, null);
        builder.setView(dialogView);

        TextInputEditText etMaSan = dialogView.findViewById(R.id.etMaSan);
        TextInputEditText etTenSan = dialogView.findViewById(R.id.etTenSan);
        TextInputEditText etDiaChiSan = dialogView.findViewById(R.id.etDiaChiSan);
        TextInputEditText etGiaThue = dialogView.findViewById(R.id.etGiaThue);

        builder.setPositiveButton("Thêm sân", null);
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String maSan = etMaSan.getText() != null ? etMaSan.getText().toString().trim() : "";
            String tenSan = etTenSan.getText() != null ? etTenSan.getText().toString().trim() : "";
            String diaChi = etDiaChiSan.getText() != null ? etDiaChiSan.getText().toString().trim() : "";
            String chuoiGia = etGiaThue.getText() != null ? etGiaThue.getText().toString().trim() : "";

            if (maSan.isEmpty()) {
                etMaSan.setError("Vui lòng nhập mã sân");
                return;
            }
            if (tenSan.isEmpty()) {
                etTenSan.setError("Vui lòng nhập tên sân");
                return;
            }
            if (chuoiGia.isEmpty()) {
                etGiaThue.setError("Vui lòng nhập giá thuê");
                return;
            }

            double giaThue;
            try {
                giaThue = Double.parseDouble(chuoiGia);
                if (giaThue <= 0) {
                    etGiaThue.setError("Giá thuê phải lớn hơn 0");
                    return;
                }
            } catch (NumberFormatException e) {
                etGiaThue.setError("Giá thuê không hợp lệ");
                return;
            }

            if (dbHelper.kiemTraMaSanTonTai(maSan, -1)) {
                etMaSan.setError("Mã sân này đã tồn tại!");
                return;
            }

            SanBong sanMoi = new SanBong(maSan, tenSan, diaChi, giaThue, "Còn trống");
            long ketQua = dbHelper.themSanBong(sanMoi);

            if (ketQua > 0) {
                Toast.makeText(requireContext(), "Thêm sân bóng thành công!", Toast.LENGTH_SHORT).show();
                taiDanhSachSan();
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Lỗi khi thêm sân bóng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDatSanClick(SanBong sanBong) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).chuyenDenManHinhDatSan(sanBong);
        }
    }

    @Override
    public void onXoaSanClick(SanBong sanBong) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận xóa sân")
                .setMessage("Bạn có chắc chắn muốn xóa sân bóng '" + sanBong.getTenSan() + "' (" + sanBong.getMaSan() + ")?\n\nLưu ý: Các đơn đặt sân liên quan cũng sẽ bị xóa.")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    int soDong = dbHelper.xoaSanBong(sanBong.getId());
                    if (soDong > 0) {
                        Toast.makeText(requireContext(), "Đã xóa sân bóng!", Toast.LENGTH_SHORT).show();
                        taiDanhSachSan();
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).lamMoiTatCaTab();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Lỗi khi xóa sân bóng!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}