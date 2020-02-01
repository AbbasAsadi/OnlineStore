package com.example.onlinestore.controller.fragment;


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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;
import com.example.onlinestore.adapter.ProductAdapterHorizontal;
import com.example.onlinestore.adapter.ProductCategoryAdapter;
import com.example.onlinestore.model.products.ProductBody;
import com.example.onlinestore.network.WoocommerceRepository;
import com.example.onlinestore.utils.sliderr.PicassoImageLoadingService;
import com.google.android.material.card.MaterialCardView;

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
    private ProductBody mProduct;
    private boolean isAmazingSuggestion = false;
    private ProductCategoryAdapter mCategoryAdapter;
    private List<ProductBody> mRelatedProduct;
    private ProductAdapterHorizontal mRelatedProductAdapter;

    public DetailProductFragment() {
        // Required empty public constructor
    }

    public static DetailProductFragment newInstance() {

        Bundle args = new Bundle();

        DetailProductFragment fragment = new DetailProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.back_toolbar)
    void onBackClick() {
        getActivity().finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = WoocommerceRepository.getInstance();
        mProductId = mRepository.getClickedProductId();
        mProduct = mRepository.getProductById(mProductId);
        Slider.init(new PicassoImageLoadingService());
        mRelatedProduct = new ArrayList<>();
        for (int id : mProduct.getRelatedIds()) {
            mRelatedProduct.add(mRepository.getProductById(id));
        }
        Toast.makeText(getActivity(), "id:" + mProduct.getId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        ButterKnife.bind(this, view);

        setSliderAdapter();

        titleProduct.setText(mProduct.getName());

        setAmazingSuggestionStatus();

        regularPrice.setText(mProduct.getRegularPrice());
        salePrice.setText(mProduct.getSalePrice());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (shortDescriptionProduct != null) {
                shortDescriptionProduct.setText(Html.fromHtml(mProduct.getShortDescription(), Html.FROM_HTML_MODE_COMPACT));
            }
            descriptionProduct.setText(Html.fromHtml(mProduct.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            if (shortDescriptionProduct != null)
                shortDescriptionProduct.setText(Html.fromHtml(mProduct.getShortDescription()));
            descriptionProduct.setText(Html.fromHtml(mProduct.getDescription()));
        }

        categoryProductRecyclerView
                .setLayoutManager(new LinearLayoutManager
                        (getActivity(), RecyclerView.HORIZONTAL, true));
        updateCategoryAdapter();
        categoryProductRecyclerView
                .setAdapter(mCategoryAdapter);

        relatedProductRecyclerView
                .setLayoutManager(new LinearLayoutManager(
                        getActivity(), RecyclerView.HORIZONTAL, true));
        updateRelatedProductAdapter();
        relatedProductRecyclerView.setAdapter(mRelatedProductAdapter);

        userComments.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_product_Activity , CommentFragment.newInstance())
                    .addToBackStack(null)
                    .commit();

        });

        return view;
    }

    private void updateRelatedProductAdapter() {
        if (mRelatedProductAdapter == null) {
            mRelatedProductAdapter = new ProductAdapterHorizontal(mRelatedProduct, getActivity());
        } else {
            mRelatedProductAdapter.setListProduct(mRelatedProduct);
            mRelatedProductAdapter.notifyDataSetChanged();
        }
    }

    private void updateCategoryAdapter() {
        if (mCategoryAdapter == null) {
            mCategoryAdapter = new ProductCategoryAdapter(mProduct.getCategories(), getContext());

        } else {
            mCategoryAdapter.setListCategories(mProduct.getCategories());
            mCategoryAdapter.notifyDataSetChanged();
        }
    }

    private void setAmazingSuggestionStatus() {
        if (!mProduct.getTags().isEmpty()) {
            if (mProduct.getTags().get(0).getId() == DISCOUNT_TAG) {
                isAmazingSuggestion = true;
                amazingSuggestionLabel.setVisibility(View.VISIBLE);
                amazingSuggestionLogo.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setSliderAdapter() {
        mSlider.setAdapter(new SliderAdapter() {
            @Override
            public int getItemCount() {
                return mProduct.getImages().size();
            }

            @Override
            public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
                imageSlideViewHolder.bindImageSlide(mProduct.getImages().get(position).getSrc());
                imageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCategoryAdapter();
        updateRelatedProductAdapter();
    }
}
