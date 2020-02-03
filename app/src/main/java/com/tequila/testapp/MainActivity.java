package com.tequila.testapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tequila.testapp.models.ItemList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import adapters.AdapterItem;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public OkHttpClient client;
    public Context context;
    public RecyclerView recyclerView;
    public AdapterItem adapterItem;
    public ArrayList<ItemList> arrayList;
    public long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        task.execute();
    }

    String runQuery() throws IOException {
        int countTracks = 200;
        //startTimer();
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.shazam.com/shazam/v2/ru/RU/web/-/tracks/country-chart-RU?pageSize=" + countTracks + "&startFrom=0")
                .build();
        Response response = client.newCall(request).execute();
        //stopTimer("Connecting");
        return response.body().string();
    }

    @SuppressLint("StaticFieldLeak")
    final AsyncTask task = new AsyncTask() {

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                JSONArray chart = new JSONObject(runQuery()).getJSONArray("chart");
                arrayList = new ArrayList<>();

                for (int i = 0; i < chart.length(); i++) {

                    String artistValue = chart.getJSONObject(i).getJSONObject("heading").getString("subtitle");
                    String titleValue = chart.getJSONObject(i).getJSONObject("heading").getString("title");
                    String coverUrl = null;

                    if (!chart.getJSONObject(i).getJSONObject("images").isNull("default")) {
                        coverUrl = chart.getJSONObject(i).getJSONObject("images").getString("default");
                    } else {
                        coverUrl = "https://tequila-apps.ru/files/default_cover.png";
                    }

                    ItemList itemList = new ItemList(artistValue, titleValue, coverUrl);
                    arrayList.add(itemList);
                }
            } catch (IOException | JSONException e) {
                return null;
            }
            return "true";
        }

        @Override
        protected void onPostExecute(Object o) {
            if (o != null) {
                //startTimer();
                adapterItem = new AdapterItem(context, arrayList);
                recyclerView.setAdapter(adapterItem);
                //stopTimer("Adapter");
            }
        }
    };

    public void startTimer() {
        time = 0;
        time = System.nanoTime();
    }

    public void stopTimer(String info) {
        time = System.nanoTime() - time;
        System.out.printf("Timer: (" + info + ") Время выполнения: %,9.3f ms\n", time / 1_000_000.0);
    }
}