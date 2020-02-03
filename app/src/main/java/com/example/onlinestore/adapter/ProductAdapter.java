package com.example.onlinestore.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.App;
import com.example.onlinestore.R;
import com.example.onlinestore.controller.Activity.DetailProductActivity;
import com.example.onlinestore.model.products.ProductBody;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductBody> mListProduct;
    private Context mContext;

    public ProductAdapter(List<ProductBody> listProduct, Context context) {
        mListProduct = listProduct;
        mContext = context;
    }

    public void setListProduct(List<ProductBody> listProduct) {
        mListProduct = new ArrayList<>();
        mListProduct = listProduct;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.products_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductBody productBody = mListProduct.get(position);

        Picasso.get().load(productBody.getImages().get(0).getSrc())
                .placeholder(R.drawable.digikala_place_holder).into(holder.mProductImage);

        holder.mTitleProduct.setText(productBody.getName());
        if (!productBody.getShortDescription().isEmpty()) {
            holder.mShortDescription.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.mShortDescription
                        .setText(Html.fromHtml(mListProduct
                                .get(position).getShortDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.mShortDescription
                        .setText(Html.fromHtml(productBody
                                .getShortDescription()));
            }
        } else holder.mShortDescription.setVisibility(View.INVISIBLE);

        if (!productBody.getRegularPrice()
                .equals(productBody.getPrice())) {
            holder.mRegularPrice.setVisibility(View.VISIBLE);
            holder.mAmazingSuggestionLable.setVisibility(View.VISIBLE);
        } else {
            holder.mRegularPrice.setVisibility(View.INVISIBLE);
            holder.mAmazingSuggestionLable.setVisibility(View.GONE);
        }

        holder.mTitleProduct.setText(productBody.getName());
        if (!productBody.getRegularPrice().equals("")) {
            String regularPrice = App.getInstance()
                    .getPersianNumber(Double
                            .parseDouble(productBody.getRegularPrice()))
                    + " تومان";
            holder.mRegularPrice.setText(regularPrice);
            holder.mRegularPrice.setVisibility(View.VISIBLE);
        } else {
            holder.mRegularPrice.setText("");
            holder.mRegularPrice.setVisibility(View.INVISIBLE);
        }
        if (!productBody.getPrice().equals("")) {
            String price = App.getInstance()
                    .getPersianNumber(Double.parseDouble(productBody.getPrice()))
                    + " تومان";
            holder.mSalePrice.setText(price);
        } else {
            holder.mRegularPrice.setVisibility(View.INVISIBLE);
        }
        if (!mListProduct.get(position).getName().equals("تخفیفات")) {
            holder.mProductCardView.setOnClickListener(view ->
                    mContext.startActivity(DetailProductActivity
                            .newIntent(mContext, mListProduct.get(position).getId())));
        }

    }


    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_product_list_item)
        TextView mTitleProduct;
        @BindView(R.id.image_product_list_item)
        ImageView mProductImage;
        @BindView(R.id.short_description_product_list_item)
        TextView mShortDescription;
        @BindView(R.id.price_regular_product_list_item)
        TextView mRegularPrice;
        @BindView(R.id.sale_price_product_list_item)
        TextView mSalePrice;
        @BindView(R.id.amazing_suggestion_label_product_list_item)
        ImageView mAmazingSuggestionLable;
        @BindView(R.id.product_cardView_product_list_item)
        CardView mProductCardView;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
