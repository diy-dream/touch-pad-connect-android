package com.tpc.alanl.icm20948.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tpc.alanl.icm20948.device.ConstantICM20948;
import com.tpc.alanl.icm20948.device.ICM20948;

import java.util.ArrayList;
import java.util.List;

public class BluetoothLowEnergy {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private List<BluetoothGattService> services;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public BluetoothLeScanner bluetoothLeScanner;
    public BluetoothGattCharacteristic bluetoothGattCharacteristic;
    private Context context;
    private ScanCallback scanCallback;

    public List<BluetoothDevice> lBluetoothDevice = new ArrayList<BluetoothDevice>();

    public BluetoothLowEnergy(Context context, Activity activity){
        // Initializes Bluetooth adapter
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        this.context = context;

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        Log.d("BluetoothLowEnergy", "Construtor BluetoothLowEnergy");
    }

    public void startScan(){
        bluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        bluetoothLeScanner.startScan(this.getScanCallback());
    }

    public void stopScan() {
        bluetoothLeScanner.stopScan(scanCallback);
    }

    private final BluetoothGattCallback btleGattCallback = new BluetoothGattCallback() {

        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            // this will get called when a device connects or disconnects
            if(newState == BluetoothProfile.STATE_CONNECTED){
                //ICM20948.getInstance().getListenerBluetoothState().onBluetoothStateChange(BluetoothProfile.STATE_CONNECTED);
                Log.d("BluetoothLowEnergy", "BluetoothProfile.STATE_CONNECTED");
                bluetoothLeScanner.stopScan(scanCallback);
                bluetoothGatt.discoverServices();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "STATE_CONNECTED", Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(newState == BluetoothProfile.STATE_CONNECTING){
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "STATE_CONNECTING", Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                Log.d("BluetoothLowEnergy", "BluetoothProfile.STATE_DISCONNECTED");
                //ICM20948.getInstance().getListenerBluetoothState().onBluetoothStateChange(BluetoothProfile.STATE_DISCONNECTED);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "STATE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(newState == BluetoothProfile.STATE_DISCONNECTING){
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "STATE_DISCONNECTING", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            services = bluetoothGatt.getServices();
            Log.d("BluetoothLowEnergy", "BluetoothProfile.STATE_DISCONNECTING");
            gatt.setCharacteristicNotification(gatt.getService(ConstantICM20948.SERVICE_ICM20948).getCharacteristic(ConstantICM20948.CHARACTERISTIC_ICM20948_RAW_DATA), true);
            BluetoothGattDescriptor descriptor = gatt.getService(ConstantICM20948.SERVICE_ICM20948).getCharacteristic(ConstantICM20948.CHARACTERISTIC_ICM20948_RAW_DATA).getDescriptors().get(0);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(descriptor);
            gatt.readCharacteristic(gatt.getService(ConstantICM20948.SERVICE_ICM20948).getCharacteristic(ConstantICM20948.CHARACTERISTIC_ICM20948_RAW_DATA));
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            bluetoothGattCharacteristic = characteristic;
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            //Data from ICM20948
            ICM20948.getInstance().setPsi(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, 0));
            ICM20948.getInstance().setTheta(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, 2));
            ICM20948.getInstance().setPhi(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, 4));
            ICM20948.getInstance().setGestureState(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, 6));

            ICM20948.getInstance().getListenerDataUpdate().ICM20948DataUpdate();
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };

    public void connection(BluetoothDevice bluetoothDevice){
        bluetoothGatt = bluetoothDevice.connectGatt(context , true, btleGattCallback); // Connection Device
    }

    private boolean enable(BluetoothAdapter m){
        return true;
    }

    public List<BluetoothDevice> getlBluetoothDevice() {
        return lBluetoothDevice;
    }

    public void setlBluetoothDevice(List<BluetoothDevice> lBluetoothDevice) {
        this.lBluetoothDevice = lBluetoothDevice;
    }

    public List<BluetoothGattService> getServices() {
        return services;
    }


    public BluetoothGatt getBluetoothGatt() {
        return bluetoothGatt;
    }

    public void setScanCallback(ScanCallback scanCallback) {
        this.scanCallback = scanCallback;
    }

    public ScanCallback getScanCallback() {
        return this.scanCallback;
    }
}