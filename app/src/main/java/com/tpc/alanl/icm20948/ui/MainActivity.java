package com.tpc.alanl.icm20948.ui;

import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.tpc.alanl.icm20948.R;
import com.tpc.alanl.icm20948.bluetooth.BluetoothStateListener;
import com.tpc.alanl.icm20948.device.ICM20948;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BluetoothStateListener {

    NavController navController;
    TabLayout tabLayout;
    Context context;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setSubtitle("Device not connected");

        //ICM20948.getInstance().setListenerBluetoothState(this);

        context = this;

        navController = Navigation.findNavController(this, R.id.nav_controller);
        tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                navController.popBackStack(navController.getGraph().getStartDestination(), false);
                switch (tab.getPosition()){
                    case 0: navController.popBackStack(navController.getGraph().getStartDestination(), false); break;
                    case 1: navController.navigate(R.id.fragment_data_tab); break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, BluetoothScanDevice.class));
            }
        });

        //ICM20948.getInstance().setListenerBluetoothState(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //ICM20948.getInstance().getBluetoothLowEnergy().di
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBluetoothStateChange(int bluetoothState) {
        if(bluetoothState == BluetoothProfile.STATE_CONNECTED){
            try{
                toolbar.setSubtitle("Connected");
                //toolbar.getMenu().getItem(1).setIcon(R.drawable.baseline_bluetooth_connected_24);
            }
            catch (Exception e){
                Log.d("onBluetoothStateChange", e.toString());
            }
        }else if(bluetoothState == BluetoothProfile.STATE_CONNECTING){
            try {
                toolbar.setSubtitle("Connecting ...");
                //toolbar.getMenu().getItem(1).setIcon(R.drawable.baseline_bluetooth_searching_24);
            }catch(Exception e){

            }
        }else if(bluetoothState == BluetoothProfile.STATE_DISCONNECTED){
            try{
                toolbar.setSubtitle("Disconnected");
                //toolbar.getMenu().getItem(1).setIcon(R.drawable.baseline_bluetooth_disabled_24);
            }catch(Exception e){

            }
        }else if(bluetoothState == BluetoothProfile.STATE_DISCONNECTING){
            try{
                toolbar.setSubtitle("Disconnecting ...");
                //toolbar.getMenu().getItem(1).setIcon(R.drawable.baseline_bluetooth_disabled_24);
            }catch(Exception e){

            }
        }
    }
}
