package com.example.breathalyzer;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
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
        TextView timeLeft = findViewById(R.id.timeLeft);
        getBluetoothPermissions();
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
        getBluetoothPermissions();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.d(TAG, "No bluetooth");
            Toast.makeText(getApplicationContext(), "Phone does not have bluetooth", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(TAG, "Has bluetooth");
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            }
            else{
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address
                        if (deviceName.equals("Project Zero")) {
                            Log.d(TAG, "hit");
                            connect(device);
                            break;
                        }
                    }
                }
            }
        }
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

    private void connect(BluetoothDevice device) {

    }
    private void requestPermission(String[] permission, int code) {
        ActivityCompat.requestPermissions(
                MainActivity.this,
                permission,
                code);
        Log.d(TAG, "Done");
    }


    private void getBluetoothPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Needs the stuff");
                int i = 0;
                String[] permissions = {Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
                for (String access : permissions) {
                    requestPermission(new String[] {access}, i);
                    i++;
                }
//                ActivityCompat.requestPermissions(
//                        MainActivity.this,
//                        permissions,
//                        PERMISSION_CODE);
                Log.d(TAG, "Done");
            }
            else{
                Log.d(TAG, "Gave permission");
            }
        }
        else{
            Log.d(TAG, "sdk not high enough");
        }
    }

    // This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        Log.d(TAG, "result");
    }
}