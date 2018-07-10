package com.example.danish.handyman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubCategory extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    String category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIncomingIntent();
        setContentView(R.layout.sub_category_list);
        mRecyclerView = findViewById(R.id.recycler_view_sub_category);

        final ArrayList<Category> categories = new ArrayList<>();
        //Instantiating the request queue


        String url = "http://43.252.214.56/cfmpro/public/api/subcategory/"+category_id ;

        Log.i("URL", url);
        //Request a string response from the provided url.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("SubCategory","Response --------------------------> "+response);
                try {
                    JSONObject root = new JSONObject(response);
                    String image_url = root.getString("url");
                    JSONArray data = root.getJSONArray("data") ;
                    for (int i = 0; i < data.length();i++){
                        JSONObject currentItem = data.getJSONObject(i);
                        String sub_category_id = currentItem.getString("sub_id");
                        String sub_category_name = currentItem.getString("sub_cat_name");
                        String parent_category = currentItem.getString("parent_cat");
                        String image_id = image_url + currentItem.getString("image");
                        Category sub_category_item = new Category(parent_category,sub_category_name,image_id,sub_category_id);
                        categories.add(sub_category_item);
                    }
                    //Use a Linear Layout manager
                    mLayoutManager = new LinearLayoutManager(SubCategory.this, LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    //Set the adapter
                    mAdapter = new CategoryAdapter(categories,SubCategory.this,true,R.layout.sub_category_item);
                    mRecyclerView.setAdapter(mAdapter);



                } catch (JSONException e) {

                }


        }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("SubCategory","The error is : "+ (error.getMessage()));
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    //Getting data from the intent
    private void getIncomingIntent(){

        if (getIntent().hasExtra("category_id")){

           category_id = getIntent().getStringExtra("category_id");
        }


    }

}
