package com.example.restorelogactivitylife;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    public static final String MY_PREFERENCES = "MyPrefs";
    private SwipeRefreshLayout swipeLayout;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> array = new ArrayList<>();
    private BaseAdapter listContentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.zone_list);
        String [] values =  prepareContent();
        arrayList.addAll(Arrays.asList(values));



        listContentAdapter = createAdapter(arrayList);
        listView.setAdapter(listContentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList.remove(position);
                listContentAdapter.notifyDataSetChanged();
            }
        });


        swipeLayout = findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
                if(!(savedInstanceState ==null)){
                    onRestoreInstanceState(savedInstanceState);
                }

            }
        });


    }
    @NonNull
    private BaseAdapter createAdapter(ArrayList<String> values) {
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
    }

    @NonNull
    private String[] prepareContent() {
        return getString(R.string.large_text).split("\n");
    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList(MY_PREFERENCES,arrayList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(MY_PREFERENCES)) {
           array = savedInstanceState.getStringArrayList(MY_PREFERENCES);
           listContentAdapter = createAdapter(array);
           listView.setAdapter(listContentAdapter);
        }
    }

}
