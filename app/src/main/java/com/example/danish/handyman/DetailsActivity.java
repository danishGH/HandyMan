package com.example.danish.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    String map_company_name,address,name,phone,latitude,longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getIncomingIntent();

        final Button button = findViewById(R.id.button_hire);
        TextView company_name = findViewById(R.id.title_company_name);
        company_name.setText(map_company_name);
        TextView company_address = findViewById(R.id.address_detail);
        company_address.setText(address);
        TextView company_latitude = findViewById(R.id.latitude_detail);
        company_latitude.setText(latitude);
        TextView company_longitude = findViewById(R.id.longitude_detail);
        company_longitude.setText(longitude);
        TextView company_name_name = findViewById(R.id.name_detail);
        company_name_name.setText(name);
        TextView company_phone = findViewById(R.id.phone_detail);
        company_phone.setText(phone);

        final CheckBox checkBox = findViewById(R.id.checkbox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    button.setVisibility(View.VISIBLE);
                }else {
                    button.setVisibility(View.INVISIBLE);
                }

            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentLogin = new Intent(DetailsActivity.this,UserLogin.class);
//                startActivity(intentLogin);
//            }
//        });




    }

    public void getIncomingIntent(){
        if (getIntent().hasExtra("map_company_name") && getIntent().hasExtra("address") && getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")
                && getIntent().hasExtra("name") && getIntent().hasExtra("phone")){
            map_company_name = getIntent().getStringExtra("map_company_name");

            address = getIntent().getStringExtra("address");
            latitude = getIntent().getStringExtra("latitude");
            longitude = getIntent().getStringExtra("longitude");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");

        }
    }

}
