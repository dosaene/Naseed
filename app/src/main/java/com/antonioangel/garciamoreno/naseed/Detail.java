package com.antonioangel.garciamoreno.naseed;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Detail extends Activity {
    private String image;
    ImageView imvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.detail);

        imvDetail = (ImageView) findViewById(R.id.imvDetail);

        image = getIntent().getExtras().getString("image");
        new AsyncObtainImage().execute();

    }

    class AsyncObtainImage extends AsyncTask<Void, Void, Integer> {
        Bitmap bitmap;

        protected void onPreExecute(){

        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(image).getContent());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Integer result){

            imvDetail.setImageBitmap(bitmap);

        }
    }
}
