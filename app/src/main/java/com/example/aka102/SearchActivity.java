package com.example.aka102;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aka102.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_search,
                new SearchFragment()).commit();
    }



}