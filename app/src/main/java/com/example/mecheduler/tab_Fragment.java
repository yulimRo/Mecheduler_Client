package com.example.mecheduler;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class tab_Fragment extends FragmentPagerAdapter {

    private String[] VIEW_MAPNTOP_TITLES = {"진료 현황", "복용 안내", "나의 병원", "자가 진단"};
    private ArrayList<Fragment> fList;

    public tab_Fragment(FragmentManager fm, ArrayList<Fragment> fList) {
        super(fm);
        this.fList = fList;
    }

    public tab_Fragment(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    // tab title를 반환 함수.
    @Override
    public CharSequence getPageTitle(int position) {

        return VIEW_MAPNTOP_TITLES[position];

    }

    // 해당 프라그먼트 호출 함수.
    @Override
    public Fragment getItem(int position) {
        return this.fList.get(position);
    }

    @Override
    public int getCount() {
        return fList.size();
    }
}
