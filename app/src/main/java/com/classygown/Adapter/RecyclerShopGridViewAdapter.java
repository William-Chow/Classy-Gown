package com.classygown.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classygown.Beans.Products;
import com.classygown.Beans.ShopObject;
import com.classygown.HomePage.ProductDescription;
import com.classygown.Interface.OnLoadMoreListener;
import com.classygown.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by William Chow on 1/26/2016.
 */
public class RecyclerShopGridViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShopObject> shopObjects;
    private List<Products> array_shop_products;
    private Context context;
    public Activity activity;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
//    private int visibleThreshold = 5;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;

    private RecyclerView rViewGrid;

    public RecyclerShopGridViewAdapter(Activity _activity, Context _context, List<ShopObject> _shopObjects, List<Products> _array_shop_products, RecyclerView rViewGrid) {
        this.activity = _activity;
        this.context = _context;
        this.shopObjects = _shopObjects;
        this.array_shop_products = _array_shop_products;
        this.rViewGrid = rViewGrid;

        final GridLayoutManager gm = (GridLayoutManager) rViewGrid.getLayoutManager();
        rViewGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = gm.getItemCount();
                lastVisibleItem = gm.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    static class RecyclerViewShopHolders extends RecyclerView.ViewHolder {
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
    }

    @Override
    public int getItemViewType(int position) {
        return this.shopObjects.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_shop_list, parent, false);
            return new RecyclerViewShopHolders(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_shop_progress, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerViewShopHolders) {
            RecyclerViewShopHolders holders = (RecyclerViewShopHolders) holder;
            holders.shop_title.setText(shopObjects.get(position).getShop_title());
            if (shopObjects.get(position).getShop_price().contains("</del>")) {
                holders.rlSplitShopPrice.setVisibility(View.VISIBLE);
                holders.shop_price.setVisibility(View.GONE);
                String[] parts = shopObjects.get(position).getShop_price().split("</del>");
                String strikeText = parts[0];
                String nonStrikeText = parts[1];

                String removeStrikeText = strikeText;
                String newStrikeText = removeStrikeText.replace("<del>", "");

                if (newStrikeText.length() > 50) {
                    holders.rlSplitShopPrice.setVisibility(View.GONE);
                    holders.rlSplitShopPrice_NextLine.setVisibility(View.VISIBLE);
                    holders.tvShopPrice_NextLine_Strike_1.setText(Html.fromHtml(newStrikeText));
                    holders.tvShopPrice_NextLine_Strike_1.setPaintFlags(holders.tvShopPrice_NextLine_Strike_1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holders.tvShopPrice_NextLine_Strike_2.setText("\n" + "" + Html.fromHtml(nonStrikeText));
                } else {
                    holders.rlSplitShopPrice.setVisibility(View.VISIBLE);
                    holders.rlSplitShopPrice_NextLine.setVisibility(View.GONE);
                    holders.tvShopPrice_Strike_1.setText(Html.fromHtml(newStrikeText));
                    holders.tvShopPrice_Strike_1.setPaintFlags(holders.tvShopPrice_Strike_1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holders.tvShopPrice_Strike_2.setText("  " + Html.fromHtml(nonStrikeText));
                }
            } else {
                holders.rlSplitShopPrice.setVisibility(View.GONE);
                holders.shop_price.setVisibility(View.VISIBLE);
                holders.shop_price.setText(Html.fromHtml(shopObjects.get(position).getShop_price()));
            }

            ShopObject current = shopObjects.get(position);
            Uri uri = Uri.parse(current.getShop_image());
            Picasso.with(context).load(uri).into(holders.shop_image);

            if (shopObjects.get(position).getShop_on_sale().equalsIgnoreCase("true")) {
                holders.ivShopSalesTag.setVisibility(View.VISIBLE);
            } else {
                holders.ivShopSalesTag.setVisibility(View.GONE);
            }

            holders.llShopItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ProductDescription.class);
                    intent.putExtra("array_products", (Serializable) array_shop_products);
                    intent.putExtra("position", position);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_slide_out_left);
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return this.shopObjects == null ? 0 : this.shopObjects.size();
    }

}
