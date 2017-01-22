package com.tutorials.hp.filepickerrecyclerview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.tutorials.hp.filepickerrecyclerview.mRecycler.MyAdapter;

import java.io.File;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<String> filePaths=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filePaths.clear();
                FilePickerBuilder.getInstance().setMaxCount(5)
                        .setSelectedFiles(filePaths)
                        .setActivityTheme(R.style.AppTheme)
                        .pickPhoto(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE:
                if(resultCode==RESULT_OK && data!=null)
                {
                    filePaths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS);
                    Spacecraft s;
                    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();

                    try
                    {
                        for (String path:filePaths) {
                            s=new Spacecraft();
                            s.setName(path.substring(path.lastIndexOf("/")+1));

                            s.setUri(Uri.fromFile(new File(path)));
                            spacecrafts.add(s);
                        }

                        rv.setAdapter(new MyAdapter(this,spacecrafts));
                        Toast.makeText(MainActivity.this, "Total = "+String.valueOf(spacecrafts.size()), Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }



                }
        }
    }

}
