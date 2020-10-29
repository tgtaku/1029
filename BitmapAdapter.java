package com.example.pdfview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

public class BitmapAdapter extends ArrayAdapter<Bitmap> {
    public int resourceId;

    public BitmapAdapter(Context context, int resource, List<Bitmap> objects){
        super(context, resource, objects);
        resourceId = resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public  View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Objects.requireNonNull(inflater).inflate(resourceId, null);
        }

        ImageView view = (ImageView) convertView;
        view.setImageBitmap(getItem(position));

        return view;
    }
}
