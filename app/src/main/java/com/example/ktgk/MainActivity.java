package com.example.ktgk;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ktgk.adapter.ViewPagerAdapter;
import com.example.ktgk.model.SanBong;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(3);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.tab_pitch_list);
                    break;
                case 1:
                    tab.setText(R.string.tab_book_pitch);
                    break;
                case 2:
                    tab.setText(R.string.tab_booking_list);
                    break;
            }
        }).attach();
    }

    public void chuyenTab(int viTri) {
        if (viewPager != null && viTri >= 0 && viTri < adapter.getItemCount()) {
            viewPager.setCurrentItem(viTri, true);
        }
    }

    public void chuyenDenManHinhDatSan(SanBong sanBong) {
        chuyenTab(1);
        if (adapter != null && adapter.getDatSanFragment() != null) {
            adapter.getDatSanFragment().chonSanPreSelect(sanBong);
        }
    }

    public void lamMoiTatCaTab() {
        if (adapter != null) {
            if (adapter.getDanhSachSanFragment() != null) {
                adapter.getDanhSachSanFragment().taiDanhSachSan();
            }
            if (adapter.getDatSanFragment() != null) {
                adapter.getDatSanFragment().taiDanhSachSanBong();
            }
            if (adapter.getDanhSachDonFragment() != null) {
                adapter.getDanhSachDonFragment().taiDanhSachDon();
            }
        }
    }
}