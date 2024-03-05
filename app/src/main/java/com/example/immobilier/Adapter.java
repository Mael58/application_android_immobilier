package com.example.immobilier;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Adapter extends BaseAdapter {

    Context context;
    private List<Bien> appts;
   private HashMap<String, List<String>> idList = new HashMap<>();

    private static LayoutInflater inflater;



    void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Adapter(Context context, List<Bien> appts) {
        this.context = context;
        this.appts = appts;
        init();
    }

    public void setAppts(List<Bien> appts) {
        this.appts = appts;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return appts.size();
    }

    @Override
    public Object getItem(int i) {
        return appts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public HashMap<String, List<String>> getIdList(){
        return this.idList;
    }

    static class Holder {
        TextView titleTV;
        TextView priceTV;
        TextView surfaceTV;
        ImageView ImageTv;

        CheckBox checkList;

        public void setTitleTV(TextView titleTV) {
            this.titleTV = titleTV;
        }

        public void setPriceTV(TextView priceTV) {
            this.priceTV = priceTV;
        }

        public void setSurfaceTV(TextView surfaceTV) {
            this.surfaceTV = surfaceTV;
        }
        public void setImageTv(ImageView imageTv) {
            ImageTv = imageTv;
        }

        public TextView getPriceTV() {
            return priceTV;
        }

        public TextView getTitleTV() {
            return titleTV;
        }

        public TextView getSurfaceTV() {
            return surfaceTV;
        }

        public ImageView getImageTv() {
            return ImageTv;
        }

        public CheckBox getCheckList() {
            return checkList;
        }

        public void setCheckList(CheckBox checkList) {
            this.checkList = checkList;
        }
    }

    private Holder initHolder(View v){
        Holder holder = new Holder();

        holder.setTitleTV(v.findViewById(R.id.title));
        holder.setSurfaceTV(v.findViewById(R.id.surface));
        holder.setPriceTV(v.findViewById(R.id.price));
        holder.setImageTv(v.findViewById(R.id.imageLoca));
    holder.setCheckList(v.findViewById(R.id.checkList));

        return holder;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View cv = convertView;

        if (cv == null) {
            cv = inflater.inflate(R.layout.list_appart, parent, false);
        }
        Holder holder = initHolder(cv);
        //holder.getPriceTV().setText(films[position].editor);
        holder.getPriceTV().setText(appts.get(i).getPrix()+" €");
        //holder.getTitleTV().setText(films[position].title);
        holder.getTitleTV().setText(appts.get(i).getTitle());
        //holder.getSurfaceTV().setText(films[position].date);
        holder.getSurfaceTV().setText(appts.get(i).getSurface()+" m²");
        holder.getCheckList().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    idList.computeIfAbsent(appts.get(i).getTypeBien(), k -> new ArrayList<>());
                    idList.get(appts.get(i).getTypeBien()).add(appts.get(i).getId());


                }else{
                    List<String> ids = idList.get(appts.get(i).getTypeBien()).stream().filter(id -> !id.equals(appts.get(i).getId())).collect(Collectors.toList());
                    idList.get(appts.get(i).getTypeBien()).clear();
                    idList.get(appts.get(i).getTypeBien()).addAll(ids);

                }



            }
        });
        try{
            Picasso.get().load(appts.get(i).getUrl().get(0)).into(holder.ImageTv);
        }catch (Exception e){

        }


        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) context;
                if (mainActivity.admin_mode) {
                    mainActivity.addPopUp(appts.get(i));
                }
                else {
                    Intent intent = new Intent(context, Info_apprt.class);
                    intent.putExtra("appt", (Serializable) appts.get(i));
                    context.startActivity(intent);
                }

            }
        });
        return cv;
    }
}
