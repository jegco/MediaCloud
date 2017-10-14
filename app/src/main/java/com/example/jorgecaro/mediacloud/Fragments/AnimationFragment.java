package com.example.jorgecaro.mediacloud.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.jorgecaro.mediacloud.MainActivity;
import com.example.jorgecaro.mediacloud.R;


public class AnimationFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animation, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.animations);
        final ImageView star = (ImageView) view.findViewById(R.id.animated_image);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.star_animation);
                star.startAnimation(animation);
            }
        });
        return view;
    }
}
