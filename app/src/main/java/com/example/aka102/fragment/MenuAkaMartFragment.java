package com.example.aka102.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aka102.R;

public class MenuAkaMartFragment extends Fragment {

    EditText radius, height,lukis;
    TextView tv_vol, tv_luas, tv_kel;
    Button hasil;

    double vol, luas, luasper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_akamart_fragment, container, false);

        return v;
    }



}