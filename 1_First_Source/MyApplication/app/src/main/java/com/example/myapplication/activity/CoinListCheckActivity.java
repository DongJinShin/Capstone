package com.example.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.item.CoinListItem;
import com.example.myapplication.adapter.ListViewBaseAdapter;

public class CoinListCheckActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_list);

        ListView listview ;
        ListViewBaseAdapter adapter;

        // Adapter 생성
        adapter = new ListViewBaseAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.

        CoinListItem item1 = new CoinListItem();
        item1.setCoin(1080);
        item1.setTime("2017. 03. 01 14:30:23");
        item1.setSave(500);
        item1.setStore("맘스터치");

        CoinListItem item2 = new CoinListItem();
        item2.setCoin(1480);
        item2.setTime("2017. 03. 02 15:33:21");
        item2.setSave(400);
        item2.setStore("롯데리아");

        CoinListItem item3 = new CoinListItem();
        item3.setCoin(2080);
        item3.setTime("2017. 03. 12 18:03:21");
        item3.setSave(600);
        item3.setStore("맥도날드");

        adapter.addItem(item1) ;
        adapter.addItem(item2) ;
        adapter.addItem(item3) ;
    }
}
