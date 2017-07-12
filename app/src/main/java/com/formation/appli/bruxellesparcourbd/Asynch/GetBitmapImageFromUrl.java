package com.formation.appli.bruxellesparcourbd.Asynch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jeremyvdm on 12/07/2017.
 */

public class GetBitmapImageFromUrl extends AsyncTask<String, Void, Bitmap> {

    private Bitmap imageBitmap;
    private String urlImage;


    //region Callback
    public interface GetBitmapImageFromUrlCallBack{
        void getBitmap( Bitmap imageFresqueBitmap);
    }

    public GetBitmapImageFromUrlCallBack callback;
    public void setCallback(GetBitmapImageFromUrlCallBack callback) {
        this.callback = callback;
    }
    //endregion


    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap imageurl=null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            URL url = new URL(urlImage);
            connection = (HttpURLConnection) url.openConnection();

            inputStream = connection.getInputStream();

            imageurl = BitmapFactory.decodeStream(inputStream, null, bmOptions);
            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }

        return imageBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        callback.getBitmap(imageBitmap);
    }
}
