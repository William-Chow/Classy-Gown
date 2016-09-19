package com.classygown.HomePage;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classygown.BaseActivity.BaseActivityWithoutExit;
import com.classygown.Beans.Products;
import com.classygown.Beans.Products_Options;
import com.classygown.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by William Chow on 1/12/16.
 */
public class ProductDescription extends BaseActivityWithoutExit implements View.OnClickListener, ViewPagerEx.OnPageChangeListener {

    private TextView tvHomePageMoreSubHeaderTitle, tvBackOrCancelOrClose, tvAddCart, tvValue, tvTextViewInfo;
    private RelativeLayout rlBack, rlLogout, rlSplitPrice, rlSplitPrice_NextLine, rlAddToCart, rlPlus, rlMinus;
    private ImageView ivSearch, ivShop, ivLogout, ivBack;
    private LinearLayout llMainBar, llSegment;

    private List<Products> products = new ArrayList<Products>();
    private int position = 0, imagePosition = 0, value = 1;

    private SliderLayout mDemoSlider;
    private TextSliderView textSliderView;

    public static long lastClickTime;
    private long mLastClickTime = 0;

    // Zoom
    PhotoViewAttacher mAttacher;

    // Description Content
    private TextView tvDescriptionContentTitle, tvDescriptionContentPrice, tvDescriptionContentSku, tvDescriptionContentMaterial, tvDescriptionContentSizeContent;
    private TextView tvDescriptionContentPrice_Strike_1, tvDescriptionContentPrice_Strike_2;
    public TextView tvPrice_NextLine_Strike_1, tvPrice_NextLine_Strike_2;

    private RadioButton radioSelectedButton, radioButton;

    private Products_Options products_options_obj = null;
    private ArrayList<Products_Options> dynamic_product_optionslist = new ArrayList<Products_Options>();
    private String name = "", tag = "";
    private ArrayList<String> option = new ArrayList<String>();

//    private SegmentedGroup segmentGroup;
//    private View view;

