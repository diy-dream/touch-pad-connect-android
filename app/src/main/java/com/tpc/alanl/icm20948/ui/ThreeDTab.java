package com.tpc.alanl.icm20948.ui;


import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tpc.alanl.icm20948.OpenGLRenderer;
import com.tpc.alanl.icm20948.R;
import com.tpc.alanl.icm20948.device.ICM20948;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeDTab extends Fragment {


    public ThreeDTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_three_dtab, container, false);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        GLSurfaceView view3D = new GLSurfaceView(getActivity());
        view3D.setRenderer(new OpenGLRenderer(getActivity()));
        //setContentView(view3D);

        //ICM20948.getInstance().setListnerDataChanged(this);

        return view3D;
    }

}
