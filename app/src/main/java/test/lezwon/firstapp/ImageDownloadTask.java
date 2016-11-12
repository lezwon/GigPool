package test.lezwon.firstapp;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static android.os.Environment.*;

/**
 * Created by Lezwon on 12-11-2016.
 */
class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

    private final Uri uri;
    private Bitmap bitmap;
    private File directory = new File(getExternalStorageDirectory(), "Bhatinder");
    private File profileImage = new File(directory, "profile.PNG");

    ImageDownloadTask(Uri uri) {
        this.uri = uri;
    }

    @Override
    protected Bitmap doInBackground(String... url) {

        try{
            InputStream inputStream = new URL(uri.toString()).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPreExecute() {
//        if(profileImage.exists())
//            this.cancel(true);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if(!Environment.MEDIA_MOUNTED.equals(getExternalStorageState())){
            return;
        }

        if(!directory.exists())
            if(!directory.mkdir())
                return;

        if(profileImage.exists())
            if(!profileImage.delete())
                return;

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(profileImage);
            bitmap.compress(Bitmap.CompressFormat.PNG,80,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
