package com.example.jorgecaro.mediacloud.Fragments;

import android.content.Context;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.jorgecaro.mediacloud.Graphics.CubeRenderer;
import com.example.jorgecaro.mediacloud.MainActivity;
import com.example.jorgecaro.mediacloud.R;


public class GraphicFragment extends Fragment {

    private GLSurfaceView canvas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graphic, container, false);
        canvas = new GLSurfaceView(getActivity());
        canvas.setRenderer(new CubeRenderer(getContext()));
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.graphics);
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.graphic_container);
        layout.addView(canvas);
        return view;
    }
}
