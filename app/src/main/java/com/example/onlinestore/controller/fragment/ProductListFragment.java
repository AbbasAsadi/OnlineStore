package com.example.onlinestore.controller.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;
import com.example.onlinestore.adapter.ProductAdapter;
import com.example.onlinestore.controller.Activity.ProductListActivity;
import com.example.onlinestore.model.products.ProductBody;
import com.example.onlinestore.repository.WoocommerceRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {
    private static final String TAG = "ProductListFragment";
    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.basket_badge)
    TextView basketBadge;
    @BindView(R.id.products_list_recyclerView)
    RecyclerView productsListRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_list)
    TextView emptyList;
    @BindView(R.id.basket_img)
    ImageView basketImg;
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.sort_relative)
    RelativeLayout sortRelative;
    @BindView(R.id.sub_sort_txt)
    TextView subSortTxt;
    @BindView(R.id.filter_relative)
    RelativeLayout filterRelative;
    private int mCategoryId = -1;
    private MutableLiveData<List<ProductBody>> mLiveProductList;
    private List<ProductBody> mProducts;
    private ProductAdapter mProductAdapter;
    private String mListType = "empty";
    public ProductListFragment() {
        // Required empty public constructor
    }


    private ProductListFragment(int id) {
        mCategoryId = id;
    }

    private ProductListFragment(String listType) {
        mListType = listType;
    }

    public static ProductListFragment newInstance(int id) {

        Bundle args = new Bundle();

        ProductListFragment fragment = new ProductListFragment(id);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductListFragment newInstance(String listType) {

        Bundle args = new Bundle();

        ProductListFragment fragment = new ProductListFragment(listType);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.back_toolbar_product_list)
    void onBackClick() {
        getActivity().finish();
    }

    private MutableLiveData<List<ProductBody>> getLiveProductList() {
        if (mLiveProductList == null) {
            mLiveProductList = new MutableLiveData<>();
        }
        return mLiveProductList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProducts = new ArrayList<>();
        getLiveProductList().setValue(mProducts);
        getOrderedProductAsync async = new getOrderedProductAsync();
        async.execute();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, view);
        productsListRecyclerView.setVisibility(View.GONE);
        productsListRecyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL,
                false));

        Observer<List<ProductBody>> observer = productBodies -> {
            if (getLiveProductList().getValue() == null ||
                    getLiveProductList().getValue().isEmpty()) {
                emptyList.setVisibility(View.VISIBLE);
                productsListRecyclerView.setVisibility(View.GONE);
            } else {
                updateProductAdapter();
            }

        };
        getLiveProductList().observe(this, observer);
        Log.d(TAG, "onCreateView: mProducts.size:" + mProducts.size());
        //updateProductAdapter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getLiveProductList().getValue() != null ||
                !getLiveProductList().getValue().isEmpty())
            updateProductAdapter();
    }


    private void updateProductAdapter() {
        if (mProductAdapter == null) {
            mProductAdapter = new
                    ProductAdapter(getLiveProductList().getValue(), getActivity());
        } else {
            mProductAdapter.setListProduct(getLiveProductList().getValue());
            mProductAdapter.notifyDataSetChanged();
        }
        productsListRecyclerView.setAdapter(mProductAdapter);
    }


    class getOrderedProductAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (mListType.equals("empty")) {
                try {
                    mProducts = WoocommerceRepository.getInstance()
                            .getProductByCategoryId(mCategoryId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (mCategoryId == -1) {
                switch (mListType) {
                    case ProductListActivity.NEWEST_PRODUCT:
                        mProducts = WoocommerceRepository.getInstance().getNewestProductList();
                        break;
                    case ProductListActivity.POPULAR_PRODUCT:
                        mProducts = WoocommerceRepository.getInstance().getPopularProductList();
                        break;
                    case ProductListActivity.SPECIAL_SALE:
                        mProducts = WoocommerceRepository.getInstance().getSpecialSaleList();
                        break;
                    case ProductListActivity.TOP_RATED_PRODUCT:
                        mProducts = WoocommerceRepository.getInstance().getTopRatedProductList();
                        break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCategoryId = -1;
            mListType = "empty";
            progressBar.setVisibility(View.GONE);
            getLiveProductList().setValue(mProducts);
            if (mProducts == null || mProducts.isEmpty()) {
                emptyList.setVisibility(View.VISIBLE);
                productsListRecyclerView.setVisibility(View.GONE);
            } else {
                productsListRecyclerView.setVisibility(View.VISIBLE);
                emptyList.setVisibility(View.GONE);
            }
        }
    }
}
