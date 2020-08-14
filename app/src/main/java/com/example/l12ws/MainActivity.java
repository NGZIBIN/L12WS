package com.example.l12ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private AsyncHttpClient client;
    private ArrayList<Incident> alIncident;
    private ArrayAdapter<Incident> aaIncident;
    private ListView lv;

    private FirebaseFirestore db;
    private CollectionReference colRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alIncident = new ArrayList<Incident>();
        client = new AsyncHttpClient();
        lv = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void onResume() {
        super.onResume();


        alIncident.clear();
        client.addHeader("AccountKey", "cYsiznKuReChgmNVjkun9Q==");


        client.get("http://datamall2.mytransport.sg/ltaodataservice/TrafficIncidents", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try{
                    Log.i("JSON Results: ", response.toString());
                    JSONArray jsonArray = response.getJSONArray("value");
                    for(int i = 0; i<response.length(); i ++){
                        JSONObject jsonObj = jsonArray.getJSONObject(i);

                        String type = jsonObj.getString("Type");
                        String lat = jsonObj.getString("Latitude");
                        String lng = jsonObj.getString("Longitude");
                        String msg = jsonObj.getString("Message");
                        Date date = new Date();

                        Incident incident = new Incident(date, lat, lng, msg, type);
                        alIncident.add(incident);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                aaIncident = new IncidentAdapter(getApplicationContext(), R.layout.row, alIncident);
                lv.setAdapter(aaIncident);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Incident chosen = alIncident.get(i);
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                intent.putExtra("method", "listview");
                intent.putExtra("lnt", chosen.getLatitude());
                intent.putExtra("long", chosen.getLongtitude());
                intent.putExtra("type", chosen.getType());
                intent.putExtra("msg", chosen.getMessage());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_upload:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Upload to firestore");
                builder.setMessage("Proceed to upload to firestore?");

                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        db = FirebaseFirestore.getInstance();
                        colRef = db.collection("Incidents");

                        for(int i = 0; i< alIncident.size(); i ++){
                            colRef.add(alIncident.get(i));

                        }

                    }
                });

                builder.setNegativeButton("Cancel", null);

                builder.create();
                builder.show();


            case R.id.action_calendar:
                return true;

            case R.id.action_myPlace:
                Intent i = new Intent(getBaseContext(), MapsActivity.class);
                i.putExtra("method", "action");
                i.putExtra("list", alIncident);
                startActivity(i);
                return true;

            case R.id.action_refresh:
                onResume();


        }
        return super.onOptionsItemSelected(item);
    }
}