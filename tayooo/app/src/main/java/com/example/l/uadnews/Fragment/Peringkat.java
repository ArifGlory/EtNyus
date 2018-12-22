package com.example.l.uadnews.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.l.uadnews.Adapter.PeringkatAdapter;
import com.example.l.uadnews.HttpUrl;
import com.example.l.uadnews.Model.PeringkatModel;
import com.example.l.uadnews.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Peringkat extends Fragment {

    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_peringkat,container,false);

        listView = (ListView)view.findViewById(R.id.listperingkat);

        getData();

        return view;
    }

    public void getData(){
        RequestQueue requestQueu = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(HttpUrl.alamat + "api/berita/peringkat",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonObject = new JSONArray(response);
                            List<PeringkatModel> peringkatModels =new ArrayList<>();
                            for(int i = 0;i<jsonObject.length();i++){
                                JSONObject object =jsonObject.getJSONObject(i);
                                PeringkatModel peringkatModel = new PeringkatModel();
                                peringkatModel.banyak = object.getString("banyak");
                                peringkatModel.email  = object.getString("email");
                                peringkatModel.nama_user = object.getString("nama_user");
                                peringkatModels.add(peringkatModel);
                            }

                            listView.setAdapter(new PeringkatAdapter(getContext(),peringkatModels));


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueu.add(stringRequest);
    }
}
