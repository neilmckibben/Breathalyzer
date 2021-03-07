package com.example.breathalyzer;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import java.util.Random;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breathalyzer.ui.main.SectionsPagerAdapter;
import com.txusballesteros.widgets.FitChart;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_ENABLE_BT = 1;
    private final static int PERMISSION_CODE = 2;
    String TAG = "bluetoothStuff";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        FitChart fitChart = (FitChart)findViewById(R.id.progress);
        TextView bacValue = findViewById(R.id.bacValue);
        Button startButton = findViewById(R.id.startButton);
        fitChart.setMinValue(0f);
        fitChart.setMaxValue(1f);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                startInput();
            }
        });
    }

    private void startInput(){
        TextView title = findViewById(R.id.title);
        FitChart fitChart = (FitChart)findViewById(R.id.progress);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView bacValue = findViewById(R.id.bacValue);
        title.setText("");
        title.setVisibility(View.VISIBLE);
        fitChart.setMinValue(0f);
        fitChart.setMaxValue(1f);
        progressBar.setProgress(0);
        new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                title.setText("Starting in " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                title.setText("Blow!");
                progressBar.setVisibility(View.VISIBLE);
                new CountDownTimer(5000, 50) {
                    public void onTick(long millisUntilFinished) {  
                        progressBar.incrementProgressBy(50);
                    }
                    public void onFinish() {
                        title.setText("Blood Alcohol Content");
                        progressBar.setVisibility(View.INVISIBLE);
                        float val = (float) Math.random();
                        fitChart.setValue(val);
                        bacValue.setText(val+"");
                    }
                }.start();
            }
        }.start();
    }
}


//    private void getBluetoothPermissions(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(
//                        MainActivity.this,
//                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
//                        PERMISSION_CODE);
//            }
//        }
//    }
