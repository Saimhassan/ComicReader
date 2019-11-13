package com.example.comicreaderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.comicreaderapp.Adapter.MySliderAdapter;
import com.example.comicreaderapp.Interface.IBannerLoadDone;
import com.example.comicreaderapp.Interface.IComicLoadDone;
import com.example.comicreaderapp.Model.Comic;
import com.example.comicreaderapp.Service.PicassoLoadingService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.sql.StatementEvent;

import dmax.dialog.SpotsDialog;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements IBannerLoadDone, IComicLoadDone {

    Slider slider;
    SwipeRefreshLayout swipeRefreshLayout;

    //Database
    DatabaseReference banners,comics;

    //Listener
    IBannerLoadDone bannerListener;
    IComicLoadDone comicListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Database
        banners = FirebaseDatabase.getInstance().getReference("Banners");
        comics = FirebaseDatabase.getInstance().getReference("Comic");
        //Initialize Listener
        bannerListener = this;
        comicListener = this;

        slider = (Slider)findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadComic();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
                loadComic();
            }
        });


    }

    private void loadComic() {
        //Show Dialog

        AlertDialog alertDialog = new SpotsDialog.Builder().setContext(this)
                .setCancelable(false)
                .setMessage("Please wait...")
                .build();
        alertDialog.show();

        comics.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Comic> comic_load = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot comicSnapShot:dataSnapshot.getChildren())
                {
                    Comic comic = comicSnapShot.getValue(Comic.class);
                    comic_load.add(comic);
                }
                comicListener.ComicLoadDoneListener(comic_load);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadBanner() {
        banners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> bannerList = new ArrayList<>();
                for (DataSnapshot bannerSnapShot:dataSnapshot.getChildren())
                {
                    String image = bannerSnapShot.getValue(String.class);
                    bannerList.add(image);

                }
                //Call Listener
                bannerListener.OnBannerLoadDoneListener(bannerList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void OnBannerLoadDoneListener(List<String> banners) {
        slider.setAdapter(new MySliderAdapter(banners));

    }

    @Override
    public void ComicLoadDoneListener(List<Comic> comicss) {

    }
}
