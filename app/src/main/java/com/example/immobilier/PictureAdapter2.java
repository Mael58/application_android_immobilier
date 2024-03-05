package com.example.immobilier;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PictureAdapter2 extends RecyclerView.Adapter<PictureAdapter2.MyViewHolder>{
    private List<String> url;
    static class MyViewHolder extends RecyclerView.ViewHolder {
         ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.picture);

        }


    }

    public PictureAdapter2(List<String> url) {
        this.url = url;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picture, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String urlPicture = url.get(position);
        Picasso.get().load(urlPicture).into(holder.imageView);

    }
    @Override
    public int getItemCount() {
        return url.size();
    }
}
