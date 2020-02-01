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
import com.example.onlinestore.model.categories.CategoryBody;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryHorizontalAdapter extends RecyclerView.Adapter<CategoryHorizontalAdapter.CategoryHolder> {
    private List<CategoryBody> mCategoryList;
    private Context mContext;

    public CategoryHorizontalAdapter(List<CategoryBody> categoryList, Context context) {
        mCategoryList = categoryList;
        mContext = context;
    }

    public List<CategoryBody> getCategoryList() {
        return mCategoryList;
    }

    public void setCategoryList(List<CategoryBody> categoryList) {
        mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.category_horizontal_recyclerview_item_green, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.titleCategory.setText(mCategoryList.get(position).getName());
        holder.parentCardView.setOnClickListener(view -> {


        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_category_horizontal_recyclerView)
        TextView titleCategory;
        @BindView(R.id.category_cardView)
        CardView parentCardView;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
