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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.l.uadnews.Adapter.HistoryAdapter;
import com.example.l.uadnews.HttpUrl;
import com.example.l.uadnews.Model.HistoryModel;
import com.example.l.uadnews.Prefferences;
import com.example.l.uadnews.R;
import com.example.l.uadnews.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Riwayat extends Fragment {

    private ListView listberita;
    private TextView noBerita;
    List<HistoryModel> historyModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_riwayat,container,false);
        listberita = (ListView)view.findViewById(R.id.listberita);
        noBerita = (TextView)view.findViewById(R.id.noriwayat);

        listberita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_riwayat);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
                final VideoView videoview = (VideoView) dialog.findViewById(R.id.video);
                final ImageView imageView = (ImageView)dialog.findViewById(R.id.img);
                ((TextView)dialog.findViewById(R.id.isi)).setText(historyModels.get(i).isi_berita);
                dialog.show();;

                new AsyncTask<Void,Void,Bitmap>(){
                    @Override
                    protected Bitmap doInBackground(Void... voids) {
                        Bitmap bitmap = null;
                        try {
                            String img = historyModels.get(i).img;
                            URL url = new URL(HttpUrl.alamat+"assets/upload/"+img);
                            InputStream inputStream = url.openConnection().getInputStream();

                            bitmap  = BitmapFactory.decodeStream(inputStream);

                        }catch (Exception e){

                            Log.d("error gambar",e.getMessage());
                        }
                        return bitmap;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        Bitmap bitmap1 = new Util().ubahOrenatasi(bitmap);
                        imageView.setImageBitmap(bitmap1);

                        if(String.valueOf(bitmap) == "null"){
                            videoview.setVisibility(View.VISIBLE);
                            videoview.setVideoURI(Uri.parse(HttpUrl.alamat+
                                    "assets/upload/"+historyModels.get(i).img));
                            MediaController mediaController = new MediaController(videoview.getContext());
                            mediaController.setMediaPlayer(videoview);
                            mediaController.setAnchorView(videoview);
                            videoview.setMediaController(mediaController);
                            videoview.requestFocus();
                            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mediaPlayer.start();
                                }
                            });

                        }
                    }
                }.execute();

            }
        });
        getData();
        return view;

    }

    public void getData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(HttpUrl.alamat + "api/berita/riwayat/"
                + new Prefferences(getContext()).getIduser(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response",response);
                            JSONArray    jsonArray = new JSONArray(response);
                            historyModels = new ArrayList<>();
                            for(int i =0;i<jsonArray.length();i++){
                                JSONObject data = jsonArray.getJSONObject(i);
                                HistoryModel historyModel = new HistoryModel();
                                historyModel.judul = data.getString("judul");
                                historyModel.isi_berita = data.getString("isi_berita");
                                historyModel.keterangan = data.getString("status_berita");
                                historyModel.tanggal = data.getString("tanggal_post");
                                historyModel.img = data.getString("img");
                                historyModels.add(historyModel);
                            }
                            if(historyModels.size()>0) noBerita.setVisibility(View.GONE);
                            listberita.setAdapter(new HistoryAdapter(getContext(),historyModels));

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
}
