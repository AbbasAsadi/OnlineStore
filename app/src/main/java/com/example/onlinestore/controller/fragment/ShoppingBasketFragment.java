package com.example.onlinestore.controller.fragment;


import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.App;
import com.example.onlinestore.R;
import com.example.onlinestore.controller.Activity.DetailProductActivity;
import com.example.onlinestore.model.products.ShoppingBasketProduct;
import com.example.onlinestore.repository.WoocommerceRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingBasketFragment extends Fragment {
    private static final String TAG = "ShoppingBasketFragment";

    @BindView(R.id.progress_bar_shopping_basket)
    ProgressBar mProgressBar;
    @BindView(R.id.basket_recyclerView)
    RecyclerView productRecyclerView;
    @BindView(R.id.basket_badge_shopping_basket)
    TextView basketBadge;
    @BindView(R.id.sum_basket_price_shopping_basket)
    TextView sumBasketPrice;
    @BindView(R.id.basket_relative)
    RelativeLayout basketLayout;
    @BindView(R.id.empty_text_shopping_basket)
    TextView emptyBasketText;
    @BindView(R.id.finalize_basket)
    LinearLayout finalizeBasket;
    private ShoppingBasketAdapter mBasketAdapter;
    private double mSumBasketPrice = 0;

    public ShoppingBasketFragment() {
        // Required empty public constructor
    }

    public static ShoppingBasketFragment newInstance() {

        Bundle args = new Bundle();

        ShoppingBasketFragment fragment = new ShoppingBasketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.toolbar_shopping_basket)
    void onBackClicked() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_basket, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView: ");


        mProgressBar.setVisibility(View.GONE);
        updateUi();

        return view;
    }

    private void updateUi() {
        List<ShoppingBasketProduct> products = WoocommerceRepository
                .getInstance().getShoppingBasketProducts();
        Log.d(TAG, "updateUi: ");
        if (products == null || products.isEmpty()) {
            emptyBasketText.setVisibility(View.VISIBLE);
            productRecyclerView.setVisibility(View.GONE);
            finalizeBasket.setVisibility(View.GONE);
            basketBadge.setVisibility(View.GONE);
            sumBasketPrice.setText("0");
        } else {
            for (ShoppingBasketProduct product : products) {
                mSumBasketPrice += (Integer.parseInt(product.getFinalPrice()) * product.getNumber());
            }
            String badge = App
                    .getInstance()
                    .getPersianNumber(WoocommerceRepository
                            .getInstance()
                            .getBadgeNumber()) + " تومان";
            basketBadge.setText(badge);
            basketBadge.setVisibility(View.VISIBLE);

            String sum = App.getInstance().getPersianNumber(mSumBasketPrice) + " تومان";
            sumBasketPrice.setText(sum);

            productRecyclerView.setLayoutManager(new
                    LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            updateBasketAdapter();
        }
    }

    private void updateBasketAdapter() {
        if (mBasketAdapter == null) {
            mBasketAdapter = new
                    ShoppingBasketAdapter(WoocommerceRepository
                    .getInstance().getShoppingBasketProducts());
        } else {
            mBasketAdapter.setProductList(WoocommerceRepository
                    .getInstance().getShoppingBasketProducts());
            mBasketAdapter.notifyDataSetChanged();
        }
        productRecyclerView.setAdapter(mBasketAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        updateBasketAdapter();
    }

    class ShoppingBasketAdapter extends RecyclerView.Adapter<ShoppingBasketAdapter.ShoppingBasketViewHolder> {
        private List<ShoppingBasketProduct> mProductList;

        ShoppingBasketAdapter(List<ShoppingBasketProduct> productList) {
            mProductList = productList;
        }

        void setProductList(List<ShoppingBasketProduct> productList) {
            mProductList = productList;
        }

        @NonNull
        @Override
        public ShoppingBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ShoppingBasketViewHolder(LayoutInflater.from(getActivity())
                    .inflate(R.layout.product_basket_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ShoppingBasketViewHolder holder, int position) {

            Picasso.get().load(mProductList.get(position).getImageSrc())
                    .placeholder(R.drawable.digikala_place_holder)
                    .into(holder.mProductImage);

            holder.mTitleProduct.setText(mProductList.get(position).getTitle());

            initSpinnerAdapter(holder, position);

            initShortDescription(holder, position);

            initPrice(holder, position);

            initDiscount(holder, position);

            initFinalPrice(holder, position);

            holder.mDeleteProduct.setOnClickListener(view -> {
                WoocommerceRepository.getInstance()
                        .deleteFromShoppingBasket(WoocommerceRepository
                                .getInstance()
                                .getShoppingBasketProducts()
                                .get(position));
                mSumBasketPrice = 0;
                for (ShoppingBasketProduct product:
                        WoocommerceRepository.getInstance().getShoppingBasketProducts() ){
                    mSumBasketPrice += (Double.parseDouble(product.getFinalPrice()) * product.getNumber());
                }
                String sum = App
                        .getInstance()
                        .getPersianNumber(mSumBasketPrice)
                        +" تومان";
                sumBasketPrice.setText(sum);
                String badge = App
                        .getInstance()
                        .getPersianNumber(WoocommerceRepository.getInstance().getBadgeNumber());
                basketBadge.setText(badge);
                mProductList = WoocommerceRepository.getInstance().getShoppingBasketProducts();
            });

            holder.mProductImage.setOnClickListener(view ->
                    getActivity()
                            .startActivity(DetailProductActivity
                                    .newIntent(getActivity(),
                                            mProductList.get(position).getProductId())));

        }

        private void initSpinnerAdapter(@NonNull ShoppingBasketViewHolder holder, int position) {
            ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.product_count, android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.mProductCountSpinner.setSelection(WoocommerceRepository
                    .getInstance()
                    .getShoppingBasketProducts().get(position).getNumber() - 1);
            Log.d(TAG, "initSpinnerAdapter: size:" + mProductList.get(position).getNumber());
            holder.mProductCountSpinner.setAdapter(spinnerAdapter);
            holder.mProductCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG, "onItemSelected: ");
                    if (WoocommerceRepository.getInstance()
                            .getShoppingBasketProducts()
                            .get(position).getNumber() != i) {

                        WoocommerceRepository.getInstance()
                                .getShoppingBasketProducts().get(position).setNumber(i);

                        WoocommerceRepository.getInstance()
                                .updateShoppingBasketProductList(WoocommerceRepository
                                        .getInstance()
                                        .getShoppingBasketProducts().get(position));


                        updateBasketAdapter();
                        mSumBasketPrice = 0;
                        for (ShoppingBasketProduct product :
                                WoocommerceRepository.getInstance().getShoppingBasketProducts()) {
                            mSumBasketPrice += (Integer.parseInt(product.getFinalPrice())
                                    * product.getNumber());
                        }
                        String badge = App
                                .getInstance()
                                .getPersianNumber(WoocommerceRepository
                                        .getInstance()
                                        .getBadgeNumber()) + " تومان";
                        basketBadge.setText(badge);
                        basketBadge.setVisibility(View.VISIBLE);

                        String sum = App.getInstance().getPersianNumber(mSumBasketPrice) + " تومان";
                        sumBasketPrice.setText(sum);
                        Log.d(TAG, "onItemSelected: number: " + WoocommerceRepository
                                .getInstance()
                                .getShoppingBasketProducts().get(position).getNumber());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            holder.mProductCountSpinner.setSelection(mProductList.get(position).getNumber());
        }

        private void initFinalPrice(@NonNull ShoppingBasketViewHolder holder, int position) {
            if (!mProductList.get(position).getFinalPrice().equals("")) {
                String finalPriceStr = App.getInstance()
                        .getPersianNumber(Double
                                .parseDouble(mProductList.get(position).getFinalPrice()) *
                                mProductList.get(position).getNumber())
                        + " تومان";
                holder.mFinalePrice.setText(finalPriceStr);
            } else {
                holder.mFinalePrice.setText("محصول فاقد قیمت است");
            }
        }

        private void initDiscount(@NonNull ShoppingBasketViewHolder holder, int position) {
            if (!mProductList.get(position).getFinalPrice().equals("") &&
                    !mProductList.get(position).getPrice()
                            .equals(mProductList.get(position).getFinalPrice())) {
                int price = Integer.parseInt(mProductList.get(position).getPrice());
                int finalPrice = Integer.parseInt(mProductList
                        .get(position).getFinalPrice());
                double discountPrice = price - finalPrice;
                String discount = App.getInstance()
                        .getPersianNumber(discountPrice * mProductList.get(position).getNumber())
                        + " تومان";
                holder.mDiscountPrice.setText(discount);
                holder.mAmazingSuggestionText.setVisibility(View.VISIBLE);

            } else {
                holder.mDiscountPrice.setVisibility(View.INVISIBLE);
                holder.mAmazingSuggestionText.setVisibility(View.INVISIBLE);
            }
        }

        private void initPrice(@NonNull ShoppingBasketViewHolder holder, int position) {
            if (!mProductList.get(position).getPrice().equals("")) {
                String price = App.getInstance()
                        .getPersianNumber(Double
                                .parseDouble(mProductList.get(position).getPrice())
                                * mProductList.get(position).getNumber())
                        + " تومان";
                holder.mPrice.setText(price);
            } else {
                holder.mPrice.setVisibility(View.INVISIBLE);
            }
        }

        private void initShortDescription(@NonNull ShoppingBasketViewHolder holder, int position) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.mShortDescriptionProduct
                        .setText(Html.fromHtml(mProductList.get(position)
                                .getShortDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.mShortDescriptionProduct
                        .setText(Html.fromHtml(mProductList.get(position).getShortDescription()));
            }
        }


        @Override
        public int getItemCount() {
            return mProductList.size();
        }

        class ShoppingBasketViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.image_product_shopping_basket)
            ImageView mProductImage;
            @BindView(R.id.title_product_shopping_basket)
            TextView mTitleProduct;
            @BindView(R.id.short_description_product_shopping_basket)
            TextView mShortDescriptionProduct;
            @BindView(R.id.product_count_spinner_shopping_basket)
            Spinner mProductCountSpinner;
            @BindView(R.id.price_shopping_basket)
            TextView mPrice;
            @BindView(R.id.discount_price__shopping_basket)
            TextView mDiscountPrice;
            @BindView(R.id.amazing_suggestion_text_shopping_basket)
            TextView mAmazingSuggestionText;
            @BindView(R.id.final_price_shopping_basket)
            TextView mFinalePrice;
            @BindView(R.id.delete_product_shopping_basket)
            TextView mDeleteProduct;

            ShoppingBasketViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
