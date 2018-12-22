package com.example.l.uadnews;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.l.uadnews.Model.ProfileUpdate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by L on 28/04/18.
 */

public class Upload {
    public String uploadKeServer(Context context, Uri uri, String file, BeritaRequest beritaRequest) {
        HttpURLConnection conn = null;
        String tipefile = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String respon = null;
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 512000;
        try {
            URL url = new URL(HttpUrl.alamat + "api/berita/input");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());
            //ByteArrayInputStream fileInputStream=null;
            //fileInputStream = bitmapToStream(bitmap);
            FileInputStream fileInputStream = (FileInputStream)
                    context.getContentResolver().openInputStream(uri);
            Log.d("ukuran gambar", String.valueOf(fileInputStream.available()));
            String param = "type=" + tipefile;
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"userfile\";filename=\""
                    + file + "\"" + lineEnd + "");

            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            Log.d("ukuran buffer ", String.valueOf(bufferSize));
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //Log.d("ukuran buffer -- ",String.valueOf(bufferSize)+" - "+String.valueOf(bytesAvailable));
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                //Log.d("byte read",String.valueOf(bytesRead));
            }
            //Log.d("ukuran buffer terakhir ",String.valueOf(fileInputStream.available()));
            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"judul\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(beritaRequest.judul);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"img\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(beritaRequest.img);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"id_user\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(beritaRequest.id_user);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"isi_berita\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(beritaRequest.isi_berita);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"status_berita\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(beritaRequest.status_berita);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"waktu\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(beritaRequest.waktu);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"id_kategori\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(beritaRequest.id_kategori);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"lokasi\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(beritaRequest.lokasi);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens);

            fileInputStream.close();
            dos.flush();
            dos.close();
            BufferedReader buf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                sb.append(line);
            }
            buf.close();
            respon = sb.toString();
        } catch (Exception e) {
            respon = e.getMessage();
            Log.d("error", e.getMessage());
        }
        return respon;
    }


    public String updateProfile(Context context, Bitmap uri, String file, ProfileUpdate update) {
        HttpURLConnection conn = null;
        String tipefile = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String respon = null;
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 512000;
        try {
            URL url = new URL(HttpUrl.alamat + "api/profile/update");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());
            //ByteArrayInputStream fileInputStream=null;
            //fileInputStream = bitmapToStream(bitmap);

            String param = "type=" + tipefile;
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            if (uri != null) {
                ByteArrayInputStream fileInputStream = bitmapToStream(uri);
                Log.d("ukuran gambar", String.valueOf(fileInputStream.available()));
                dos.writeBytes("Content-Disposition: form-data; name=\"img_user\";filename=\""
                        + file + "\"" + lineEnd + "");

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                Log.d("ukuran buffer ", String.valueOf(bufferSize));
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    //Log.d("ukuran buffer -- ",String.valueOf(bufferSize)+" - "+String.valueOf(bytesAvailable));
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    //Log.d("byte read",String.valueOf(bytesRead));
                }
                //Log.d("ukuran buffer terakhir ",String.valueOf(fileInputStream.available()));
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                fileInputStream.close();
            }

            dos.writeBytes("Content-Disposition: form-data; name=\"username\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(update.getUsername());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"nama_user\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(update.getNamalengkap());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"status\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(update.getStatus());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"no_hp\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(update.getNotelp());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"email\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(update.getEmail());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"iduser\"" + lineEnd + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(new Prefferences(context).getIduser());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens);

            dos.flush();
            dos.close();
            BufferedReader buf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                sb.append(line);
            }
            buf.close();
            respon = sb.toString();
        } catch (Exception e) {
            respon = e.getMessage();
            Log.d("error", e.getMessage());
        }
        return respon;
    }

    public ByteArrayInputStream bitmapToStream(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bitmap = bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
        return bs;
    }
}
