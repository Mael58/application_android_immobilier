package com.example.immobilier;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PictureAdapter extends BaseAdapter {

    Context context;
    List<String> url;

    private static LayoutInflater inflater;


    void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public PictureAdapter(Context context, List<String> url) {
        this.context = context;
        this.url = url;
        init();
    }

    public void setAppts(List<String> url) {
        this.url = url;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return url.size();
    }

    @Override
    public Object getItem(int i) {
        return url.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class Holder {
       private ImageView imageView;

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }

    private Holder initHolder(View v){
        Holder holder = new Holder();

      holder.setImageView(v.findViewById(R.id.picture));

        return holder;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View cv = convertView;

        if (cv == null) {
            cv = inflater.inflate(R.layout.picture, parent, false);
        }
        Holder holder = initHolder(cv);
        //holder.getPriceTV().setText(films[position].editor);
        Picasso.get().load(url.get(i)).into(holder.getImageView());




        return cv;
    }
}
