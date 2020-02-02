package com.example.onlinestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;
import com.example.onlinestore.controller.Activity.CategoryListActivity;
import com.example.onlinestore.controller.Activity.ProductListActivity;
import com.example.onlinestore.model.categories.CategoryBody;
import com.example.onlinestore.model.products.CategoriesItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.categoryViewHolder> {
    private List<CategoriesItem> mListCategories;
    private Context mContext;
    public ProductCategoryAdapter(List<CategoriesItem> categories, Context context) {
        mListCategories = categories;
        mContext = context;
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new categoryViewHolder(LayoutInflater
                .from(mContext)
                .inflate(R.layout.category_horizontal_recyclerview_item_white ,
                        parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        holder.titleCategory.setText(mListCategories.get(position).getName());
        holder.parentCardView.setOnClickListener(view ->
                mContext.startActivity(ProductListActivity
                .newIntent(mContext , mListCategories.get(position).getId())));
    }


    @Override
    public int getItemCount() {
        return mListCategories.size();
    }

    class categoryViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.title_category_horizontal_recyclerView_white)
        TextView titleCategory;
        @BindView(R.id.category_cardView_white)
        CardView parentCardView;
        categoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



    public void setListCategories(List<CategoriesItem> listCategories) {
        mListCategories = listCategories;
    }

    public List<CategoriesItem> getListCategories() {
        return mListCategories;
    }
}
