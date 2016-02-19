package com.antonioangel.garciamoreno.naseed;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Event> events;
    private ListView lsvEventList;
    private EventListAdapter eventListAdapter;
    private StringBuilder result;
    HttpURLConnection urlConnection;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        events = new ArrayList<>();
        lsvEventList = (ListView) findViewById(R.id.lsvEventList);

        new AsyncObtainEvents().execute();

        lsvEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = (Event) parent.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this, Detail.class);
                i.putExtra("image", event.getImage());
                startActivity(i);
            }
        });
    }

    class AsyncObtainEvents extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute(){

        }

        @Override
        protected Integer doInBackground(Void... params) {
            obtainEvents();
            return null;
        }

        protected void onPostExecute(Integer result){

            eventListAdapter = new EventListAdapter(MainActivity.this, R.layout.listitem, events);
            lsvEventList.setAdapter(eventListAdapter);
            for (int i = 0; i < num; i++) {
                new AsyncObtainEvents2().execute(i);
            }

        }
    }

    public void obtainEvents(){
        result = new StringBuilder();

        try {
            URL url = new URL("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        saveEvents(result.toString());
    }

    public void saveEvents(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsa = jsonObject.getJSONArray("photos");
            num = jsa.length();
            for (int i = 0; i < num; i++) {
                JSONObject jso = jsa.getJSONObject(i);



                //Create a new event with the JSONObject obtained
                Event event = new Event(jso.getString("earth_date"), jso.getJSONObject("camera").getString("full_name"), jso.getString("img_src"));


                events.add(event);//Add event to ArrayList
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class AsyncObtainEvents2 extends AsyncTask<Integer, Void, Integer> {

        protected void onPreExecute(){

        }

        @Override
        protected Integer doInBackground(Integer... params) {
            Event event = events.get(params[0]);
            try {
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(event.getImage()).getContent());
                event.setBtmp(scaleDown(bitmap, 100, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            events.set(params[0], event);

            return null;
        }

        protected void onPostExecute(Integer result){

            eventListAdapter.notifyDataSetChanged();

        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }
}
