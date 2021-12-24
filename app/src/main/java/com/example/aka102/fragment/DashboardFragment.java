package com.example.aka102.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.aka102.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = view.findViewById(R.id.viewPagerdasbord);
        TabLayout tabLayout =  view.findViewById(R.id.tblayout);
        HomeAdapter adapter = new HomeAdapter(getChildFragmentManager());
        adapter.addFragment(new MenuAkaMartFragment(), "AKa Mart");
        adapter.addFragment(new MenuPesanFragment() , "Pesan");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    class HomeAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final  List<String> mFragmentTitleList = new ArrayList<>();

        public HomeAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position){
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
        @Override
        public  int getCount(){
            return mFragmentList.size();
        }



    }
}