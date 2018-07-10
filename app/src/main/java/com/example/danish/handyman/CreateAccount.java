package com.example.danish.handyman;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText mCompany_company_name;
    EditText mCompany_address;
    EditText mCompany_latitude;
    EditText mCompany_longitude;
    EditText mCompany_registration;
    EditText mCompany_phone;
    EditText mCompany_name;
    EditText mCompany_ic_number;
    Button mCompany_register;
    String post_url = "http://43.252.214.56/cfmpro/public/api/signup";
    String spinner_url_category = "http://43.252.214.56/cfmpro/public/api/categoryshow";
    String spinner_url_subcategory = "http://43.252.214.56/cfmpro/public/api/subcategoryshow";
    AlertDialog.Builder builder;
    Spinner spinner_category;
    Spinner spinner_subcategory;
    ArrayAdapter<String> adapter;
    String[] array;
    String [] array_subcategory;
    String selected_cat_ID;
    String selected_sub_ID;
    String [] categoryIDs;
    String [] subCategoryIDs;
    List<SubCategorySpinner> list;
    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mCompany_company_name = findViewById(R.id.company_name);
        mCompany_address = findViewById(R.id.company_address);
        mCompany_latitude = findViewById(R.id.company_latitude);
        mCompany_longitude = findViewById(R.id.company_longitude);
        mCompany_registration = findViewById(R.id.company_registration);
        mCompany_register = findViewById(R.id.button_company_register);
        mCompany_phone = findViewById(R.id.company_phone);
        mCompany_ic_number = findViewById(R.id.ic_number);
        mCompany_name = findViewById(R.id.name);
        builder = new AlertDialog.Builder(CreateAccount.this);
        spinner_category = findViewById(R.id.spinner_category);
        spinner_subcategory = findViewById(R.id.spinner_subcategory);
        imageView = findViewById(R.id.icon_picker);

        //Company Logo Picker
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });


        


        //Request a string response from the provided url.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, spinner_url_category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject root = new JSONObject(response);
                    JSONArray data = root.getJSONArray("data");
                    array = new String[data.length()];
                    categoryIDs = new String[data.length()];

                    for (int i= 0; i < data.length();i++)
                    {
                        JSONObject currentItem = data.getJSONObject(i);
                        String category_id = currentItem.getString("categoryID");
                        String category_name = currentItem.getString("categoryName");
                        array[i] = category_id + " " + category_name ;
                        categoryIDs[i] = category_id;
                    }


                    adapter = new ArrayAdapter<>(CreateAccount.this, android.R.layout.simple_spinner_item, array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_category.setAdapter(adapter);
            }catch (JSONException e){

                }
        }},new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("CreateAccount","The error is : "+ (error.getMessage()));
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        spinner_category.setOnItemSelectedListener(this);

        //Request a string response from the provided url.
        StringRequest stringRequesttwo = new StringRequest(Request.Method.GET, spinner_url_subcategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject root = new JSONObject(response);
                    JSONArray data = root.getJSONArray("data");
                    array_subcategory = new String[data.length()];
                    subCategoryIDs = new String[data.length()];
                    list = new ArrayList<>();

                    for (int i= 0; i < data.length(); i++)
                    {
                        JSONObject currentItem = data.getJSONObject(i);
                        String sub_id = currentItem.getString("sub_id");
                        String sub_cat_name = currentItem.getString("sub_cat_name");
                        String parent_cat = currentItem.getString("parent_cat");
                        //array_subcategory[i] = sub_id + " " + sub_cat_name ;
                        //subCategoryIDs[i] = sub_id;
                        list.add(new SubCategorySpinner(sub_id,sub_cat_name,parent_cat));

                    }



                }catch (JSONException e){


                }
            }},new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("CreateAccount","The error is : "+ (error.getMessage()));
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequesttwo);
        spinner_subcategory.setOnItemSelectedListener(this);






        mCompany_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final JSONObject object = new JSONObject();
                try {
                    object.put("file",imageView);
                    object.put("icnumber", mCompany_ic_number.getText().toString());
                    object.put("name",mCompany_name.getText().toString());
                    object.put("companyname",mCompany_company_name.getText().toString());
                    object.put("latitude",mCompany_latitude.getText().toString());
                    object.put("longitude",mCompany_longitude.getText().toString());
                    object.put("address",mCompany_address.getText().toString());
                    object.put("companyregistrationnumber",mCompany_registration.getText().toString());
                    object.put("category", selected_cat_ID);
                    object.put("sub_category",selected_sub_ID);
                    object.put("phone",mCompany_phone.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,post_url,object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject object1) {
                                Toast.makeText(CreateAccount.this, object1.toString(), Toast.LENGTH_SHORT).show();

                                builder.setTitle("Server Response");
                                builder.setMessage("Response: " + object1);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mCompany_company_name.setText("");
                                        mCompany_address.setText("");
                                        mCompany_latitude.setText("");
                                        mCompany_longitude.setText("");
                                        mCompany_registration.setText("");
                                        mCompany_phone.setText("");
                                        mCompany_name.setText("");
                                        mCompany_ic_number.setText("");
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("CreateAccount",error.getMessage());
                        }
                }){

                };
                MySingleton.getInstance(CreateAccount.this).addToRequestQueue(request);

            }
        });



    }

    List<SubCategorySpinner> sublist;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (parent.getId())
        {
            case R.id.spinner_category:
                sublist = new ArrayList<>();
                selected_cat_ID = categoryIDs[position];


                for (int i = 0; i <list.size();i++)
                {
                    if (selected_cat_ID.equalsIgnoreCase(list.get(i).getParent_cat()))
                    {
                        sublist.add(list.get(i));
                    }
                }
                array_subcategory = new String[sublist.size()];
                for (int j =0; j < sublist.size(); j++)
                {
                    array_subcategory[j] = sublist.get(j).getSub_name();
                }


                adapter = new ArrayAdapter<>(CreateAccount.this, android.R.layout.simple_spinner_item, array_subcategory);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Log.i("SPIN", "sublist: " + sublist.size());
                spinner_subcategory.setAdapter(adapter);
                break;
            case R.id.spinner_subcategory:
                selected_sub_ID = sublist.get(position).getSub_id();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}

