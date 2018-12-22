package com.example.l.uadnews.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.l.uadnews.Adapter.HistoryAdapter;
import com.example.l.uadnews.Adapter.PointAdapter;
import com.example.l.uadnews.Adapter.PointsAdapter;
import com.example.l.uadnews.HttpUrl;
import com.example.l.uadnews.Model.HistoryModel;
import com.example.l.uadnews.Model.Point;
import com.example.l.uadnews.Model.PointModel;
import com.example.l.uadnews.Prefferences;
import com.example.l.uadnews.R;
import com.example.l.uadnews.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointFragment extends Fragment {

    private TextView noPoint,totalpoint;
    List<PointModel> pointModels;
    ListView pointlistview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_point,container,false);
        totalpoint = (TextView)view.findViewById(R.id.totalpoint);
        pointlistview =  (ListView)view.findViewById(R.id.riwayatpoint);
        getData();
        getPoint();

        return view;

    }

    public void getData(){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(HttpUrl.alamat + "api/berita/pointAndClaim/"
                + new Prefferences(getContext()).getIduser(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String total = obj.getString("banyak");
                            totalpoint.append(total);
                        }catch (Exception e){
                            Toast.makeText(getContext(),
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    public void getPoint(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST,HttpUrl.alamat + "api/profile/riwayat/"
                + new Prefferences(getContext()).getIduser(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      Log.d("error",response);
                        try {
                            Log.d("response",response);
                            JSONObject data = new JSONObject(response);
                            JSONArray array = data.getJSONArray("hasil");
                            List<PointModel> pointList = new ArrayList<>();
                            for(int i =0;i<array.length();i++){
                                PointModel point = new PointModel();

                                point.periode = (array.getJSONObject(i).getString("bulan"));
                                point.point = (array.getJSONObject(i).getString("jumlah"));
                                pointList.add(point);
                            }

                            if(array.length()>0){
                                pointlistview.setAdapter(new PointAdapter(getContext(),pointList));
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(),
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("id_user",new Prefferences(getContext()).getIduser());
                return data;

            }
        };
        requestQueue.add(stringRequest);
    }
}
