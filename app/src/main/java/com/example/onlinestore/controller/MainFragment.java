package com.example.onlinestore.controller;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;
import com.example.onlinestore.adapter.CategoryAdapter;
import com.example.onlinestore.adapter.ProductAdapterHorizontal;
import com.example.onlinestore.network.WoocommerceRepository;
import com.example.onlinestore.utils.utils.sliderr.MainSliderAdapter;
import com.example.onlinestore.utils.utils.sliderr.PicassoImageLoadingService;

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
    @BindView(R.id.amzing_recycleview)
    RecyclerView mRecyclerViewAmazingSuggest;
    @BindView(R.id.newest_products_recyclerview)
    RecyclerView mRecyclerViewNewestProduct;
    @BindView(R.id.popular_products_recyclerview)
    RecyclerView mRecyclerViewPopularProduct;
//    @BindView(R.id.)
//    private RecyclerView mRecyclerViewTopRatedProduct;
    private ProductAdapterHorizontal mProductAdapterAmazingSuggest;
    private ProductAdapterHorizontal mProductAdapterNewestProduct;
    private ProductAdapterHorizontal mProductAdapterPopularProduct;
    private CategoryAdapter mCategoryAdapter;
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
        mRepository = WoocommerceRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this , view);

        mSlider.setAdapter(new MainSliderAdapter());

        mCategoryAdapter = new CategoryAdapter(mRepository.getCategoriesList() , mContext);
        mRecyclerViewCategory.setLayoutManager
                (new LinearLayoutManager(mContext , RecyclerView.HORIZONTAL , false));
        mRecyclerViewCategory
                .setAdapter(mCategoryAdapter);

        mProductAdapterAmazingSuggest = new ProductAdapterHorizontal
                (mRepository.getRatedProductList() , mContext );
        mRecyclerViewAmazingSuggest.setLayoutManager(new LinearLayoutManager
                (mContext , RecyclerView.HORIZONTAL , true));
        mRecyclerViewAmazingSuggest
                .setAdapter(mProductAdapterAmazingSuggest);

        mProductAdapterNewestProduct = new ProductAdapterHorizontal
                (mRepository.getNewestProductList() , mContext);
        mRecyclerViewNewestProduct.setLayoutManager
                (new LinearLayoutManager(mContext , RecyclerView.HORIZONTAL , false));
        mRecyclerViewNewestProduct
                .setAdapter(mProductAdapterNewestProduct);

        mProductAdapterPopularProduct = new ProductAdapterHorizontal(
                mRepository.getPopularProductList() , mContext);
        mRecyclerViewPopularProduct.setLayoutManager
                (new LinearLayoutManager(mContext , RecyclerView.HORIZONTAL , false));
        mRecyclerViewPopularProduct
                .setAdapter(mProductAdapterPopularProduct);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
