package com.classygown.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classygown.R;

/**
 * Created by William Chow on 1/26/2016.
 */
public class RecyclerViewShopHolders extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public CardView cvShop;
    public LinearLayout llShopItem;
    public TextView shop_title;
    public TextView shop_price;
    public RelativeLayout rlSplitShopPrice;
    public TextView tvShopPrice_Strike_1;
    public TextView tvShopPrice_Strike_2;
    public RelativeLayout rlSplitShopPrice_NextLine;
    public TextView tvShopPrice_NextLine_Strike_1;
    public TextView tvShopPrice_NextLine_Strike_2;
    public ImageView shop_image;
    public ImageView ivShopSalesTag;

    public RecyclerViewShopHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        cvShop = (CardView) itemView.findViewById(R.id.card_view_shop);
        llShopItem = (LinearLayout) itemView.findViewById(R.id.llShopItem);
        shop_title = (TextView) itemView.findViewById(R.id.tvShopTitle);
        shop_price = (TextView) itemView.findViewById(R.id.tvShopPrice);
        rlSplitShopPrice = (RelativeLayout) itemView.findViewById(R.id.rlSplitShopPrice);
        tvShopPrice_Strike_1 = (TextView) itemView.findViewById(R.id.tvShopPrice_Strike_1);
        tvShopPrice_Strike_2 = (TextView) itemView.findViewById(R.id.tvShopPrice_Strike_2);
        rlSplitShopPrice_NextLine = (RelativeLayout) itemView.findViewById(R.id.rlSplitShopPrice_NextLine);
        tvShopPrice_NextLine_Strike_1 = (TextView) itemView.findViewById(R.id.tvShopPrice_NextLine_Strike_1);
        tvShopPrice_NextLine_Strike_2 = (TextView) itemView.findViewById(R.id.tvShopPrice_NextLine_Strike_2);
        shop_image = (ImageView) itemView.findViewById(R.id.ivShopImage);
        ivShopSalesTag = (ImageView) itemView.findViewById(R.id.ivShopSalesTag);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
//        Toast.makeText(view.getContext(), "Clicked Position :: " + position, Toast.LENGTH_LONG).show();
    }
}
