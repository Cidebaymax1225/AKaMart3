package com.example.aka102.fragment;

import android.app.Activity;
import android.content.Intent;
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
import com.example.aka102.SetingActivity;
import com.example.aka102.SearchActivity;

public class MenuPesanFragment extends Fragment {

    EditText side;
    TextView tv_vol, tv_luas, tv_kel;
    Button hasil;

    double vol, luas, kel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_pesan_fragment, container, false);
        hasil = (Button) v.findViewById(R.id.has);

        side = (EditText) v.findViewById(R.id.side);
        tv_vol = (TextView) v.findViewById(R.id.volume);
        tv_luas = (TextView) v.findViewById(R.id.area);
        tv_kel = (TextView)v.findViewById(R.id.kel);

        hasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (side.getText().toString().trim().length() == 0) {
                    Toast.makeText(getContext(), "Diisi dulu dong", Toast.LENGTH_SHORT).show();
                } else {
                    double edit1 = Double.valueOf(side.getText().toString());
                    vol = edit1 * edit1 * edit1;
                    luas = 6 * edit1 * edit1;
                    kel = 12*edit1;
                    tv_vol.setText(String.valueOf(vol));
                    tv_luas.setText(String.valueOf(luas));
                    tv_kel.setText(String.valueOf(kel));
                }
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        menu.findItem(R.id.action_new_pesan).setVisible(true);
        menu.findItem(R.id.action_Contact).setVisible(true);
        menu.findItem(R.id.action_Search).setVisible(true);



        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_new_pesan){
            Intent intent = new Intent(getActivity(), SetingActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0,0);
        }

        if (id == R.id.action_Contact){
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0,0);
        }

        if (id == R.id.action_Search){
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0,0);
        }


        return super.onOptionsItemSelected(item);
    }
}