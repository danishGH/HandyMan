package com.example.danish.handyman;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.net.ssl.ManagerFactoryParameters;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout mySwipeRefreshLayout;
    android.support.v7.widget.Toolbar toolbar;
    private DrawerLayout mDrawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mRecyclerView = findViewById(R.id.recycler_view);


    //Button buttonRegister = findViewById(R.id.register_company);
        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        categoryList();


        //Swipe to refresh
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("MainActivity.this", "onRefresh called from SwipeRefreshLayout");
                        categoryList();
                    }
                }
        );

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        switch (menuItem.getItemId()) {
                            case R.id.nav_user:
                                Intent intentUser = new Intent (MainActivity.this,UserLogin.class);
                                startActivity(intentUser);
                                mDrawerLayout.closeDrawers();
                                return true;
                            case R.id.nav_vendor:
                                    Intent intentVendor = new Intent(MainActivity.this,CreateAccount.class);
                                    startActivity(intentVendor);
                                    mDrawerLayout.closeDrawers();
                                    return true;
                            case R.id.nav_policy:
                                Intent intentPolicy = new Intent(MainActivity.this,PrivacyPolicy.class);
                                startActivity(intentPolicy);
                                mDrawerLayout.closeDrawers();
                                return true;
                            case R.id.nav_terms:
                                Intent intentTerms = new Intent (MainActivity.this,TermsAndConditions.class);
                                startActivity(intentTerms);
                                mDrawerLayout.closeDrawers();
                                return true;
                            case R.id.nav_about:
                                Intent intentAbout = new Intent (MainActivity.this,About.class);
                                startActivity(intentAbout);
                                mDrawerLayout.closeDrawers();
                                return true;
                        }
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }

//        //Sign up button
//        buttonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentSignUp = new Intent(MainActivity.this,CreateAccount.class);
//                startActivity(intentSignUp);
//            }
//        }
        //);



    //Toolbar for app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



        //Category fetch using volley
        public void categoryList()
        {
            final ArrayList<Category> categories = new ArrayList<>();
            String url = "http://43.252.214.56/cfmpro/public/api/categoryshow";

            //Request a string response from the provided url.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject root = new JSONObject(response);
                        JSONArray data = root.getJSONArray("data");
                        String image_url = root.getString("url");
                        for (int i= 0; i < data.length();i++){
                            JSONObject currentItem = data.getJSONObject(i);
                            String category_id = currentItem.getString("categoryID");
                            String category_name = currentItem.getString("categoryName");
                            String image_id = image_url + currentItem.getString("image");
                            Category category_item = new Category(category_id,category_name,image_id);
                            categories.add(category_item);

                        }
                        // use a Grid layout manager
                        mLayoutManager = new GridLayoutManager(MainActivity.this,3);
                        mRecyclerView.setLayoutManager(mLayoutManager);

                        //Set the adapter
                        mAdapter = new CategoryAdapter(categories,MainActivity.this,false,R.layout.category_item);
                        mRecyclerView.setAdapter(mAdapter);
                        mySwipeRefreshLayout.setRefreshing(false);

                    }catch (JSONException e){

                    }
                }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("MainActivity","The error is : "+ (error.getMessage()));
                }
            });
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }



}