    ArrayList<String> optionResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_classy_gown_home_product_description);

        init();
        setOnClickButtonListener();
        setupLayout();
        populateData();
        imageSlider();
    }

    private void init() {
        optionResult = new ArrayList<String>();
        tvHomePageMoreSubHeaderTitle = (TextView) findViewById(R.id.tvHomePageMoreSubHeaderTitle);
        tvAddCart = (TextView) findViewById(R.id.tvAddCart);
        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        rlLogout = (RelativeLayout) findViewById(R.id.rlLogout);
        tvBackOrCancelOrClose = (TextView) findViewById(R.id.tvBackOrCancelOrClose);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivShop = (ImageView) findViewById(R.id.ivShop);
        ivLogout = (ImageView) findViewById(R.id.ivLogout);
        llMainBar = (LinearLayout) findViewById(R.id.llMainBar);

        tvDescriptionContentTitle = (TextView) findViewById(R.id.tvDescriptionContentTitle);
        tvDescriptionContentPrice = (TextView) findViewById(R.id.tvDescriptionContentPrice);
        tvDescriptionContentSku = (TextView) findViewById(R.id.tvDescriptionContentSku);
        tvDescriptionContentMaterial = (TextView) findViewById(R.id.tvDescriptionContentMaterial);
        tvDescriptionContentSizeContent = (TextView) findViewById(R.id.tvDescriptionContentSizeContent);

        rlSplitPrice = (RelativeLayout) findViewById(R.id.rlSplitPrice);
        tvDescriptionContentPrice_Strike_1 = (TextView) findViewById(R.id.tvDescriptionContentPrice_Strike_1);
        tvDescriptionContentPrice_Strike_2 = (TextView) findViewById(R.id.tvDescriptionContentPrice_Strike_2);

        rlSplitPrice_NextLine = (RelativeLayout) findViewById(R.id.rlSplitPrice_NextLine);
        tvPrice_NextLine_Strike_1 = (TextView) findViewById(R.id.tvPrice_NextLine_Strike_1);
        tvPrice_NextLine_Strike_2 = (TextView) findViewById(R.id.tvPrice_NextLine_Strike_2);

        llSegment = (LinearLayout) findViewById(R.id.llSegment);

        rlAddToCart = (RelativeLayout) findViewById(R.id.rlAddToCart);
        rlPlus = (RelativeLayout) findViewById(R.id.rlPlus);
        rlMinus = (RelativeLayout) findViewById(R.id.rlMinus);
        tvValue = (TextView) findViewById(R.id.tvValue);
    }

    private void setOnClickButtonListener() {
        rlBack.setOnClickListener(this);
        rlLogout.setOnClickListener(this);
        rlAddToCart.setOnClickListener(this);
        rlPlus.setOnClickListener(this);
        rlMinus.setOnClickListener(this);
    }

    private void setupLayout() {
        tvAddCart.setVisibility(View.INVISIBLE);
        ivSearch.setVisibility(View.INVISIBLE);
        ivShop.setVisibility(View.INVISIBLE);
        llMainBar.setVisibility(View.GONE);
        tvBackOrCancelOrClose.setText(getResources().getText(R.string.sub_header_back));
        tvBackOrCancelOrClose.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setBackgroundResource(R.drawable.back_icon);
        rlLogout.setVisibility(View.VISIBLE);
        ivLogout.setVisibility(View.VISIBLE);
        ivLogout.setBackgroundResource(R.drawable.shopping_cart);
        tvValue.setText(String.valueOf(value));
    }

    private void populateData() {
        Intent intentData = ProductDescription.this.getIntent();
        products = (List<Products>) intentData.getSerializableExtra("array_products");
        position = intentData.getIntExtra("position", -1);

        tvHomePageMoreSubHeaderTitle.setText(products.get(position).getTitle());
        tvDescriptionContentTitle.setText(products.get(position).getTitle());
        if (products.get(position).getPrice_html().contains("</del>")) {
            rlSplitPrice.setVisibility(View.VISIBLE);
            tvDescriptionContentPrice.setVisibility(View.GONE);
            chopString(products.get(position).getPrice_html());
        } else {
            rlSplitPrice.setVisibility(View.GONE);
            tvDescriptionContentPrice.setVisibility(View.VISIBLE);
            tvDescriptionContentPrice.setText(Html.fromHtml(products.get(position).getPrice_html()));
        }
        tvDescriptionContentSku.setText(Html.fromHtml(products.get(position).getSku()));
        tvDescriptionContentMaterial.setText(Html.fromHtml(products.get(position).getShort_description()));
        tvDescriptionContentSizeContent.setText(Html.fromHtml(products.get(position).getDescription()));

        // All
        for (int j = 0; j < products.get(position).getProducts_attributes().size(); j++) {
            dynamic_product_optionslist = new ArrayList<Products_Options>();
            for (int k = 0; k < products.get(position).getProducts_attributes().get(j).getProducts_attributes_option().size(); k++) {
                name = products.get(position).getProducts_attributes().get(j).getProducts_attributes_option().get(k).getName();
                option = products.get(position).getProducts_attributes().get(j).getProducts_attributes_option().get(k).getOption();
                products_options_obj = new Products_Options(name, option);
                dynamic_product_optionslist.add(products_options_obj);

                if (k == products.get(position).getProducts_attributes().get(j).getProducts_attributes_option().get(k).getOption().size() - 1) {
                    final LinearLayout ll = new LinearLayout(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 10, 0, 10);
                    ll.setOrientation(LinearLayout.VERTICAL);

                    final TextView tvTextViewInfo = new TextView(this);
                    tvTextViewInfo.setTextSize(getResources().getDimension(R.dimen._8sdp));
                    tvTextViewInfo.setTextColor(Color.parseColor("#000000"));
                    tvTextViewInfo.setText(name);
                    ll.addView(tvTextViewInfo);
                    final HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
                    horizontalScrollView.setVerticalScrollBarEnabled(false);
                    horizontalScrollView.setHorizontalScrollBarEnabled(false);
                    final SegmentedGroup segmentGroup = new SegmentedGroup(this);
                    segmentGroup.setOrientation(LinearLayout.HORIZONTAL);
                    segmentGroup.setTintColor(Color.parseColor("#ff000000"), Color.parseColor("#ffFF0087"));
                    addSegment(segmentGroup, option, ProductDescription.this);

                    segmentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            radioSelectedButton = (RadioButton) findViewById(checkedId);
                            String result = radioSelectedButton.getText().toString();
                            Log.i("William", "Selected Radio Button :: " + result);

                            for (int myResult = 0; myResult < products.get(position).getProducts_attributes().size(); myResult++) {
                                for (int output = 0; output < products.get(position).getProducts_attributes().get(myResult).getProducts_attributes_option().size(); output++) {
                                    for (int single_output = 0; single_output < products.get(position).getProducts_attributes().get(myResult).getProducts_attributes_option().get(output).getOption().size(); single_output++) {
                                        if (result.equalsIgnoreCase(products.get(position).getProducts_attributes().get(myResult).getProducts_attributes_option().get(output).getOption().get(single_output))) {
                                            tag = products.get(position).getProducts_attributes().get(myResult).getProducts_attributes_option().get(output).getName();
                                        }
                                    }
                                }
                            }
                            tvTextViewInfo.setText(tag + " - " + result);
                            String text = ((TextView) tvTextViewInfo).getText().toString();
                            Log.i("William", " New Text :: " + text);
                            for (int removeString = 0; removeString < optionResult.size(); removeString++) {
                                String[] partsRemoveString = optionResult.get(removeString).split("-");
                                String parts1 = partsRemoveString[0];
                                String[] partsOfText = text.split("-");
                                String textPart = partsOfText[0];
                                if (parts1.equalsIgnoreCase(textPart)) {
                                    optionResult.remove(removeString);
                                }
                            }
                            optionResult.add(text);

                        }
                    });
                    horizontalScrollView.addView(segmentGroup);
                    ll.addView(horizontalScrollView);
                    llSegment.addView(ll, layoutParams);
                }
            }
        }
    }

    private void imageSlider() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        mDemoSlider.stopAutoCycle();
        HashMap<String, String> url_maps = new HashMap<String, String>();

        for (int i = 0; i < products.get(position).getImages_products_featured().size(); i++) {
            url_maps.put("", products.get(position).getImages_products_featured().get(i).getImages_products_featured_src());

            for (String name : url_maps.keySet()) {
                imagePosition = i;
                textSliderView = new TextSliderView(ProductDescription.this);
                textSliderView.image(url_maps.get(name)).setScaleType(BaseSliderView.ScaleType.CenterInside);

                showImage(position, imagePosition);

                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", name);

                mDemoSlider.addSlider(textSliderView);
            }
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    private void showImage(final int position, final int imagePosition) {
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Uri imageUri = null;
                for (int image = 0; image < products.get(position).getImages_products_featured().get(imagePosition).getImages_products_featured_src().length(); image++) {
                    imageUri = Uri.parse(products.get(position).getImages_products_featured().get(imagePosition).getImages_products_featured_src());
                }

                Dialog builder = new Dialog(ProductDescription.this);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                });

                ImageView imageView = new ImageView(ProductDescription.this);
                // Zoom
                mAttacher = new PhotoViewAttacher(imageView);
                mAttacher.update();
                Picasso.with(ProductDescription.this).load(imageUri).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                builder.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (isFastDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.rlBack:
                intentBack();
                break;
            case R.id.rlLogout:
                intentCart();
                break;
            case R.id.rlAddToCart:
                Log.i("William", " Arrays :: " + Arrays.asList(optionResult));
//                if (validateInput()) {
//                    // Success
//                } else {
//                    // Fail
//                }
                break;
            case R.id.rlPlus:
                plus();
                break;
            case R.id.rlMinus:
                minus();
                break;
            default:
                break;
        }
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - lastClickTime) < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    private void intentBack() {
        ProductDescription.this.finish();
        ProductDescription.this.overridePendingTransition(R.anim.animation_slide_in_left, R.anim.animation_slide_out_right);
    }

    private void intentCart() {
        Intent intent = new Intent(ProductDescription.this, CartActivity.class);
        startActivity(intent);
        ProductDescription.this.overridePendingTransition(R.anim.animation_slide_up, R.anim.animation_slide_down);
    }

    private void chopString(String chopString) {
        String[] parts = chopString.split("</del>");
        String strikeText = parts[0];
        String nonStrikeText = parts[1];

        String removeStrikeText = strikeText;
        String newStrikeText = removeStrikeText.replace("<del>", "");
        if (newStrikeText.length() > 50) {
            rlSplitPrice.setVisibility(View.GONE);
            rlSplitPrice_NextLine.setVisibility(View.VISIBLE);
            tvPrice_NextLine_Strike_1.setText(Html.fromHtml(newStrikeText));
            tvPrice_NextLine_Strike_1.setPaintFlags(tvPrice_NextLine_Strike_1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvPrice_NextLine_Strike_2.setText("\n" + "" + Html.fromHtml(nonStrikeText));
        } else {
            rlSplitPrice.setVisibility(View.VISIBLE);
            rlSplitPrice_NextLine.setVisibility(View.GONE);
            tvDescriptionContentPrice_Strike_1.setText(Html.fromHtml(newStrikeText));
            tvDescriptionContentPrice_Strike_1.setPaintFlags(tvDescriptionContentPrice_Strike_1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvDescriptionContentPrice_Strike_2.setText("  " + Html.fromHtml(nonStrikeText));
        }
    }

    private void addSegment(SegmentedGroup _segmentGroup, ArrayList _dynamic_list, Activity _activity) {
        for (int list = 0; list < _dynamic_list.size(); list++) {
            radioButton = (RadioButton) _activity.getLayoutInflater().inflate(R.layout.activity_home_page_classy_gown_home_product_description_radio_button_item, null);
            radioButton.setText(_dynamic_list.get(list).toString());
            _segmentGroup.addView(radioButton);
            _segmentGroup.updateBackground();
        }
    }

    private void plus() {
        String number = tvValue.getText().toString();
        int myNumber = Integer.parseInt(number);
        if (myNumber >= 1) {
            value++;
            tvValue.setText(String.valueOf(value));
        }
    }

    private void minus() {
        String number = tvValue.getText().toString();
        int myNumber = Integer.parseInt(number);
        if (myNumber <= 1) {
        } else {
            value--;
            tvValue.setText(String.valueOf(value));
        }
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
