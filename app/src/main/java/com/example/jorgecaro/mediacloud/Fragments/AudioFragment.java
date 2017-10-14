package com.example.jorgecaro.mediacloud.Fragments;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorgecaro.mediacloud.MainActivity;
import com.example.jorgecaro.mediacloud.R;

import java.io.File;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class AudioFragment extends Fragment {
    static final int PICK_SONG = 1;
    boolean playing = true, recording = false;
    View view;
    TextView path;
    Button recorde, play, open;
    MediaPlayer audio;
    MediaRecorder recorder;
    String patch, uriRecordedAudio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_audio, container, false);
        open = (Button) view.findViewById(R.id.bt_open);
        play = (Button) view.findViewById(R.id.bt_play);
        recorde = (Button) view.findViewById(R.id.bt_recorde);
        path = (TextView) view.findViewById(R.id.path);
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.audio);
        audio = new MediaPlayer();
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("audio/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Selecciona un audio"), PICK_SONG);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playing == false){
                    play(patch);
                    playing = true;
                    return;
                }if(playing){
                    stop();
                    playing = false;
                    return;
                }
            }
        });

        recorde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recording == false){
                    startRecording();
                    recorde.setText(R.string.stop_recording);
                    recording = true;
                    return;
                }if(recording){
                    stopRecording();
                    recorde.setText(R.string.recorde);
                    recording = false;
                    play(uriRecordedAudio);
                    return;
                }
            }
        });

        return view;
    }

    public void startRecording(){
        recorder = new MediaRecorder();
        uriRecordedAudio = Environment.getExternalStorageDirectory()+"/MediaCloud/Audio/recorded-"+new Date().toString()+".3gp";
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(uriRecordedAudio);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try{
            recorder.prepare();
            recorder.start();
            recorde.setText(R.string.stop_recording);
        }catch (Exception e){
            Toast.makeText(getContext(), "Error en la grabación", Toast.LENGTH_SHORT).show();
            Log.i("error", e.toString());
        }
    }

    public void stopRecording(){
        recorder.stop();
        recorder.release();
        recorder = null;
        recorde.setText(R.string.recorde);
        path.setText(uriRecordedAudio);
        Toast.makeText(getContext(),"El audio se guardo en: "+uriRecordedAudio, Toast.LENGTH_SHORT);
    }

    public void play(String patch){
        try{
            audio = new MediaPlayer();
            audio.setDataSource(getContext(), Uri.parse(patch));
            audio.prepare();
            audio.start();
            play.setText(R.string.stop_audio);
        }catch (Exception e){
            Toast.makeText(getContext(),"Error al reproducir el audio", Toast.LENGTH_SHORT);

        }
    }

    public void stop(){
        audio.stop();
        play.setText(R.string.play_audio);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_SONG:
                if (resultCode == RESULT_OK){
                    patch = data.getDataString();
                    path.setText(patch);
                    play(patch);
                }
        }
    }
}
