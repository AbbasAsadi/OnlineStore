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

    public List<ProductBody> getListProduct() {
        return mListProduct;
    }

    public void setListProduct(List<ProductBody> listProduct) {
        mListProduct = new ArrayList<>();
        mListProduct = listProduct;
    }

    public void addToProductList(List<ProductBody> productList) {
        this.mListProduct.addAll(productList);
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
                .placeholder(R.drawable.digikala_place_holder).into(holder.productImage);

        holder.titleProduct.setText(productBody.getName());
        if (!productBody.getShortDescription().isEmpty()) {
            holder.shortDescription.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.shortDescription
                        .setText(Html.fromHtml(mListProduct
                                .get(position).getShortDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.shortDescription
                        .setText(Html.fromHtml(productBody
                                .getShortDescription()));
            }
        } else holder.shortDescription.setVisibility(View.INVISIBLE);

        if (!productBody.getRegularPrice()
                .equals(productBody.getPrice())) {
            holder.regularPrice.setVisibility(View.VISIBLE);
            holder.amazingSuggestionLable.setVisibility(View.VISIBLE);
        } else {
            holder.regularPrice.setVisibility(View.INVISIBLE);
            holder.amazingSuggestionLable.setVisibility(View.GONE);
        }

        holder.titleProduct.setText(productBody.getName());
        if (!productBody.getRegularPrice().equals("")){
            String regularPrice = App.getInstance()
                    .getPersianNumber(Double
                            .parseDouble(productBody.getRegularPrice()))
                    + " تومان";
            holder.regularPrice.setText(regularPrice);
            holder.regularPrice.setVisibility(View.VISIBLE);
        }else {
            holder.regularPrice.setText("");
            holder.regularPrice.setVisibility(View.INVISIBLE);
        }

        String price = App.getInstance()
                .getPersianNumber(Double.parseDouble(productBody.getPrice()))
                + " تومان";
        holder.salePrice.setText(price);

        holder.productCardView.setOnClickListener(view ->
                mContext.startActivity(DetailProductActivity
                .newIntent(mContext , mListProduct.get(position).getId())));
    }


    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_product)
        public TextView titleProduct;
        @BindView(R.id.product_img)
        public ImageView productImage;
        @BindView(R.id.short_description)
        TextView shortDescription;
        @BindView(R.id.price_regular)
        TextView regularPrice;
        @BindView(R.id.sale_price)
        TextView salePrice;
        @BindView(R.id.amazing_suggestion_label)
        ImageView amazingSuggestionLable;
        @BindView(R.id.product_cardView)
        CardView productCardView;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
