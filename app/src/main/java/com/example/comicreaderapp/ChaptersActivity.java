package com.example.comicreaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.comicreaderapp.Adapter.MyChapterAdapter;
import com.example.comicreaderapp.Common.Common;
import com.example.comicreaderapp.Model.Comic;

public class ChaptersActivity extends AppCompatActivity {

    RecyclerView recycler_chapters;
    TextView txt_chapter_name;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        //View
        txt_chapter_name = (TextView)findViewById(R.id.txt_chapter_name);
        recycler_chapters = (RecyclerView)findViewById(R.id.recycler_chapter);
        linearLayoutManager =new LinearLayoutManager(this);
        recycler_chapters.setHasFixedSize(true);
        recycler_chapters.setLayoutManager(linearLayoutManager);
        recycler_chapters.addItemDecoration(new DividerItemDecoration(this,linearLayoutManager.getOrientation()));

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Common.comicSelected.Name);

        //Set ICON
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fetchChapter(Common.comicSelected);

    }

    private void fetchChapter(Comic comicSelected) {
        Common.chapterList = comicSelected.Chapters;
        recycler_chapters.setAdapter(new MyChapterAdapter(this,comicSelected.Chapters));
        txt_chapter_name.setText(new StringBuilder("CHAPTERS (").append(comicSelected.Chapters.size())
        .append(")"));
    }
}
