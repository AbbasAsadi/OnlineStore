package com.example.onlinestore.controller.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;
import com.example.onlinestore.adapter.CategoryHorizontalAdapter;
import com.example.onlinestore.adapter.ProductAdapterHorizontal;
import com.example.onlinestore.controller.Activity.ProductListActivity;
import com.example.onlinestore.repository.WoocommerceRepository;
import com.example.onlinestore.utils.sliderr.MainSliderAdapter;
import com.example.onlinestore.utils.sliderr.PicassoImageLoadingService;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.com.bannerslider.Slider;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    @BindView(R.id.slider_main_fragment)
    Slider mSlider;
    @BindView(R.id.category_recyclerview)
    RecyclerView mRecyclerViewCategory;
    @BindView(R.id.amazing_recyclerview)
    RecyclerView mRecyclerViewAmazingSuggest;
    @BindView(R.id.newest_products_recyclerview_main_fragment)
    RecyclerView mRecyclerViewNewestProduct;
    @BindView(R.id.popular_products_recyclerview_main_fragment)
    RecyclerView mRecyclerViewPopularProduct;
    @BindView(R.id.fullList_text_popular_product_main_fragment)
    TextView mFullListTopRated;
    @BindView(R.id.full_list_newest_product_text_main_fragment)
    TextView mFullListNewest;

    //    @BindView(R.id.)
//    private RecyclerView mRecyclerViewTopRatedProduct;
    private ProductAdapterHorizontal mProductAdapterAmazingSuggest;
    private ProductAdapterHorizontal mProductAdapterNewestProduct;
    private ProductAdapterHorizontal mProductAdapterPopularProduct;
    private CategoryHorizontalAdapter mCategoryAdapter;
    private WoocommerceRepository mRepository;
    private Context mContext;

    public MainFragment() {
        // Required empty public constructor
    }

    private MainFragment(Context context) {
        mContext = context;
    }

    public static MainFragment newInstance(Context context) {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment(context);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slider.init(new PicassoImageLoadingService());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        mRepository = WoocommerceRepository.getInstance();

        mSlider.setAdapter(new MainSliderAdapter());
        mRecyclerViewCategory.setLayoutManager
                (new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        mRecyclerViewCategory.setVisibility(View.VISIBLE);
        updateCategoryAdapter();

        updateAmazingProductAdapter();
        mRecyclerViewAmazingSuggest.setLayoutManager(new LinearLayoutManager
                (mContext, RecyclerView.HORIZONTAL, true));

        updateNewestProductAdapter();
        mRecyclerViewNewestProduct.setLayoutManager
                (new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));

        updatePopularProductAdapter();
        mRecyclerViewPopularProduct.setLayoutManager
                (new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));

        mFullListTopRated.setOnClickListener(view1 ->
                mContext.startActivity(ProductListActivity
                        .newIntent(mContext, ProductListActivity.TOP_RATED_PRODUCT)));

        mFullListNewest.setOnClickListener(view2 ->
                mContext.startActivity(ProductListActivity
                        .newIntent(mContext, ProductListActivity.NEWEST_PRODUCT)));

        return rootView;
    }

    private void updatePopularProductAdapter() {
        if (mProductAdapterPopularProduct == null) {
            mProductAdapterPopularProduct = new ProductAdapterHorizontal(
                    mRepository.getTopRatedProductList(), mContext);
        } else {
            mProductAdapterPopularProduct.setListProduct(mRepository.getTopRatedProductList());
            mProductAdapterPopularProduct.notifyDataSetChanged();
        }
        mRecyclerViewPopularProduct
                .setAdapter(mProductAdapterPopularProduct);
    }

    private void updateNewestProductAdapter() {
        if (mProductAdapterNewestProduct == null) {
            mProductAdapterNewestProduct = new ProductAdapterHorizontal
                    (mRepository.getNewestProductList(), mContext);
        } else {
            mProductAdapterNewestProduct.setListProduct(mRepository.getNewestProductList());
            mProductAdapterNewestProduct.notifyDataSetChanged();
        }
        mRecyclerViewNewestProduct
                .setAdapter(mProductAdapterNewestProduct);
    }

    private void updateAmazingProductAdapter() {
        if (mProductAdapterAmazingSuggest == null)
            mProductAdapterAmazingSuggest = new ProductAdapterHorizontal
                    (mRepository.getAmazingProducts(), mContext);
        else {
            mProductAdapterAmazingSuggest.setListProduct(mRepository.getAmazingProducts());
            mProductAdapterAmazingSuggest.notifyDataSetChanged();
        }
        mRecyclerViewAmazingSuggest
                .setAdapter(mProductAdapterAmazingSuggest);
    }

    private void updateCategoryAdapter() {
        if (mCategoryAdapter == null) {
            mCategoryAdapter = new
                    CategoryHorizontalAdapter(mRepository.getParentCategoryList(), mContext);
        } else {
            mCategoryAdapter.setCategoryList(mRepository.getParentCategoryList());
            mCategoryAdapter.notifyDataSetChanged();
        }
        mRecyclerViewCategory.setAdapter(mCategoryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCategoryAdapter();
        updateAmazingProductAdapter();
        updateNewestProductAdapter();
        updatePopularProductAdapter();
    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu , menu);
    }*/
}
