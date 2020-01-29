package com.example.onlinestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.App;
import com.example.onlinestore.R;
import com.example.onlinestore.model.WoocommerceBody;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapterHorizontal extends RecyclerView.Adapter <ProductAdapterHorizontal.HorizontalProductViewHolder> {
    private List<WoocommerceBody> mListProduct;
    private Context mContext;

    public ProductAdapterHorizontal(List<WoocommerceBody> listProduct, Context context) {
        mListProduct = listProduct;
        mContext = context;
    }

    public List<WoocommerceBody> getListProduct() {
        return mListProduct;
    }

    public void setListProduct(List<WoocommerceBody> listProduct) {
        mListProduct = listProduct;
    }

    @NonNull
    @Override
    public HorizontalProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HorizontalProductViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.horizontal_recyclerview_item_price , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductViewHolder holder, int position) {
        if (mListProduct.get(position).getPrice().equals("")) mListProduct.remove(position);
        if (mListProduct.get(position).getImages().isEmpty()){
            holder.productImage.setImageResource(R.drawable.digikala_place_holder);
        }else {
            Picasso.get().load(mListProduct.get(position).getImages().get(0).getSrc())
                    .placeholder(R.drawable.digikala_place_holder)
                    .into(holder.productImage);
        }
        if (!mListProduct.get(position).getRegularPrice().equals(mListProduct.get(position).getPrice())) {
            holder.priceRegular.setVisibility(View.VISIBLE);
        }else holder.priceRegular.setVisibility(View.GONE);

        holder.titleProduct.setText(mListProduct.get(position).getName());
        String regularPrice = App.getInstance()
                .getPersianNumber(Double.parseDouble(mListProduct.get(position).getRegularPrice()))
                + " تومان";

        holder.priceRegular.setText(regularPrice);
        String price =  App.getInstance()
                .getPersianNumber(Double.parseDouble(mListProduct.get(position).getPrice()))
                + " تومان";
        holder.salePrice.setText(price);

        /*holder.horizontalCardView.setOnClickListener(view ->
                EventBus.getDefault()
                        .post(new OnProductClickedMessage(mListProduct.get(position).getId())));*/
    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_img_horizontal_recyclerView)
        ImageView productImage;
        @BindView(R.id.title_product_horizontal_recyclerView)
        TextView titleProduct;
        @BindView(R.id.price_regular)
        TextView  priceRegular;
        @BindView(R.id.sale_price)
        TextView  salePrice;
        @BindView(R.id.horizontal_cardView)
        MaterialCardView horizontalCardView;
        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);

        }
    }
}
