package com.example.l.uadnews.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.l.uadnews.Model.Point
import com.example.l.uadnews.Model.PointModel
import com.example.l.uadnews.R

/**
 * Created by L on 25/05/18.
 */
class PointAdapter : BaseAdapter {

    private var namaBulan = arrayOf("","Januari",
            "Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember")

    private lateinit var context:Context
    private lateinit var inflater:LayoutInflater
    private lateinit var data:MutableList<PointModel>

    constructor(context: Context,data:MutableList<PointModel>){
        this.inflater = LayoutInflater.from(context)
        this.data     = data


    }

    override fun getCount(): Int {
        return this.data.size
    }

    override fun getItem(p0: Int): Any {
       return this.data[p0]
    }

    override fun getItemId(p0: Int): Long {
       return 0;
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
        view = inflater.inflate(R.layout.riwayat_point_item,p2,false)

        view.findViewById<TextView>(R.id.periode).setText(namaBulan[data[p0].periode.toInt()])
        view.findViewById<TextView>(R.id.totalpoint).setText(data[p0].point)


        return view
    }
}