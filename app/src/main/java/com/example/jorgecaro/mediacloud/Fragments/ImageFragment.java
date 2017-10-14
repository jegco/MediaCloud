package com.example.jorgecaro.mediacloud.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import android.widget.TextView;

import com.example.jorgecaro.mediacloud.MainActivity;
import com.example.jorgecaro.mediacloud.R;

import java.io.File;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class ImageFragment extends Fragment {

    View view;
    Button open, capture;
    Bitmap bitmap = null;
    ImageView imageView;
    String uriCapturedPhoto;
    TextView imagePath;
    final static int REQUEST_CODE_CAMERA = 1;
    final static int REQUEST_CODE_GALLERY = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_image, container, false);
        open = (Button) view.findViewById(R.id.bt_open);
        capture = (Button) view.findViewById(R.id.bt_camera);
        imageView = (ImageView) view.findViewById(R.id.image);
        imagePath = (TextView) view.findViewById(R.id.path);
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.image);
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

    public void open(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,2);
    }

    public void capture(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File folder = new File(Environment.getExternalStorageDirectory(),"MediaCloud/Pictures");
        folder.mkdirs();
        uriCapturedPhoto = "MediaCloud photo-"+new Date().toString()+".jpg";
        File photoCaptured = new File(folder, uriCapturedPhoto);
        Uri uri = Uri.fromFile(photoCaptured);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(i, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri image = data.getData();
            String[] path = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(image, path, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(path[0]);
            String pathImage = cursor.getString(column);
            cursor.close();
            bitmap = BitmapFactory.decodeFile(pathImage);
            BitmapFactory.Options options = new BitmapFactory.Options();
            int heigth = bitmap.getHeight();
            int width = bitmap.getWidth();
            float scaleA = ((float)(width/2))/width;
            float scaleB = ((float)(heigth/2))/heigth;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleA, scaleB);
            Bitmap resizedImage = Bitmap.createBitmap(bitmap,0,0,width,heigth,matrix,true);
            imageView.setImageBitmap(resizedImage);
            imagePath.setText("Ruta: "+pathImage);
        }
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK){
            bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/MediaCloud/Pictures/"+uriCapturedPhoto);
            int heigth = bitmap.getHeight();
            int width = bitmap.getWidth();
            float scaleA = ((float)(width/2))/width;
            float scaleB = ((float)(heigth/2))/heigth;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleA, scaleB);
            Bitmap resizedImage = Bitmap.createBitmap(bitmap,0,0,width,heigth,matrix,true);
            imageView.setImageBitmap(resizedImage);
            imagePath.setText("Ruta: "+uriCapturedPhoto);
        }
    }
}
