package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.NoticeModel;
import com.example.myapplication.model.TradeModel;

import java.util.ArrayList;

public class NoticeAdapter extends ArrayAdapter<NoticeModel>
{

    private ViewHolder viewHolder = null;
    private LayoutInflater inflater = null;
    private ArrayList<TradeModel> items = null;
    private Context context = null;

    public NoticeAdapter(Context context, int textViewResourceId,
                                ArrayList<NoticeModel> arrays) {
        super(context, textViewResourceId, arrays);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public NoticeModel getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        View v = convertview;

        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.item_notice, null);
            viewHolder.TVTitle = (TextView) v.findViewById(R.id.tv_title);
            viewHolder.TVTime = (TextView) v.findViewById(R.id.tv_time);

            v.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) v.getTag();
        }


        viewHolder.TVTitle.setText(getItem(position).getTitle());
        viewHolder.TVTime.setText(getItem(position).getTime());

        return v;
    }

    class ViewHolder
    {
        public TextView TVTitle = null;
        public TextView TVTime = null;
    }

    @Override
    protected void finalize() throws Throwable
    {
        free();
        super.finalize();
    }

    private void free()
    {
        inflater = null;
        items = null;
        viewHolder = null;
        context = null;
    }
}
