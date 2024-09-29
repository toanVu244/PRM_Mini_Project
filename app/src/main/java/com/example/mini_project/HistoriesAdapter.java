package com.example.mini_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HistoriesAdapter extends BaseAdapter {

  private   List<History> list;
   private Context context;
private int layout;

    public HistoriesAdapter(List<History> list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView textViewBetting = (TextView) view.findViewById(R.id.textViewBetting);
        TextView textViewWinner = (TextView) view.findViewById(R.id.textViewWinner);
        TextView textViewGet = (TextView) view.findViewById(R.id.textViewGet);
        History his = list.get(i);
        textViewBetting.setText(String.valueOf(his.getMoneyBetting()));
        textViewWinner.setText(String.valueOf(his.getWiner()));
        textViewGet.setText(String.valueOf(his.getMoneyGet()));
        return view;
    }
}
