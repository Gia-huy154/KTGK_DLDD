package com.example.ktgk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktgk.R;
import com.example.ktgk.model.DonDatSan;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DonDatSanAdapter extends RecyclerView.Adapter<DonDatSanAdapter.DonDatViewHolder> {

    public interface SuKienDonDatListener {
        void onChiTietClick(DonDatSan donDatSan);
        void onHuyDonClick(DonDatSan donDatSan);
    }

    private final Context context;
    private List<DonDatSan> danhSachDonDat;
    private final SuKienDonDatListener listener;
    private final DecimalFormat dinhDangTien = new DecimalFormat("#,###");

    public DonDatSanAdapter(Context context, List<DonDatSan> danhSachDonDat, SuKienDonDatListener listener) {
        this.context = context;
        this.danhSachDonDat = danhSachDonDat != null ? danhSachDonDat : new ArrayList<>();
        this.listener = listener;
    }

    public void setDanhSachDonDat(List<DonDatSan> danhSachDonDat) {
        this.danhSachDonDat = danhSachDonDat != null ? danhSachDonDat : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonDatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_don_dat_san, parent, false);
        return new DonDatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonDatViewHolder holder, int position) {
        DonDatSan donDatSan = danhSachDonDat.get(position);

        holder.tvTenNguoiDat.setText(donDatSan.getTenNguoiDat());
        holder.tvThongTinSan.setText(donDatSan.getTenSanBong() + " (" + donDatSan.getMaSanBong() + ")");
        holder.tvSoDienThoai.setText("SĐT: " + donDatSan.getSoDienThoai());

        String chuoiGio = (donDatSan.getSoGioThue() == (long) donDatSan.getSoGioThue()) ?
                String.format("%.0f", donDatSan.getSoGioThue()) : String.format("%.1f", donDatSan.getSoGioThue());

        holder.tvThoiGianThue.setText("Ngày: " + donDatSan.getNgayThue() + " | " + donDatSan.getGioBatDau() + " (" + chuoiGio + " giờ)");
        holder.tvTongTien.setText(dinhDangTien.format(donDatSan.getTongTien()) + " VNĐ");

        String trangThai = donDatSan.getTrangThai();
        holder.tvTrangThaiDon.setText(trangThai);

        if ("Đã đặt".equalsIgnoreCase(trangThai)) {
            holder.tvTrangThaiDon.setBackgroundResource(R.drawable.bg_badge_available);
            holder.tvTrangThaiDon.setTextColor(ContextCompat.getColor(context, R.color.status_available_text));
            holder.btnHuyDon.setVisibility(View.VISIBLE);
        } else {
            holder.tvTrangThaiDon.setBackgroundResource(R.drawable.bg_badge_cancelled);
            holder.tvTrangThaiDon.setTextColor(ContextCompat.getColor(context, R.color.status_cancelled_text));
            holder.btnHuyDon.setVisibility(View.GONE);
        }

        holder.btnChiTietDon.setOnClickListener(v -> {
            if (listener != null) listener.onChiTietClick(donDatSan);
        });

        holder.btnHuyDon.setOnClickListener(v -> {
            if (listener != null) listener.onHuyDonClick(donDatSan);
        });
    }

    @Override
    public int getItemCount() {
        return danhSachDonDat.size();
    }

    static class DonDatViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNguoiDat, tvThongTinSan, tvSoDienThoai, tvThoiGianThue, tvTongTien, tvTrangThaiDon;
        MaterialButton btnHuyDon, btnChiTietDon;

        public DonDatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNguoiDat = itemView.findViewById(R.id.tvTenNguoiDat);
            tvThongTinSan = itemView.findViewById(R.id.tvThongTinSan);
            tvSoDienThoai = itemView.findViewById(R.id.tvSoDienThoai);
            tvThoiGianThue = itemView.findViewById(R.id.tvThoiGianThue);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            tvTrangThaiDon = itemView.findViewById(R.id.tvTrangThaiDon);
            btnHuyDon = itemView.findViewById(R.id.btnHuyDon);
            btnChiTietDon = itemView.findViewById(R.id.btnChiTietDon);
        }
    }
}