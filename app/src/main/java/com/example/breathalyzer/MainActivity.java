package com.example.breathalyzer;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.breathalyzer.ui.main.SectionsPagerAdapter;
import com.txusballesteros.widgets.FitChart;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        FitChart fitChart = (FitChart)findViewById(R.id.progress);
        TextView bacValue = findViewById(R.id.bacValue);
        TextView timeLeft = findViewById(R.id.timeLeft);
        fitChart.setMinValue(0f);
        fitChart.setMaxValue(1f);
        fitChart.setValue(.56f);
        bacValue.setText(".56");
        new CountDownTimer(3000000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeLeft.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timeLeft.setText("Sober");
            }
        }.start();
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
//        FloatingActionButton fab = findViewById(R.id.fab);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}