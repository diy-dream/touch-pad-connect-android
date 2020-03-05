package com.tpc.alanl.icm20948.device;

import android.bluetooth.BluetoothDevice;

import com.tpc.alanl.icm20948.bluetooth.BluetoothLowEnergy;
import com.tpc.alanl.icm20948.bluetooth.BluetoothStateListener;

public class ICM20948 {
    private static final ICM20948 ourInstance = new ICM20948();

    private BluetoothLowEnergy bluetoothLowEnergy;
    private BluetoothDevice bluetoothDevice;
    private float psi, theta, phi;
    private int gestureState;
    private ICM20948Listener listenerDataUpdate;
    private BluetoothStateListener listenerBluetoothState;

    public static ICM20948 getInstance() {
        return ourInstance;
    }

    private ICM20948() {
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public BluetoothLowEnergy getBluetoothLowEnergy() {
        return bluetoothLowEnergy;
    }

    public void setBluetoothLowEnergy(BluetoothLowEnergy bluetoothLowEnergy) {
        this.bluetoothLowEnergy = bluetoothLowEnergy;
    }

    public float getPsi() {
        return psi;
    }

    public void setPsi(float psi) {
        this.psi = psi;
    }

    public float getTheta() {
        return theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public float getPhi() {
        return phi;
    }

    public void setPhi(float phi) {
        this.phi = phi;
    }

    public ICM20948Listener getListenerDataUpdate() {
        return listenerDataUpdate;
    }

    public void setListenerDataUpdate(ICM20948Listener listenerDataUpdate) {
        this.listenerDataUpdate = listenerDataUpdate;
    }

    public int getGestureState() {
        return gestureState;
    }

    public void setGestureState(int gestureState) {
        this.gestureState = gestureState;
    }

    public BluetoothStateListener getListenerBluetoothState() {
        return listenerBluetoothState;
    }

    public void setListenerBluetoothState(BluetoothStateListener listenerBluetoothState) {
        this.listenerBluetoothState = listenerBluetoothState;
    }
}
