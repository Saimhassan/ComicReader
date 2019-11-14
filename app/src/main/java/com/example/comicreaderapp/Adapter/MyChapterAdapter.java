package com.example.comicreaderapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicreaderapp.Common.Common;
import com.example.comicreaderapp.Interface.IRecyclerItemClickListener;
import com.example.comicreaderapp.Model.Chapter;
import com.example.comicreaderapp.R;
import com.example.comicreaderapp.ViewComicActivity;

import java.util.List;

public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.MyViewHolder> {

    Context context;
    List<Chapter> chapterList;
    LayoutInflater inflater;

    public MyChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.chapter_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.txt_chapter_numb.setText(chapterList.get(position).Name);
      holder.setRecyclerItemClickListener(new IRecyclerItemClickListener() {
          @Override
          public void onClick(View view, int position) {
              Common.chapterSelected = chapterList.get(position);
              Common.chapterIndex = position;
              context.startActivity(new Intent(context, ViewComicActivity.class));
          }
      });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_chapter_numb;
        IRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_chapter_numb = (TextView)itemView.findViewById(R.id.txt_chapters_numb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerItemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
