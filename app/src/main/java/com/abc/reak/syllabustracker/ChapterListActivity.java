package com.abc.reak.syllabustracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ChapterListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChapterAdapter adapter;
    List<Chapter> list;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        databaseHelper = new DatabaseHelper(this);
        String subject = getIntent().getStringExtra("subject");

        //list = databaseHelper.selectAll();
        list = databaseHelper.selectSubjectWise(subject);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ChapterAdapter(this, list, databaseHelper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}