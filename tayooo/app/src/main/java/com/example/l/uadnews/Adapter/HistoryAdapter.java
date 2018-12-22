package com.example.l.uadnews.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.l.uadnews.Model.HistoryModel;
import com.example.l.uadnews.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by L on 28/04/18.
 */

public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<HistoryModel> historyModels;

    public HistoryAdapter(Context context, List<HistoryModel> historyModels){
        layoutInflater = LayoutInflater.from(context);
        this.historyModels = historyModels;
    }

    @Override
    public int getCount() {
        return historyModels.size();
    }

    @Override
    public Object getItem(int i) {
        return historyModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.item_history,viewGroup,false);

        if(historyModels.get(i).keterangan.equals("Diterima")){
            ((LinearLayout)view.findViewById(R.id.atas)).setBackgroundColor(Color.parseColor("#7FFF00"));
            //((LinearLayout)view.findViewById(R.id.bawah)).setBackgroundColor(Color.parseColor("#7FFF00"));
        }else if(historyModels.get(i).keterangan.equals("Menunggu")){
            ((LinearLayout)view.findViewById(R.id.atas)).setBackgroundColor(Color.parseColor("#FFFF00"));
            //((LinearLayout)view.findViewById(R.id.bawah)).setBackgroundColor(Color.parseColor("#FFFF00"));
        }
        ((TextView)view.findViewById(R.id.judul)).setText(historyModels.get(i).judul);
        ((TextView)view.findViewById(R.id.tanggal)).setText(historyModels.get(i).tanggal);
        ((TextView)view.findViewById(R.id.keterangan)).setText(historyModels.get(i).keterangan);
        if(historyModels.get(i).keterangan.equals("Diterima")){
            ((TextView)view.findViewById(R.id.point)).append("1");
        }else ((TextView)view.findViewById(R.id.point)).append("0");
        return view;
    }
}
