package com.tpc.alanl.icm20948.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tpc.alanl.icm20948.R;
import com.tpc.alanl.icm20948.bluetooth.BluetoothLowEnergy;
import com.tpc.alanl.icm20948.device.ICM20948;

import java.util.ArrayList;
import java.util.List;

public class BluetoothScanDevice extends AppCompatActivity {

    private static final long SCAN_PERIOD = 5000;

    BluetoothLowEnergy bluetoothLowEnergy;
    ListView bluetoothListView;
    ArrayAdapter<String> adapter;
    List<BluetoothDevice> listBluetoothDevice = new ArrayList<>();
    List<String> listStringBluetoothDevice = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_scan_device);

        statusCheck();

        bluetoothListView = (ListView) findViewById(R.id.bluetooth_list_view);
        context = this;

        bluetoothListView = (ListView)findViewById(R.id.bluetooth_list_view);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listStringBluetoothDevice);

        bluetoothListView.setAdapter(adapter);

        bluetoothListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bluetoothLowEnergy.connection(listBluetoothDevice.get(position));
                ICM20948.getInstance().setBluetoothDevice(listBluetoothDevice.get(position));
                startActivity(new Intent(context, MainActivity.class));
            }
        });


        bluetoothLowEnergy = new BluetoothLowEnergy(this, this);
        bluetoothLowEnergy.setScanCallback(scanCallback);

        ICM20948.getInstance().setBluetoothLowEnergy(bluetoothLowEnergy);

        bluetoothLowEnergy.startScan();

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bluetoothLowEnergy.startScan();
                adapter.clear();
                adapter.notifyDataSetChanged();
                listBluetoothDevice.clear();
                listStringBluetoothDevice.clear();
                pullToRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bluetoothLowEnergy.stopScan();
                        pullToRefresh.setRefreshing(false);
                    }
                },SCAN_PERIOD);
            }
        });
    }

    public ScanCallback scanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            if(!listBluetoothDevice.contains(result.getDevice()) && result.getDevice().getName() != null) {
                listBluetoothDevice.add(result.getDevice());
                listStringBluetoothDevice.add(result.getDevice().getName());
                adapter.notifyDataSetChanged();
            }
        }
    };

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
