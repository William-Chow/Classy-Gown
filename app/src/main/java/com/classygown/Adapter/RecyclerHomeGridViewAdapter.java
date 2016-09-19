package com.classygown.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.classygown.Beans.Products;
import com.classygown.Beans.ShopItemObject;
import com.classygown.HomePage.ProductDescription;
import com.classygown.R;
import com.classygown.Util.Constant;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by William Chow on 1/21/2016.
 */
public class RecyclerHomeGridViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ShopItemObject> shopItemObjects;
    private List<Products> array_recent_products;
    private Context context;
    public Activity activity;

    public RecyclerHomeGridViewAdapter(Activity _activity, Context context, List<ShopItemObject> shopItemObjects, List<Products> array_recent_products) {
        this.activity = _activity;
        this.shopItemObjects = shopItemObjects;
        this.array_recent_products = array_recent_products;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View child_layout_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_product_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(child_layout_view);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        holder.product_title.setText(shopItemObjects.get(position).getProduct_title());
        if(shopItemObjects.get(position).getProduct_price().contains("</del>")){
            holder.rlSplitPrice.setVisibility(View.VISIBLE);
            holder.product_price.setVisibility(View.GONE);
            String[] parts = shopItemObjects.get(position).getProduct_price().split("</del>");
            String strikeText = parts[0];
            String nonStrikeText = parts[1];

            String removeStrikeText = strikeText;
            String newStrikeText = removeStrikeText.replace("<del>", "");

            if(newStrikeText.length() > 50){
                holder.rlSplitPrice.setVisibility(View.GONE);
                holder.rlSplitPrice_NextLine.setVisibility(View.VISIBLE);
                holder.tvPrice_NextLine_Strike_1.setText(Html.fromHtml(newStrikeText));
                holder.tvPrice_NextLine_Strike_1.setPaintFlags(holder.tvPrice_NextLine_Strike_1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvPrice_NextLine_Strike_2.setText("\n" + "" + Html.fromHtml(nonStrikeText));
            }else{
                holder.rlSplitPrice.setVisibility(View.VISIBLE);
                holder.rlSplitPrice_NextLine.setVisibility(View.GONE);
                holder.tvPrice_Strike_1.setText(Html.fromHtml(newStrikeText));
                holder.tvPrice_Strike_1.setPaintFlags(holder.tvPrice_Strike_1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvPrice_Strike_2.setText("  " + Html.fromHtml(nonStrikeText));
            }
        }else{
            holder.rlSplitPrice.setVisibility(View.GONE);
            holder.product_price.setVisibility(View.VISIBLE);
            holder.product_price.setText(Html.fromHtml(shopItemObjects.get(position).getProduct_price()));
        }

        ShopItemObject current = shopItemObjects.get(position);
        Uri uri = Uri.parse(current.getProduct_image());
        Picasso.with(context).load(uri).into(holder.product_image);

        if(shopItemObjects.get(position).getOn_sale().equalsIgnoreCase("true")){
            holder.ivSalesTag.setVisibility(View.VISIBLE);
        }else{
            holder.ivSalesTag.setVisibility(View.GONE);
        }

        holder.llProductItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDescription.class);
                intent.putExtra("array_products", (Serializable) array_recent_products);
                intent.putExtra("position", position);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_slide_out_left);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return this.shopItemObjects.size();
    }
}
