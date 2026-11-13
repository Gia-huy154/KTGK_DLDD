package com.example.ktgk.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ktgk.fragment.DanhSachDonFragment;
import com.example.ktgk.fragment.DanhSachSanFragment;
import com.example.ktgk.fragment.DatSanFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final DanhSachSanFragment danhSachSanFragment;
    private final DatSanFragment datSanFragment;
    private final DanhSachDonFragment danhSachDonFragment;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        danhSachSanFragment = new DanhSachSanFragment();
        datSanFragment = new DatSanFragment();
        danhSachDonFragment = new DanhSachDonFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return danhSachSanFragment;
            case 1:
                return datSanFragment;
            case 2:
                return danhSachDonFragment;
            default:
                return danhSachSanFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public DanhSachSanFragment getDanhSachSanFragment() {
        return danhSachSanFragment;
    }

    public DatSanFragment getDatSanFragment() {
        return datSanFragment;
    }

    public DanhSachDonFragment getDanhSachDonFragment() {
        return danhSachDonFragment;
    }
}