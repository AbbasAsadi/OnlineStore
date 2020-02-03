package com.example.onlinestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.App;
import com.example.onlinestore.R;
import com.example.onlinestore.controller.Activity.DetailProductActivity;
import com.example.onlinestore.model.products.ProductBody;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapterHorizontal extends RecyclerView.Adapter<ProductAdapterHorizontal.HorizontalProductViewHolder> {
    private List<ProductBody> mListProduct;
    private Context mContext;

    public ProductAdapterHorizontal(List<ProductBody> listProduct, Context context) {
        mListProduct = listProduct;
        mContext = context;
    }

    public void setListProduct(List<ProductBody> listProduct) {
        mListProduct = listProduct;
    }

    @NonNull
    @Override
    public HorizontalProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HorizontalProductViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.horizontal_recyclerview_item_price, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductViewHolder holder, int position) {
        ProductBody productBody = mListProduct.get(position);
        if (productBody.getPrice().equals(""))
            mListProduct.remove(position);
        if (productBody.getImages().isEmpty()) {
            holder.productImage.setImageResource(R.drawable.digikala_place_holder);
        } else {
            Picasso.get().load(productBody.getImages().get(0).getSrc())
                    .placeholder(R.drawable.digikala_place_holder)
                    .into(holder.productImage);
        }
        if (!productBody.getRegularPrice().equals(productBody.getPrice())) {
            holder.priceRegular.setVisibility(View.VISIBLE);
        } else holder.priceRegular.setVisibility(View.GONE);

        holder.titleProduct.setText(productBody.getName());


        if (!productBody.getRegularPrice().equals("")) {
            String regularPrice = App.getInstance()
                    .getPersianNumber(Double.parseDouble(productBody.getRegularPrice()))
                    + " تومان";

            holder.priceRegular.setText(regularPrice);
        } else {
            holder.priceRegular.setVisibility(View.INVISIBLE);
        }

        if (!productBody.getPrice().equals("")) {
            String price = App.getInstance()
                    .getPersianNumber(Double.parseDouble(productBody.getPrice()))
                    + " تومان";
            holder.salePrice.setText(price);
        } else {
            holder.salePrice.setVisibility(View.INVISIBLE);
        }

        if (!mListProduct.get(position).getName().equals("تخفیفات")) {
            holder.horizontalCardView.setOnClickListener(view -> {
                Toast.makeText(mContext,
                        "clickedId" + mListProduct.get(position).getId(),
                        Toast.LENGTH_SHORT).show();
                mContext.startActivity(DetailProductActivity
                        .newIntent(mContext, mListProduct.get(position).getId()));
            });
        }


    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    class HorizontalProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_img_horizontal_recyclerView)
        ImageView productImage;
        @BindView(R.id.title_product_horizontal_recyclerView)
        TextView titleProduct;
        @BindView(R.id.price_regular)
        TextView priceRegular;
        @BindView(R.id.sale_price)
        TextView salePrice;
        @BindView(R.id.horizontal_cardView)
        MaterialCardView horizontalCardView;

        HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
