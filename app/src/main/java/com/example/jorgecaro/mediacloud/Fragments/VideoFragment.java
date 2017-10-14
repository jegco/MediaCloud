package com.example.jorgecaro.mediacloud.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.jorgecaro.mediacloud.MainActivity;
import com.example.jorgecaro.mediacloud.R;

import java.io.File;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class VideoFragment extends Fragment {

    View view;
    Button open, capture;
    Bitmap bitmap = null;
    VideoView videoView;
    String uriCapturedVideo;
    String path;
    TextView imagePath;
    final static int REQUEST_CODE_CAMERA = 1;
    final static int REQUEST_CODE_GALLERY = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_video, container, false);
        open = (Button) view.findViewById(R.id.bt_open);
        capture = (Button) view.findViewById(R.id.bt_camera);
        videoView = (VideoView) view.findViewById(R.id.video);
        imagePath = (TextView) view.findViewById(R.id.path);
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.video);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });

        return view;
    }

    public void capture(){
        Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File folder = new File(Environment.getExternalStorageDirectory(),"MediaCloud/Videos");
        folder.mkdirs();
        uriCapturedVideo = "MediaCloud video-"+new Date().toString()+".3gp";
        File video = new File(folder,uriCapturedVideo);
        Uri uri = Uri.fromFile(video);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(i, REQUEST_CODE_CAMERA);

    }

    public void open(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,2);
    }


    public void play(Uri path){
        videoView.setVideoURI(path);
        videoView.start();
        MediaController controller = new MediaController(getActivity());
        controller.setAnchorView(videoView);
        videoView.setMediaController(controller);
        videoView.getHolder().setFixedSize(videoView.getWidth(), videoView.getHeight());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK){
            path = Environment.getExternalStorageDirectory()+"/MediaCloud/Videos/"+uriCapturedVideo;
            play(Uri.parse(path));
            imagePath.setText(path);
        }if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            play(data.getData());
            imagePath.setText(data.getDataString());
        }
    }
}
