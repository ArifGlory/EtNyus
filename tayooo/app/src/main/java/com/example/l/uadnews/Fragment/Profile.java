package com.example.l.uadnews.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.l.uadnews.HttpUrl;
import com.example.l.uadnews.Login;
import com.example.l.uadnews.MainActivity;
import com.example.l.uadnews.Model.ProfileUpdate;
import com.example.l.uadnews.Prefferences;
import com.example.l.uadnews.R;
import com.example.l.uadnews.Upload;
import com.example.l.uadnews.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    CircleImageView circleImageView;

    EditText username,namalengkap,notelp,email,status;
    private Bitmap uri = null;
    String filename = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile,container,false);
        circleImageView = ((CircleImageView)view.findViewById(R.id.img));
        username = (EditText)view.findViewById(R.id.username);
        namalengkap = (EditText)view.findViewById(R.id.namaleng);
        notelp = (EditText)view.findViewById(R.id.telp);
        email = (EditText)view.findViewById(R.id.email);
        status = (EditText)view.findViewById(R.id.status);


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                startActivityForResult(pickIntent, 1);
            }
        });

        getData(new Prefferences(getContext()).getIduser());

        ((Button)view.findViewById(R.id.udpate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
        ((Button)view.findViewById(R.id.updatepassword)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });

        ((Button)view.findViewById(R.id.keluar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Apakah Anda Yakin Keluar ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Prefferences(getContext()).deleteLogin();
                        startActivity(new Intent(getContext(),Login.class));
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });

        return view;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            try {
                Bitmap bitmap = new Util().ubahOrenatasi2(
                                getContext(),data.getData());
                uri = bitmap;
                circleImageView.setImageBitmap(bitmap);
                filename = new Util().getFileName(getContext(),data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void getData(String iduser){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl.alamat+ "api/profile/index/"+iduser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            username.setText(jsonObject.getString("username"));
                            namalengkap.setText(jsonObject.getString("nama_user"));
                            notelp.setText(jsonObject.getString("no_hp"));
                            email.setText(jsonObject.getString("email"));
                            status.setText(jsonObject.getString("status"));
                            if(jsonObject.getString("img_user") != "null"){
                                loadImage(jsonObject.getString("img_user"));
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    public void loadImage(final String ulrImg){
        new AsyncTask<Void,Void,Bitmap>(){
            @Override
            protected Bitmap doInBackground(Void... voids) {
                Bitmap bitmap = null;
                try {
                    URL url = new URL(HttpUrl.alamat+"assets/upload/"+ulrImg);
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
                circleImageView.setImageBitmap(bitmap);
            }
        }.execute();

    }

    public void updateProfile(){
        final ProfileUpdate profileUpdate = new ProfileUpdate();
        profileUpdate.setEmail(email.getText().toString());
        profileUpdate.setNamalengkap(namalengkap.getText().toString());
        profileUpdate.setNotelp(notelp.getText().toString());
        profileUpdate.setStatus(status.getText().toString());
        profileUpdate.setUsername(username.getText().toString());


        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {

                return new Upload().updateProfile(getContext(),uri,filename,profileUpdate);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getContext(),
                        "sukses", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
    public void updatePassword(){
        final  Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_update_password);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ((Button)dialog.findViewById(R.id.simpan)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newp,oldp,confirm;
                newp = (EditText)dialog.findViewById(R.id.baru);
                oldp = (EditText)dialog.findViewById(R.id.old);
                confirm = (EditText)dialog.findViewById(R.id.confirm);

                if (newp.getText().toString().equals(confirm.getText().toString())) {
                    requestUpdatePassowrd(oldp.getText().toString(),newp.getText().toString(),dialog);

                }else{
                    Toast.makeText(getContext(),
                            "password tidak sama", Toast.LENGTH_SHORT).show();
                }

            }
        });


        dialog.show();

    }
    public void requestUpdatePassowrd(final String lama, final String baru, final Dialog dialog){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                HttpUrl.alamat + "api/profile/updatepassword", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("sukses")){
                    Toast.makeText(getContext(), "sukses", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else Toast.makeText(getContext(), "gagal", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("old",lama);
                map.put("baru",baru);
                map.put("iduser",new Prefferences(getContext()).getIduser());
                return map;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}
