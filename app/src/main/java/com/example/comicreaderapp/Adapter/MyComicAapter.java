package com.example.comicreaderapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicreaderapp.ChaptersActivity;
import com.example.comicreaderapp.Common.Common;
import com.example.comicreaderapp.Interface.IRecyclerItemClickListener;
import com.example.comicreaderapp.Model.Comic;
import com.example.comicreaderapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyComicAapter extends RecyclerView.Adapter<MyComicAapter.MyViewHolder> {

    Context context;
    List<Comic> comicList;
    LayoutInflater inflater;

    public MyComicAapter(Context context, List<Comic> comicList) {
        this.context = context;
        this.comicList = comicList;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.comic_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(comicList.get(position).Image).into(holder.comic_image);
        holder.comic_name.setText(comicList.get(position).Name);
        //Event
        holder.setRecyclerItemClickListener(new IRecyclerItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Save Comic Selected
                Common.comicSelected = comicList.get(position);
                context.startActivity(new Intent(context, ChaptersActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView comic_name;
        ImageView comic_image;
        IRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comic_image = (ImageView)itemView.findViewById(R.id.image_comic);
            comic_name = (TextView)itemView.findViewById(R.id.comic_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerItemClickListener.onClick(itemView,getAdapterPosition());
        }
    }
}
