package com.example.onlinestore.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.onlinestore.R;
import com.example.onlinestore.model.products.ProductBody;
import com.example.onlinestore.network.RetrofitInstance;
import com.example.onlinestore.network.interfaces.WoocommerceService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.onlinestore.repository.WoocommerceRepository.BASE_URL;
import static com.example.onlinestore.repository.WoocommerceRepository.CONSUMER_KEY;
import static com.example.onlinestore.repository.WoocommerceRepository.CONSUMER_SECRET;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortProductDialogFragment extends DialogFragment implements View.OnClickListener {
    public static int radioChecked = 4;

    @BindView(R.id.radio_rating)
    RadioButton radioRating;
    @BindView(R.id.radio_visiting)
    RadioButton radioVisiting;
    @BindView(R.id.radio_newest)
    RadioButton radioNewest;
    @BindView(R.id.radio_price_desc)
    RadioButton radioDesc;
    @BindView(R.id.radio_price_asc)
    RadioButton radioAsc;

    public SortProductDialogFragment() {
        // Required empty public constructor
    }

    public static SortProductDialogFragment newInstance() {

        Bundle args = new Bundle();

        SortProductDialogFragment fragment = new SortProductDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort_product_dialog, container, false);
        ButterKnife.bind(this, view);
        switch (radioChecked) {
            case 0: {
                radioAsc.setChecked(true);
                break;
            }
            case 1: {
                radioDesc.setChecked(true);
                break;
            }
            case 2: {
                radioVisiting.setChecked(true);
                break;
            }
            case 3: {
                radioRating.setChecked(true);
                break;
            }
            case 4: {
                radioNewest.setChecked(true);
                break;
            }
        }
        radioAsc.setOnClickListener(this);
        radioDesc.setOnClickListener(this);
        radioVisiting.setOnClickListener(this);
        radioRating.setOnClickListener(this);
        radioNewest.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        WoocommerceService mWoocommerceService = RetrofitInstance.getInstance(BASE_URL)
                .getRetrofit()
                .create(WoocommerceService.class);
        switch (view.getId()) {
            case R.id.radio_price_asc: {
                radioChecked = 0;
                fetchSortedData(mWoocommerceService, "price", "asc");
                getDialog().dismiss();
                break;
            }
            case R.id.radio_price_desc: {
                radioChecked = 1;
                fetchSortedData(mWoocommerceService, "price", "desc");
                getDialog().dismiss();
                break;
            }

            case R.id.radio_visiting: {
                radioChecked = 2;
                //should be ask
                break;
            }

            case R.id.radio_rating: {
                radioChecked = 3;
                fetchSortedData(mWoocommerceService, "rating", "desc");
                getDialog().dismiss();
                break;
            }
            case R.id.radio_newest: {
                radioChecked = 4;
                fetchSortedData(mWoocommerceService, "date", "desc");
                getDialog().dismiss();
                break;
            }

        }
    }

    private void fetchSortedData(WoocommerceService mWoocommerceService, String price, String asc) {
        Call<List<ProductBody>> call = mWoocommerceService
                .getSortedProductList(CONSUMER_KEY,
                        CONSUMER_SECRET,
                        25,
                        price,
                        asc);
        call.enqueue(new Callback<List<ProductBody>>() {
            @Override
            public void onResponse(Call<List<ProductBody>> call, Response<List<ProductBody>> response) {
                ProductListFragment.getLiveProductList().setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductBody>> call, Throwable t) {

            }
        });
    }
}
