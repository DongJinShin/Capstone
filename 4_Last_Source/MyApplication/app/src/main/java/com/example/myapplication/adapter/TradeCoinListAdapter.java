package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.TradeModel;

import java.util.ArrayList;

public class TradeCoinListAdapter extends ArrayAdapter<TradeModel>
{

    private ViewHolder viewHolder = null;
    private LayoutInflater inflater = null;
    private ArrayList<TradeModel> items = null;
    private Context context = null;
    private String ID = null;

    public TradeCoinListAdapter(Context context, int textViewResourceId,
                              ArrayList<TradeModel> arrays, String ID) {
        super(context, textViewResourceId, arrays);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.ID = ID;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public TradeModel getItem(int position) {
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
            v = inflater.inflate(R.layout.item_trade_coin_list, null);
            viewHolder.TVType = (TextView) v.findViewById(R.id.tv_type);
            viewHolder.TVName = (TextView) v.findViewById(R.id.tv_email);
            viewHolder.TVAmount = (TextView) v.findViewById(R.id.tv_amount);
            viewHolder.TVBalance = (TextView) v.findViewById(R.id.tv_balance);
            viewHolder.TVTime = (TextView) v.findViewById(R.id.tv_time);

            v.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        if(getItem(position).getType() == 1)
        {
            viewHolder.TVType.setText("적립");
            viewHolder.TVName.setText("적립 매장 : " + getItem(position).getFrom());
            viewHolder.TVAmount.setText("+ " + getItem(position).getAmount());
            viewHolder.TVAmount.setTextColor(context.getResources().getColor(R.color.blue));
        }
        else if(getItem(position).getType() == 2)
        {
            viewHolder.TVType.setText("적립금 사용");
            viewHolder.TVName.setText("사용 매장 : " + getItem(position).getFrom());
            viewHolder.TVAmount.setText("- " + getItem(position).getAmount());
            viewHolder.TVAmount.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if(getItem(position).getType() == 3)
        {
            if(ID.equalsIgnoreCase(getItem(position).getFrom()))
            {
                viewHolder.TVType.setText("선물 하기");
                viewHolder.TVName.setText("선물 받은 사용자 : " + getItem(position).getDST());
                viewHolder.TVAmount.setTextColor(context.getResources().getColor(R.color.red));
                viewHolder.TVAmount.setText("- " + getItem(position).getAmount());
            }
            else
            {
                viewHolder.TVType.setText("선물 받기");
                viewHolder.TVName.setText("선물 한 사용자 : " + getItem(position).getFrom());
                viewHolder.TVAmount.setTextColor(context.getResources().getColor(R.color.blue));
                viewHolder.TVAmount.setText("+ " + getItem(position).getAmount());
            }
        }
        viewHolder.TVBalance.setText("" + getItem(position).getBalance());
        viewHolder.TVTime.setText(getItem(position).getTime());

        return v;
    }

    class ViewHolder
    {
        public TextView TVType = null;
        public TextView TVName = null;
        public TextView TVAmount = null;
        public TextView TVBalance = null;
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
