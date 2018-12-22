package com.example.l.uadnews;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.OpenableColumns;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by L on 28/04/18.
 */

public class Util {

    public String getFileName(Context context,Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void getImage(final String alamat, final ImageView imageView, final Context context){

    }

    public Bitmap ubahOrenatasi(Bitmap uri){
        android.support.media.ExifInterface exifInterface = null;
        Bitmap bitmap = null;
        try {
            bitmap = uri;
            exifInterface = new android.support.media.ExifInterface(bitmapToStream(bitmap));

            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,1);
            Matrix matrix = new Matrix();
            int lebar = bitmap.getWidth();
            int tinggi= bitmap.getHeight();

            int setTinggi = lebar;
            int setLebar  = tinggi;
            int scalelebar;
            int scaletinggi;

            //matrix.setScale(scalelebar,scaletinggi);
            if(orientation == 6){
                matrix.postRotate(90);
            }else if(orientation == 3){
                matrix.postRotate(180);
            }else if (orientation == 8){
                matrix.postRotate(270);
            }
            //matrix.postRotate(90);
            scalelebar = 648;scaletinggi = 1152;
            bitmap = Bitmap.createBitmap(bitmap,0,0,lebar,tinggi,matrix,true);
            if(lebar > tinggi){
                int temp = scalelebar;
                scalelebar = scaletinggi;
                scaletinggi = temp;
            }
            bitmap = Bitmap.createScaledBitmap(bitmap,scalelebar,scaletinggi,true);
            Log.d("orientasinya",String.valueOf(orientation));

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    public ByteArrayInputStream bitmapToStream(Bitmap bitmap){
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
    public Bitmap ubahOrenatasi2(Context context,Uri uri){
        android.support.media.ExifInterface exifInterface = null;
        Bitmap bitmap = null;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                    uri);
            exifInterface = new android.support.media.ExifInterface(context.getContentResolver().openInputStream(uri));

            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,1);
            Matrix matrix = new Matrix();
            int lebar = bitmap.getWidth();
            int tinggi= bitmap.getHeight();

            int setTinggi = lebar;
            int setLebar  = tinggi;
            int scalelebar;
            int scaletinggi;

            //matrix.setScale(scalelebar,scaletinggi);
            if(orientation == 6){
                matrix.postRotate(90);
            }else if(orientation == 3){
                matrix.postRotate(180);
            }else if (orientation == 8){
                matrix.postRotate(270);
            }
            //matrix.postRotate(90);
            scalelebar = 648;scaletinggi = 1152;
            bitmap = Bitmap.createBitmap(bitmap,0,0,lebar,tinggi,matrix,true);
            if(lebar > tinggi){
                int temp = scalelebar;
                scalelebar = scaletinggi;
                scaletinggi = temp;
            }
           // bitmap = Bitmap.createScaledBitmap(bitmap,scalelebar,scaletinggi,true);
            Log.d("orientasinya",String.valueOf(orientation));

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
