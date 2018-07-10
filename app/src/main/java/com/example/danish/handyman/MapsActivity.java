package com.example.danish.handyman;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String sub_category_id;
    String category_id;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    MapDetailList map_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mRecyclerView = findViewById(R.id.recycler_view_maps);
        getIncomingIntent();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final ArrayList<MapDetailList> mapDetailList = new ArrayList<>();

        String url = "http://192.168.2.96/cpm-pro/public/api/category/"+ category_id+"/"+sub_category_id ;

        //Requesting a String response from the provided url.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONArray data = root.getJSONArray("data");
                    String image_url = root.getString("url");
                    for (int i = 0; i < data.length();i++){
                        JSONObject currentItem = data.getJSONObject(i);
                        String company_name = currentItem.getString("companyname");
                        String phone = currentItem.getString("phone");
                        String name = currentItem.getString("name");
                        String image_id = image_url + currentItem.getString("image");
                        double latitude = currentItem.getDouble("latitude");
                        double longitude = currentItem.getDouble("longitude");
                        String address = currentItem.getString("address");

                        LatLng location = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(location).title(company_name));
                        map_item = new MapDetailList(company_name,phone,name,image_id,latitude,longitude,address);
                        mapDetailList.add(map_item);

                    }
                    //Use a Linear Layout manager
                    mLayoutManager = new LinearLayoutManager(MapsActivity.this, LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    //Set the adapter
                    mAdapter = new MapAdapter(mapDetailList,MapsActivity.this,R.layout.map_detail_list);
                    mRecyclerView.setAdapter(mAdapter);

                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(location));


                }catch (JSONException e){
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MapsActivity","The error is : "+ (error.getMessage()));
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

        // Add a marker in Kuala Lumpur and move the camera




    }

    //Getting intent
    private void getIncomingIntent(){

        if (getIntent().hasExtra("sub_category_id") && getIntent().hasExtra("category_id")){

            sub_category_id = getIntent().getStringExtra("sub_category_id");
            category_id = getIntent().getStringExtra("category_id");
        }


    }
}
