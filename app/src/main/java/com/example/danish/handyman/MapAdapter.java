package com.example.danish.handyman;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {
    List<MapDetailList> mapDetailList;
    Context mContext;
    int mResource;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView mImageView;
        TextView mTextView;
        TextView mTextView2;
        LinearLayout mItemLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            this.mImageView = itemView.findViewById(R.id.map_image);
            this.mTextView = itemView.findViewById(R.id.map_company_name);
            this.mTextView2 = itemView.findViewById(R.id.map_phone);
            this.mItemLayout = itemView.findViewById(R.id.map_item_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MapAdapter(List<MapDetailList> mapDetailList,Context context,int resource) {
        this.mapDetailList = mapDetailList;
        this.mContext = context;
        this.mResource = resource;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MapAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(mResource, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Glide.with(mContext).load(mapDetailList.get(position).getImage()).into(holder.mImageView);
            holder.mTextView.setText(mapDetailList.get(position).getCompany_name());
        holder.mTextView2.setText(mapDetailList.get(position).getPhone());

        holder.mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,DetailsActivity.class);
                intent.putExtra("map_company_name",mapDetailList.get(position).getCompany_name());
                intent.putExtra("address",mapDetailList.get(position).getAddress());
                intent.putExtra("latitude",mapDetailList.get(position).getLatitude());
                intent.putExtra("longitude",mapDetailList.get(position).getLongitude());
                intent.putExtra("name",mapDetailList.get(position).getName());
                intent.putExtra("phone",mapDetailList.get(position).getPhone());
                mContext.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mapDetailList.size();
    }
}

