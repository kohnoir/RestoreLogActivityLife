package com.example.restorelogactivitylife;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    SharedPreferences sharedPreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    SwipeRefreshLayout swipeLayout;
    ArrayList<Integer>  intMap = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    BaseAdapter listContentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.zone_list);
        initSharedPreferences();
        String [] values =  prepareContent();
        arrayList.addAll(Arrays.asList(values));



        listContentAdapter = createAdapter(arrayList);
        listView.setAdapter(listContentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intMap.add(position);
                arrayList.remove(position);
                listContentAdapter.notifyDataSetChanged();
            }
        });
        swipeLayout = findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
                String [] value =  prepareContent();
                arrayList.addAll(Arrays.asList(value));
                listContentAdapter.notifyDataSetChanged();
            }
        });
        if(!(savedInstanceState ==null)){
            ArrayList<Integer> list = savedInstanceState.getIntegerArrayList(MY_PREFERENCES);
            for (int i = 0; i < list.size();i++){
                arrayList.remove(i);
            }
        }

    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        for (int i = 0; i < prepareContent().length; i++) {
            String string = String.valueOf(prepareContent().length);
            sharedPreferences.edit()
                    .putString(string, prepareContent()[i]);
        }
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
        savedInstanceState.putIntegerArrayList(MY_PREFERENCES,intMap);
    }
}
