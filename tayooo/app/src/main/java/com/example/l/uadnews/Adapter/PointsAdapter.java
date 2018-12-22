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
import com.example.l.uadnews.Model.PointModel;
import com.example.l.uadnews.R;

import java.util.List;

/**
 * Created by L on 28/04/18.
 */

public class PointsAdapter extends BaseAdapter {

    //private Context context;
    private LayoutInflater layoutInflater;
    private List<PointModel> pointModels;

    public PointsAdapter(Context context, List<PointModel> pointModels) {
        layoutInflater = LayoutInflater.from(context);
        this.pointModels = pointModels;
    }

    @Override
    public int getCount() {
        return pointModels.size();
    } //menampilkan data sesuai jumlah yang ada pada database

    @Override
    public Object getItem(int i) {
        return pointModels.get(i);
    }  //mengambil list dari pointmodels (list)

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.item_riwayat_point, viewGroup, false); //membuat parameter view berisi nilai yg merujuk pada item_riwayat_point

        ((TextView) view.findViewById(R.id.pointHistory)).setText(pointModels.get(i).point); //deklarasi id pointHistory bersedia diisi oleh list dari objek point yang berasal dari model
        ((TextView) view.findViewById(R.id.periodeHistory)).setText(pointModels.get(i).periode);

        return view;
    }
}
