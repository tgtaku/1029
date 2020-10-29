package com.example.pdfview;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c){
        mContext = c;
    }

    public int getCount(){
        //return mThumbIds.length;
        return selectPic.bitmapArrayList.size();
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    //Adapterから参照される新しImageViewを作成
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        //CheckBox checkBox;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(15,20,15,20);
        }else{
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(selectPic.bitmapArrayList.get(position));
        return imageView;
    }

}
