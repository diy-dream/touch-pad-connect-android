package com.tpc.alanl.icm20948.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tpc.alanl.icm20948.R;
import com.tpc.alanl.icm20948.device.ICM20948;
import com.tpc.alanl.icm20948.device.ICM20948Listener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataTab extends Fragment implements ICM20948Listener {

    TextView textViewPsi, textViewTheta, textViewPhi, gestureState;
    ImageView imageViewGestureState;

    public DataTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_tab, container, false);

        ICM20948.getInstance().setListenerDataUpdate(this);

        textViewPsi = view.findViewById(R.id.textView_psi);
        textViewTheta = view.findViewById(R.id.textView_theta);
        textViewPhi = view.findViewById(R.id.textView_phi);

        gestureState = view.findViewById(R.id.textViewGestureState);
        imageViewGestureState = view.findViewById(R.id.imageViewGestureState);

        return view;
    }

    @Override
    public void ICM20948DataUpdate() {
        textViewPsi.setText("PSI = " + ICM20948.getInstance().getPsi());
        textViewTheta.setText("THETA = " + ICM20948.getInstance().getTheta());
        textViewPhi.setText("PHI = " + ICM20948.getInstance().getPhi());

        String str;
        int ressource;

        switch(ICM20948.getInstance().getGestureState()){
            case 0x10:
                str = "Single Click";
                ressource = R.drawable.icon_finger_click;
                break;
            case 0x11:
                str = "Click and Hold";
                ressource = R.drawable.icon_finger_click;
                break;
            case 0x20:
                str = "Double Click";
                ressource = R.drawable.icon_finger_click;
                break;
            case 0x31:
                str = "Down Swipe";
                ressource = R.drawable.icon_finger_swipe_down;
                break;
            case 0x32:
                str = "Down Swipe and Hold";
                ressource = R.drawable.icon_finger_swipe_down;
                break;
            case 0x41:
                str = "Right Swipe";
                ressource = R.drawable.icon_finger_swipe_right;
                break;
            case 0x42:
                str = "Right Swipe and Hold";
                ressource = R.drawable.icon_finger_swipe_right;
                break;
            case 0x51:
                str = "Up Swipe";
                ressource = R.drawable.icon_finger_swipe_up;
                break;
            case 0x52:
                str = "Up Swipe and Hold";
                ressource = R.drawable.icon_finger_swipe_up;
                break;
            case 0x61:
                str = "Left Swipe";
                ressource = R.drawable.icon_finger_swipe_left;
                break;
            case 0x62:
                str = "Left Swipe and Hold";
                ressource = R.drawable.icon_finger_swipe_left;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ICM20948.getInstance().getGestureState());
        }
        gestureState.setText("Gesture = " + str);
        imageViewGestureState.setImageResource(ressource);
    }
}
