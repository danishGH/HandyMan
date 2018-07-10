package com.example.danish.handyman;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder>
{
    List<Category> mCategories;
    Context mContext;
    int mResource;
    private boolean isSubCategory;

    class CatViewHolder extends RecyclerView.ViewHolder
    {
        ImageView mImageView;
        TextView mTextView;
        LinearLayout mItemLayout;

        public CatViewHolder(View itemView)
        {
            super(itemView);
            this.mImageView = itemView.findViewById(R.id.category_image);
            this.mTextView = itemView.findViewById(R.id.category_name);
            this.mItemLayout = itemView.findViewById(R.id.item_layout);

        }
    }
    // constructor
    public CategoryAdapter(List<Category> categories, Context context, boolean isSubCategory, int resource)
    {
        mCategories = categories;
        mContext = context;
        this.mResource  = resource;
        this.isSubCategory = isSubCategory;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoryAdapter.CatViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(mResource, parent, false);

        CatViewHolder vh = new CatViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CatViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mCategories.get(position).getCategory_id());
        holder.mTextView.setText(mCategories.get(position).getCategory_name());
        Glide.with(mContext).load(mCategories.get(position).getImage_url()).into(holder.mImageView);

        holder.mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSubCategory) {


                    Intent intent = new Intent(mContext,MapsActivity.class);
                    intent.putExtra("sub_category_id",mCategories.get(position).getSub_category_id());
                    intent.putExtra("category_id",mCategories.get(position).getCategory_id());
                    mContext.startActivity(intent);
                }
                else {

                    Intent intent = new Intent(mContext,SubCategory.class);
                    intent.putExtra("category_id", mCategories.get(position).getCategory_id());
                    mContext.startActivity(intent);

                }


            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        Log.i("ADAPTER", "SIZE: " + mCategories.size());
        return mCategories.size();
    }



}
