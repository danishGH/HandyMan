package com.example.danish.handyman;

public class MapDetailList {

    private String company_name;
    private String phone;
    private String name;
    private String image;
    private String address;
    private double latitude;
    private double longitude;


    public MapDetailList(String company_name,String phone,String name,String image,double latitude,double longitude,String address){

        this.company_name = company_name;
        this.phone = phone;
        this.name = name;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getCompany_name(){
        return company_name;
    }
    public String getPhone(){
        return phone;
    }
    public String getName(){
        return name;
    }
    public String getImage(){
        return image;
    }
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
    public String getAddress(){
        return address;
    }




}
