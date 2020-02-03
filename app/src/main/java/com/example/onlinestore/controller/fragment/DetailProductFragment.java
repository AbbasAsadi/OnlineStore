package com.example.onlinestore.controller.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.App;
import com.example.onlinestore.R;
import com.example.onlinestore.adapter.ProductAdapterHorizontal;
import com.example.onlinestore.adapter.ProductCategoryAdapter;
import com.example.onlinestore.model.products.ProductBody;
import com.example.onlinestore.repository.WoocommerceRepository;
import com.example.onlinestore.utils.sliderr.PicassoImageLoadingService;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProductFragment extends Fragment {
    private static final String TAG = "DetailProductFragment";
    private static final int DISCOUNT_TAG = 46;
    @BindView(R.id.toolbar_detail_product)
    TextView toolbar_detail_product;
    @BindView(R.id.basket_img)
    ImageView basketImg;
    @BindView(R.id.slider_detail_product)
    Slider mSlider;
    @BindView(R.id.amazing_suggestion_label_detail_product)
    ImageView amazingSuggestionLabel;
    @BindView(R.id.amazing_suggestion_logo)
    ImageView amazingSuggestionLogo;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.parent_fragment_product)
    RelativeLayout parentFragmentProduct;
    @BindView(R.id.title_product)
    TextView titleProduct;
    @BindView(R.id.short_description)
    TextView shortDescriptionProduct;
    @BindView(R.id.description_txt)
    TextView descriptionProduct;
    @BindView(R.id.price_regular)
    TextView regularPrice;
    @BindView(R.id.sale_price)
    TextView salePrice;
    @BindView(R.id.category_product_fragment_recyclerView)
    RecyclerView categoryProductRecyclerView;
    @BindView(R.id.related_product_recyclerView)
    RecyclerView relatedProductRecyclerView;
    @BindView(R.id.add_to_basket)
    LinearLayout addToBasket;
    @BindView(R.id.basket_badge)
    TextView basketBadge;
    @BindView(R.id.share_product)
    ImageView shareProduct;
    @BindView(R.id.favorite_product)
    ImageView favoriteProduct;
    @BindView(R.id.user_comments)
    RelativeLayout userComments;
    @BindView(R.id.related_product_txt)
    TextView relatedProductTxt;
    @BindView(R.id.description_card)
    MaterialCardView descriptionCardView;
    private int mProductId;
    private WoocommerceRepository mRepository;
    private ProductCategoryAdapter mCategoryAdapter;
    private ProductAdapterHorizontal mRelatedProductAdapter;
    private ProductBody mProduct;
    private MutableLiveData<ProductBody> mLiveProduct;
    private MutableLiveData<List<ProductBody>> mLiveRelatedProduct;

    public DetailProductFragment() {
        // Required empty public constructor
    }

    private DetailProductFragment(int productId) {
        mProductId = productId;
    }

    public static DetailProductFragment newInstance(int productId) {

        Bundle args = new Bundle();

        DetailProductFragment fragment = new DetailProductFragment(productId);
        fragment.setArguments(args);
        return fragment;
    }

    private MutableLiveData<List<ProductBody>> getLiveRelatedProduct() {
        if (mLiveRelatedProduct == null) {
            mLiveRelatedProduct = new MutableLiveData<>();
        }
        return mLiveRelatedProduct;
    }

    private MutableLiveData<ProductBody> getLiveProduct() {
        if (mLiveProduct == null) {
            mLiveProduct = new MutableLiveData<>();
        }
        return mLiveProduct;
    }

    @OnClick(R.id.back_toolbar)
    void onBackClick() {
        getActivity().finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = WoocommerceRepository.getInstance();
        Slider.init(new PicassoImageLoadingService());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_product, container, false);
        ButterKnife.bind(this, rootView);
        GetProductAsync async = new GetProductAsync();
        async.execute();
        progressBar.setVisibility(View.VISIBLE);
        parentFragmentProduct.setVisibility(View.GONE);
        Observer<ProductBody> productObserver = productBody -> {
            if (getLiveProduct().getValue() != null) {

                setSliderAdapter();

                //Toast.makeText(getActivity(), "id:" + getLiveProduct().getValue().getId(), Toast.LENGTH_SHORT).show();

                titleProduct.setText(getLiveProduct().getValue().getName());

                setAmazingSuggestionStatus();

                setRegularPrice();

                setPrice();

                setShortDescription();

                categoryProductRecyclerView
                        .setLayoutManager(new LinearLayoutManager
                                (getActivity(), RecyclerView.HORIZONTAL, true));
                updateCategoryAdapter();


                relatedProductRecyclerView
                        .setLayoutManager(new LinearLayoutManager(
                                getActivity(), RecyclerView.HORIZONTAL, true));

                handleClickOnCommentButton();

                handleShareProductLink();
            }
        };
        getLiveProduct().observe(this, productObserver);

        Observer<List<ProductBody>> relatedProductListObserver = productBodies ->
                updateRelatedProductAdapter();

        getLiveRelatedProduct().observe(this, relatedProductListObserver);
        return rootView;
    }

    private void handleShareProductLink() {
        if (getLiveProduct().getValue() != null) {
            String shareMessage = getLiveProduct().getValue().getName() + "\n" +
                    "را در " + getString(R.string.digikala_txt) + " ببین" + "\n" +
                    getLiveProduct().getValue().getPermalink();
            shareProduct.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,
                        getResources().getString(R.string.share_via)));
            });
        }
    }

    private void handleClickOnCommentButton() {
        userComments.setOnClickListener(view1 ->
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_product_Activity, CommentFragment.newInstance(mProductId))
                        .addToBackStack(null)
                        .commit());
    }


    private void setShortDescription() {
        if (getLiveProduct().getValue() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (shortDescriptionProduct != null) {
                    shortDescriptionProduct.setText(Html.fromHtml(getLiveProduct().getValue().getShortDescription(), Html.FROM_HTML_MODE_COMPACT));
                }
                descriptionProduct.setText(Html.fromHtml(getLiveProduct().getValue().getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                if (shortDescriptionProduct != null)
                    shortDescriptionProduct.setText(Html.fromHtml(getLiveProduct().getValue().getShortDescription()));
                descriptionProduct.setText(Html.fromHtml(getLiveProduct().getValue().getDescription()));
            }
        }
    }

    private void setPrice() {
        if (getLiveProduct().getValue() != null) {
            String price = App.getInstance()
                    .getPersianNumber(Double.parseDouble(getLiveProduct().getValue().getPrice()))
                    + " تومان";
            salePrice.setText(price);
        }
    }

    private void setRegularPrice() {
        if (getLiveProduct().getValue() != null) {
            if (!getLiveProduct().getValue().getRegularPrice().equals("")) {
                String regularPriceStr = App.getInstance()
                        .getPersianNumber(Double
                                .parseDouble(getLiveProduct().getValue().getRegularPrice()))
                        + " تومان";

                regularPrice.setText(regularPriceStr);
                regularPrice.setVisibility(View.VISIBLE);
            } else {
                regularPrice.setVisibility(View.INVISIBLE);
                regularPrice.setText("");
            }
        }
    }

    private void updateRelatedProductAdapter() {
        if (mRelatedProductAdapter == null) {
            mRelatedProductAdapter = new
                    ProductAdapterHorizontal(getLiveRelatedProduct().getValue(),
                    getActivity());
        } else {
            mRelatedProductAdapter.setListProduct(getLiveRelatedProduct().getValue());
            mRelatedProductAdapter.notifyDataSetChanged();
        }
        relatedProductRecyclerView.setAdapter(mRelatedProductAdapter);

    }

    private void updateCategoryAdapter() {
        if (getLiveProduct().getValue() != null) {
            if (mCategoryAdapter == null) {
                mCategoryAdapter = new ProductCategoryAdapter(getLiveProduct().getValue().getCategories(), getContext());

            } else {
                mCategoryAdapter.setListCategories(getLiveProduct().getValue().getCategories());
                mCategoryAdapter.notifyDataSetChanged();
            }
            categoryProductRecyclerView
                    .setAdapter(mCategoryAdapter);
        }
    }

    private void setAmazingSuggestionStatus() {
        if (getLiveProduct().getValue() != null) {
            if (!getLiveProduct().getValue().getTags().isEmpty()) {
                if (getLiveProduct().getValue().getTags().get(0).getId() == DISCOUNT_TAG) {
                    amazingSuggestionLabel.setVisibility(View.VISIBLE);
                    amazingSuggestionLogo.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setSliderAdapter() {
        if (getLiveProduct().getValue() != null) {
            mSlider.setAdapter(new SliderAdapter() {
                @Override
                public int getItemCount() {
                    return getLiveProduct().getValue().getImages().size();
                }

                @Override
                public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
                    imageSlideViewHolder.bindImageSlide(getLiveProduct().getValue().getImages().get(position).getSrc());
                    imageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    progressBar.setVisibility(View.GONE);

                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setSliderAdapter();
        updateCategoryAdapter();
        updateRelatedProductAdapter();
    }

    class GetProductAsync extends AsyncTask<Void, Void, Void> {
        List<ProductBody> relatedProduct = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mProduct = mRepository.getProductById(mProductId);
                if (mProduct.getRelatedIds() != null) {
                    for (int id : mProduct.getRelatedIds()) {
                        relatedProduct.add(mRepository.getProductById(id));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getLiveRelatedProduct().setValue(relatedProduct);
            getLiveProduct().setValue(mProduct);
            progressBar.setVisibility(View.GONE);
            parentFragmentProduct.setVisibility(View.VISIBLE);
        }
    }
}
