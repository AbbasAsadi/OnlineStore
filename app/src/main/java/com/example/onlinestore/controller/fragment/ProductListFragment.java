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

import com.example.onlinestore.App;
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
    private static MutableLiveData<List<ProductBody>> mLiveProductList;
    @BindView(R.id.toolbar_product_list_fragment)
    TextView mTitleToolbar;
    @BindView(R.id.basket_badge_product_list_fragment)
    TextView mBasketBadge;
    @BindView(R.id.products_list_recyclerView_product_list_fragment)
    RecyclerView mProductsListRecyclerView;
    @BindView(R.id.progress_bar_product_list_fragment)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_list_text_product_list_fragment)
    TextView mEmptyListText;
    @BindView(R.id.basket_img_product_list_fragment)
    ImageView mBasketImg;
    @BindView(R.id.search_img_product_list_fragment)
    ImageView mSearchImg;
    @BindView(R.id.sort_relativelayout_product_list_fragment)
    RelativeLayout mSortRelativeLayout;
    @BindView(R.id.sub_sort_text_product_list_fragment)
    TextView mSubSortText;
    @BindView(R.id.filter_relativelayout_product_list_fragment)
    RelativeLayout mFilterRelativeLayout;
    private int mCategoryId = -1;
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

    static MutableLiveData<List<ProductBody>> getLiveProductList() {
        if (mLiveProductList == null) {
            mLiveProductList = new MutableLiveData<>();
        }
        return mLiveProductList;
    }

    @OnClick(R.id.back_toolbar_product_list)
    void onBackClick() {
        getActivity().finish();
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

        SortProductDialogFragment.radioChecked = 4;
        sortProductDialog();
        //subSortTextFunction();

        String badge = App
                .getInstance()
                .getPersianNumber(WoocommerceRepository.getInstance().getBadgeNumber())
                + " تومان";
        mBasketBadge.setText(badge);

        mProductsListRecyclerView.setVisibility(View.GONE);
        mProductsListRecyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL,
                false));
        setSortText();

        Observer<List<ProductBody>> observer = productBodies -> {
            if (productBodies == null || productBodies.isEmpty()) {
                mEmptyListText.setVisibility(View.VISIBLE);
                mProductsListRecyclerView.setVisibility(View.GONE);
            } else {
                setSortText();
                updateProductAdapter();
            }

        };
        getLiveProductList().observe(this, observer);
        Log.d(TAG, "onCreateView: mProducts.size:" + mProducts.size());

        mBasketImg.setOnClickListener(view1 ->
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.product_list_activity, ShoppingBasketFragment.newInstance())
                        .addToBackStack(null)
                        .commit());
        return view;
    }

    private void setSortText() {
        switch (SortProductDialogFragment.radioChecked) {
            case 0:
                mSubSortText
                        .setText(getResources().getString(R.string.price_asc));
                break;
            case 1:
                mSubSortText
                        .setText(getResources().getString(R.string.price_desc));
                break;
            case 2:
                mSubSortText
                        .setText(getResources().getString(R.string.most_visiting));
                break;
            case 3:
                mSubSortText
                        .setText(getResources().getString(R.string.most_rating));
                break;
            case 4:
                mSubSortText
                        .setText(getResources().getString(R.string.most_newest));
                break;
        }
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
        mProductsListRecyclerView.setAdapter(mProductAdapter);
    }

    private void sortProductDialog() {
        mSortRelativeLayout.setOnClickListener(view ->
                SortProductDialogFragment.newInstance()
                        .show(getFragmentManager(), "sortProduct"));
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
            mProgressBar.setVisibility(View.GONE);
            getLiveProductList().setValue(mProducts);
            if (mProducts == null || mProducts.isEmpty()) {
                mEmptyListText.setVisibility(View.VISIBLE);
                mProductsListRecyclerView.setVisibility(View.GONE);
            } else {
                mProductsListRecyclerView.setVisibility(View.VISIBLE);
                mEmptyListText.setVisibility(View.GONE);
            }
        }
    }
}
