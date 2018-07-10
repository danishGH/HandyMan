package com.example.danish.handyman;

public class SubCategorySpinner {

    private String sub_id;
    private String sub_name;
    private String parent_cat;


    public SubCategorySpinner(String sub_id, String sub_name) {
        this.sub_id = sub_id;
        this.sub_name = sub_name;
    }
    public SubCategorySpinner(String sub_id, String sub_name, String parent_cat) {
        this.sub_id = sub_id;
        this.sub_name = sub_name;
        this.parent_cat = parent_cat;
    }



    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }
    public String getParent_cat() {
        return parent_cat;
    }

    public void setParent_cat(String parent_cat) {
        this.parent_cat = parent_cat;
    }







}
