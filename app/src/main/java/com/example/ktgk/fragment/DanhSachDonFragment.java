package com.example.ktgk.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktgk.MainActivity;
import com.example.ktgk.R;
import com.example.ktgk.adapter.DonDatSanAdapter;
import com.example.ktgk.database.DatabaseHelper;
import com.example.ktgk.model.DonDatSan;
import com.google.android.material.chip.ChipGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DanhSachDonFragment extends Fragment implements DonDatSanAdapter.SuKienDonDatListener {

    private RecyclerView rvDanhSachDon;
    private TextView tvDanhSachDonRong;
    private ChipGroup chipGroupLocDon;

    private DatabaseHelper dbHelper;
    private DonDatSanAdapter adapterDonDatSan;
    private List<DonDatSan> danhSachTatCaDon = new ArrayList<>();
    private List<DonDatSan> danhSachDonDaLoc = new ArrayList<>();
    private final DecimalFormat dinhDangTien = new DecimalFormat("#,###");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_don, container, false);

        rvDanhSachDon = view.findViewById(R.id.rvDanhSachDon);
        tvDanhSachDonRong = view.findViewById(R.id.tvDanhSachDonRong);
        chipGroupLocDon = view.findViewById(R.id.chipGroupLocDon);

        dbHelper = new DatabaseHelper(requireContext());

        rvDanhSachDon.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapterDonDatSan = new DonDatSanAdapter(requireContext(), danhSachDonDaLoc, this);
        rvDanhSachDon.setAdapter(adapterDonDatSan);

        chipGroupLocDon.setOnCheckedStateChangeListener((group, checkedIds) -> locDanhSachDon());

        taiDanhSachDon();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        taiDanhSachDon();
    }

    public void taiDanhSachDon() {
        if (dbHelper == null) return;
        danhSachTatCaDon = dbHelper.layDanhSachDonDatSan();
        locDanhSachDon();
    }

    private void locDanhSachDon() {
        danhSachDonDaLoc.clear();
        int chipDuocChon = chipGroupLocDon != null ? chipGroupLocDon.getCheckedChipId() : -1;

        for (DonDatSan don : danhSachTatCaDon) {
            if (chipDuocChon == R.id.chipDangDat) {
                if ("Đã đặt".equalsIgnoreCase(don.getTrangThai())) {
                    danhSachDonDaLoc.add(don);
                }
            } else if (chipDuocChon == R.id.chipDaHuy) {
                if ("Đã hủy".equalsIgnoreCase(don.getTrangThai())) {
                    danhSachDonDaLoc.add(don);
                }
            } else {
                danhSachDonDaLoc.add(don);
            }
        }

        adapterDonDatSan.setDanhSachDonDat(danhSachDonDaLoc);

        if (danhSachDonDaLoc.isEmpty()) {
            tvDanhSachDonRong.setVisibility(View.VISIBLE);
            rvDanhSachDon.setVisibility(View.GONE);
        } else {
            tvDanhSachDonRong.setVisibility(View.GONE);
            rvDanhSachDon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onChiTietClick(DonDatSan donDatSan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_chi_tiet_don, null);
        builder.setView(dialogView);

        TextView tvChiTietTrangThai = dialogView.findViewById(R.id.tvChiTietTrangThai);
        TextView tvChiTietTenNguoiDat = dialogView.findViewById(R.id.tvChiTietTenNguoiDat);
        TextView tvChiTietSoDienThoai = dialogView.findViewById(R.id.tvChiTietSoDienThoai);
        TextView tvChiTietTenSan = dialogView.findViewById(R.id.tvChiTietTenSan);
        TextView tvChiTietNgayThue = dialogView.findViewById(R.id.tvChiTietNgayThue);
        TextView tvChiTietGioThue = dialogView.findViewById(R.id.tvChiTietGioThue);
        TextView tvChiTietTongTien = dialogView.findViewById(R.id.tvChiTietTongTien);

        String trangThai = donDatSan.getTrangThai();
        tvChiTietTrangThai.setText(trangThai);
        if ("Đã đặt".equalsIgnoreCase(trangThai)) {
            tvChiTietTrangThai.setBackgroundResource(R.drawable.bg_badge_available);
            tvChiTietTrangThai.setTextColor(ContextCompat.getColor(requireContext(), R.color.status_available_text));
        } else {
            tvChiTietTrangThai.setBackgroundResource(R.drawable.bg_badge_cancelled);
            tvChiTietTrangThai.setTextColor(ContextCompat.getColor(requireContext(), R.color.status_cancelled_text));
        }

        tvChiTietTenNguoiDat.setText(donDatSan.getTenNguoiDat());
        tvChiTietSoDienThoai.setText(donDatSan.getSoDienThoai());
        tvChiTietTenSan.setText(donDatSan.getTenSanBong() + " (" + donDatSan.getMaSanBong() + ")");
        tvChiTietNgayThue.setText(donDatSan.getNgayThue());

        String chuoiGio = (donDatSan.getSoGioThue() == (long) donDatSan.getSoGioThue()) ?
                String.format("%.0f", donDatSan.getSoGioThue()) : String.format("%.1f", donDatSan.getSoGioThue());
        tvChiTietGioThue.setText(donDatSan.getGioBatDau() + " (" + chuoiGio + " giờ)");

        tvChiTietTongTien.setText(dinhDangTien.format(donDatSan.getTongTien()) + " VNĐ");

        builder.setPositiveButton("Đóng", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void onHuyDonClick(DonDatSan donDatSan) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận hủy đơn")
                .setMessage("Bạn có chắc chắn muốn hủy đơn đặt sân cho khách hàng '" + donDatSan.getTenNguoiDat() + "'?\n\nTrạng thái sân bóng sẽ được cập nhật lại tương ứng.")
                .setPositiveButton("Hủy đơn", (dialog, which) -> {
                    int soDong = dbHelper.capNhatTrangThaiDonDat(donDatSan.getId(), "Đã hủy");
                    if (soDong > 0) {
                        Toast.makeText(requireContext(), "Đã hủy đơn đặt sân!", Toast.LENGTH_SHORT).show();
                        taiDanhSachDon();
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).lamMoiTatCaTab();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Lỗi khi hủy đơn!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Quay lại", null)
                .show();
    }
}