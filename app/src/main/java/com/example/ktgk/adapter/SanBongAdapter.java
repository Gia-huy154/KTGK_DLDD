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
import com.example.ktgk.model.SanBong;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SanBongAdapter extends RecyclerView.Adapter<SanBongAdapter.SanBongViewHolder> {

    public interface SuKienSanBongListener {
        void onDatSanClick(SanBong sanBong);
        void onXoaSanClick(SanBong sanBong);
    }

    private final Context context;
    private List<SanBong> danhSachSanBong;
    private final SuKienSanBongListener listener;
    private final DecimalFormat dinhDangTien = new DecimalFormat("#,###");

    public SanBongAdapter(Context context, List<SanBong> danhSachSanBong, SuKienSanBongListener listener) {
        this.context = context;
        this.danhSachSanBong = danhSachSanBong != null ? danhSachSanBong : new ArrayList<>();
        this.listener = listener;
    }

    public void setDanhSachSanBong(List<SanBong> danhSachSanBong) {
        this.danhSachSanBong = danhSachSanBong != null ? danhSachSanBong : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SanBongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_san_bong, parent, false);
        return new SanBongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanBongViewHolder holder, int position) {
        SanBong sanBong = danhSachSanBong.get(position);

        holder.tvMaSan.setText(sanBong.getMaSan());
        holder.tvTenSan.setText(sanBong.getTenSan());
        holder.tvDiaChiSan.setText(sanBong.getDiaChi() != null && !sanBong.getDiaChi().isEmpty() ? sanBong.getDiaChi() : "Chưa có địa chỉ");
        holder.tvGiaThue.setText(dinhDangTien.format(sanBong.getGiaThueTheoGio()) + " VNĐ/giờ");

        String trangThai = sanBong.getTrangThai();
        holder.tvTrangThaiSan.setText(trangThai);

        if ("Còn trống".equalsIgnoreCase(trangThai)) {
            holder.tvTrangThaiSan.setBackgroundResource(R.drawable.bg_badge_available);
            holder.tvTrangThaiSan.setTextColor(ContextCompat.getColor(context, R.color.status_available_text));
        } else {
            holder.tvTrangThaiSan.setBackgroundResource(R.drawable.bg_badge_booked);
            holder.tvTrangThaiSan.setTextColor(ContextCompat.getColor(context, R.color.status_booked_text));
        }

        holder.btnDatSan.setOnClickListener(v -> {
            if (listener != null) listener.onDatSanClick(sanBong);
        });

        holder.btnXoaSan.setOnClickListener(v -> {
            if (listener != null) listener.onXoaSanClick(sanBong);
        });
    }

    @Override
    public int getItemCount() {
        return danhSachSanBong.size();
    }

    static class SanBongViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaSan, tvTenSan, tvDiaChiSan, tvGiaThue, tvTrangThaiSan;
        MaterialButton btnXoaSan, btnDatSan;

        public SanBongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSan = itemView.findViewById(R.id.tvMaSan);
            tvTenSan = itemView.findViewById(R.id.tvTenSan);
            tvDiaChiSan = itemView.findViewById(R.id.tvDiaChiSan);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvTrangThaiSan = itemView.findViewById(R.id.tvTrangThaiSan);
            btnXoaSan = itemView.findViewById(R.id.btnXoaSan);
            btnDatSan = itemView.findViewById(R.id.btnDatSan);
        }
    }
}