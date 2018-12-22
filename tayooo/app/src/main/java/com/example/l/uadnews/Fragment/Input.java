package com.example.l.uadnews.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.uadnews.BeritaRequest;
import com.example.l.uadnews.Prefferences;
import com.example.l.uadnews.R;
import com.example.l.uadnews.Upload;
import com.example.l.uadnews.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Input extends Fragment {

    TextView file;
    TextView judul;
    TextView lokasi;
    TextView waktu;
    TextView deskripsi;
    Button kirim;
    Spinner kategori;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_input, container, false);

        judul = ((TextView) view.findViewById(R.id.judulberita));
        lokasi = ((TextView) view.findViewById(R.id.lokasi));
        waktu = ((TextView) view.findViewById(R.id.waktu));
        deskripsi = ((TextView) view.findViewById(R.id.deskripsi));
        file = ((TextView) view.findViewById(R.id.file));
        kategori = (Spinner) view.findViewById(R.id.kategori);
        kirim = (Button) view.findViewById(R.id.bKirim);
        List<String> stringKategori = new ArrayList<>();
        stringKategori.add("Pilih Kategori");
        stringKategori.add("Kerjasama");
        stringKategori.add("Kunjungan");
        stringKategori.add("Kegiatan Ilmiah");
        stringKategori.add("Prestasi dan Penghargaan");
        stringKategori.add("Kegiatan Pengabdian Masyarakat");
        stringKategori.add("Info Kegiatan akan datang");
        stringKategori.add("Seputar Perkuliahan");
        stringKategori.add("Kegiatan UKM/Kemahasiswaan");
        stringKategori.add("Penelitian, Penemuan, inovasi");
        stringKategori.add("Lainnya");
        ArrayAdapter<String> kategoriAdpater = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, stringKategori);
        kategori.setAdapter(kategoriAdpater);

        file.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Intent pickIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/* video/*");
                    startActivityForResult(pickIntent, 12);
                }
            }
        });

        waktu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    /*val dialog = DatePickerDialog(this@RiwayatReseller, R.style.DialogTheme,
                            setDate(ke),
                            val calendar = Calendar.getInstance(TimeZone.getDefault())
                            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH))
                    dialog.show();*/
                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                    Dialog dial = new DatePickerDialog(getContext(), R.style.DialogTheme,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    String date = String.valueOf(i) + "-" + String.valueOf(i1 + 1)
                                            + "-" + String.valueOf(i2);
                                    waktu.setText(date);
                                }
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dial.getWindow().setBackgroundDrawable(null);
                    dial.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    dial.show();
                }
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 12) {
                file.setText(new Util().getFileName(getContext(), data.getData()));
                uri = data.getData();
            }
        }
    }

    String idkategori = "1";
    Uri uri;

    public void submit() {
        BeritaRequest beritaRequest = new BeritaRequest();
        beritaRequest.id_user = new Prefferences(getContext()).getIduser();
        beritaRequest.img = file.getText().toString();
        beritaRequest.isi_berita = deskripsi.getText().toString();
        beritaRequest.judul = judul.getText().toString();
        beritaRequest.status_berita = "menunggu";
        beritaRequest.lokasi = lokasi.getText().toString();
        beritaRequest.waktu = waktu.getText().toString();

        kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1)
                    idkategori = "1";
                else if (i == 2) {
                    idkategori = "2";
                } else if (i == 3) {
                    idkategori = "3";
                } else if (i == 4) {
                    idkategori = "4";
                } else if (i == 5) {
                    idkategori = "5";
                } else if (i == 6) {
                    idkategori = "6";
                } else if (i == 7) {
                    idkategori = "7";
                } else if (i == 8) {
                    idkategori = "8";
                } else if (i == 9) {
                    idkategori = "9";
                } else if (i == 10) {
                    idkategori = "10";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        beritaRequest.id_kategori = idkategori;
        readyUpload(uri, beritaRequest);
    }

    public void readyUpload(final Uri uri, final BeritaRequest beritaRequest) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        ;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return new Upload().uploadKeServer(getContext(), uri, file.getText().toString(), beritaRequest);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                resetInput();
                Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }.execute();
    }

    public void resetInput() {
        file.setText("");
        judul.setText("");
        lokasi.setText("");
        waktu.setText("");
        deskripsi.setText("");
    }
}
