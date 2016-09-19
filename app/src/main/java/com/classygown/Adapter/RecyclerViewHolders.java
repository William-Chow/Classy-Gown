package com.classygown.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classygown.HomePage.ProductDescription;
import com.classygown.R;

import java.io.Serializable;

/**
 * Created by William Chow on 1/22/2016.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CardView cv;
    public LinearLayout llProductItem;
    public TextView product_title;
    public TextView product_price;
    public RelativeLayout rlSplitPrice;
    public TextView tvPrice_Strike_1;
    public TextView tvPrice_Strike_2;
    public RelativeLayout rlSplitPrice_NextLine;
    public TextView tvPrice_NextLine_Strike_1;
    public TextView tvPrice_NextLine_Strike_2;
    public ImageView product_image;
    public ImageView ivSalesTag;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        cv = (CardView) itemView.findViewById(R.id.card_view);
        llProductItem = (LinearLayout) itemView.findViewById(R.id.llProductItem);
        product_title = (TextView) itemView.findViewById(R.id.tvProductTitle);
        product_price = (TextView) itemView.findViewById(R.id.tvProductPrice);
        rlSplitPrice = (RelativeLayout) itemView.findViewById(R.id.rlSplitPrice);
        tvPrice_Strike_1 = (TextView) itemView.findViewById(R.id.tvPrice_Strike_1);
        tvPrice_Strike_2 = (TextView) itemView.findViewById(R.id.tvPrice_Strike_2);
        rlSplitPrice_NextLine = (RelativeLayout) itemView.findViewById(R.id.rlSplitPrice_NextLine);
        tvPrice_NextLine_Strike_1 = (TextView) itemView.findViewById(R.id.tvPrice_NextLine_Strike_1);
        tvPrice_NextLine_Strike_2 = (TextView) itemView.findViewById(R.id.tvPrice_NextLine_Strike_2);
        product_image = (ImageView) itemView.findViewById(R.id.ivProductImage);
        ivSalesTag = (ImageView) itemView.findViewById(R.id.ivSalesTag);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
//        Toast.makeText(view.getContext(), "Clicked Position :: " + position, Toast.LENGTH_LONG).show();
    }
}
