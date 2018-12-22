package com.example.l.uadnews.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.l.uadnews.Model.PeringkatModel;
import com.example.l.uadnews.R;

import java.util.List;

/**
 * Created by L on 28/04/18.
 */

public class PeringkatAdapter extends BaseAdapter {

    private List<PeringkatModel> models;
    private LayoutInflater layoutInflater;

    public PeringkatAdapter(Context context,List<PeringkatModel> models){
        layoutInflater = LayoutInflater.from(context);
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
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
        view = layoutInflater.inflate(R.layout.item_peringkat,viewGroup,false);

        ((TextView)view.findViewById(R.id.nama)).setText(models.get(i).nama_user);
        ((TextView)view.findViewById(R.id.point)).setText(models.get(i).banyak);
        ((TextView)view.findViewById(R.id.email)).setText(models.get(i).email);
        ((TextView)view.findViewById(R.id.peringkat)).setText(String.valueOf(i+1));



        return view;
    }
}
