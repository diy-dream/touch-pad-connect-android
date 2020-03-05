package com.tpc.alanl.icm20948.bluetooth;

import android.bluetooth.BluetoothProfile;

public interface BluetoothStateListener {
    void onBluetoothStateChange(int bluetoothState);
}
