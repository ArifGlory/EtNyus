package com.example.l.uadnews;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by L on 28/04/18.
 */

public class Prefferences {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Prefferences(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("akun",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean checkLogin(){
        if(sharedPreferences.getString("id_user",null) == null)
            return  false;
        else return  true;
    }
    public void setLogin(String iduser){
        editor.putString("id_user",iduser);
        editor.commit();
    }
    public String getIduser(){
        return sharedPreferences.getString("id_user",null);
    }

    public void deleteLogin(){
        editor.clear();
        editor.commit();
    }
}
