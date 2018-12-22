package com.example.l.uadnews;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.uadnews.Fragment.Input;
import com.example.l.uadnews.Fragment.Peringkat;
import com.example.l.uadnews.Fragment.PointFragment;
import com.example.l.uadnews.Fragment.Profile;
import com.example.l.uadnews.Fragment.Riwayat;

public class MainActivity extends AppCompatActivity {

    private View main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.click)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/* video/*");
                startActivityForResult(pickIntent, 1);
            }
        });
        main = (RelativeLayout)findViewById(R.id.main);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.manusr)  ,0);
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.trophy)  ,1);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.note)    ,1);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.history) ,2);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.award)   ,3);


        if(!new Prefferences(this).checkLogin()){
            startActivity(new Intent(this,Login.class));
            finish();
        }
        main.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.transisi_biru));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.efect,R.anim.fadeout);
        transaction.replace(R.id.frame,new Profile());
        transaction.commit();
        tabListener(tabLayout);

    }

    public void tabListener(TabLayout tabLayout){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 4){
                    //logout();
                }else if(tab.getPosition() == 2){
                    //main.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.transisi_biru)); //ngatur background tampilan biar hokya
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.efect,R.anim.fadeout);
                    transaction.replace(R.id.frame,new Riwayat());
                    transaction.commit();
                }else if(tab.getPosition() == 1){
                    //main.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.transisi_biru));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.efect,R.anim.fadeout);
                    transaction.replace(R.id.frame,new Input());
                    transaction.commit();
            //    }else if(tab.getPosition() == 1){
                    //main.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.transisi_biru));
              //      FragmentManager fragmentManager = getSupportFragmentManager();
              //      FragmentTransaction transaction = fragmentManager.beginTransaction();
              //      transaction.setCustomAnimations(R.anim.efect,R.anim.fadeout);
              //      transaction.replace(R.id.frame,new Peringkat());
              //      transaction.commit();
                }else if(tab.getPosition() == 3){
                    //main.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.transisi_biru));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.efect,R.anim.fadeout);
                    transaction.replace(R.id.frame,new PointFragment());
                    transaction.commit();
                }else{
                    //main.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.transisi_biru));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.efect,R.anim.fadeout);
                    transaction.replace(R.id.frame,new Profile());
                    transaction.commit();


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 5)
                    logout();
            }
        });
    }

    public void logout(){
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda Yakin Keluar ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new Prefferences(MainActivity.this).deleteLogin();
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Toast.makeText(this,
                    new Util().getFileName(getApplicationContext(),data.getData()),
                    Toast.LENGTH_SHORT).show();
            new AsyncTask<Void,Void,String>(){
                @Override
                protected String doInBackground(Void... voids) {

                    BeritaRequest beritaRequest = new BeritaRequest();
                    beritaRequest.id_user = "2";
                    beritaRequest.img = "dfdf";
                    beritaRequest.isi_berita= "dfdf";
                    beritaRequest.judul = "fdf";
                    beritaRequest.status_berita = "menunggu";
                    beritaRequest.lokasi = "jakarta";
                    beritaRequest.waktu = "2019-04-02";
                    beritaRequest.id_kategori = "1";

                    return new Upload().uploadKeServer(MainActivity.this,
                            data.getData(),new Util().getFileName(MainActivity.this,data.getData()),
                            beritaRequest);
                }

                @Override
                protected void onPostExecute(String aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(MainActivity.this, "uploaded "+aVoid, Toast.LENGTH_SHORT).show();
                }
            }.execute();
        }
    }
}
