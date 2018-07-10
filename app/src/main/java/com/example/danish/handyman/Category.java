package com.example.danish.handyman;

public class Category {
    private String category_id;
    private String category_name;
    private String image_url;
    private String sub_category_id;

    public Category(String category_id, String category_name, String image_url) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.image_url = image_url;

    }

    public Category(String category_id, String category_name, String image_url,String sub_category_id) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.image_url = image_url;
        this.sub_category_id = sub_category_id;

    }

    public String getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getImage_url() {
        return image_url;
    }
    public String getSub_category_id(){
        return sub_category_id;
    }
}
