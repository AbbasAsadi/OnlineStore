package com.example.onlinestore.controller.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;
import com.example.onlinestore.controller.Activity.ProductListActivity;
import com.example.onlinestore.model.categories.CategoryBody;
import com.example.onlinestore.repository.WoocommerceRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragment extends Fragment {
    private static final String TAG = "SubCategoryFragment";

    @BindView(R.id.sub_category_recyclerView)
    RecyclerView mSubCategoryRecyclerView;

    private int mParentCategoryId;
    private List<CategoryBody> mSubCategoryList;
    private SubCategoryAdapter mAdapter;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    private SubCategoryFragment(int categoryId) {
        mParentCategoryId = categoryId;
    }

    public static SubCategoryFragment newInstance(int categoryId) {

        Bundle args = new Bundle();

        SubCategoryFragment fragment = new SubCategoryFragment(categoryId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubCategoryList = WoocommerceRepository.getInstance().getFilteredCategoryList(mParentCategoryId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        ButterKnife.bind(this, view);

        updateSubCategoryAdapter();
        mSubCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(), RecyclerView.VERTICAL, false
        ));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSubCategoryAdapter();
    }

    private void updateSubCategoryAdapter() {
        if (mAdapter == null) {
            mAdapter = new SubCategoryAdapter();
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mSubCategoryRecyclerView.setAdapter(mAdapter);
    }


    class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {


        @NonNull
        @Override
        public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SubCategoryViewHolder(LayoutInflater
                    .from(getActivity())
                    .inflate(R.layout.sub_category_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
            holder.SubCategoryTitle.setText(mSubCategoryList.get(position).getName());
            Picasso.get().load(mSubCategoryList.get(position).getImage().getSrc()).placeholder
                    (R.drawable.digikala_place_holder).into(holder.subCategoryImage);
            holder.parentLayout.setOnClickListener(view -> {
                //on Category clicked
                startActivity(ProductListActivity
                        .newIntent(getActivity(),
                                mSubCategoryList.get(position).getId()));
            });
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount: " + mSubCategoryList.size() + " id: " + mParentCategoryId);
            return mSubCategoryList.size();
        }

        class SubCategoryViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.sub_category_title)
            TextView SubCategoryTitle;
            @BindView(R.id.sub_category_img)
            ImageView subCategoryImage;
            @BindView(R.id.sub_category_relative)
            RelativeLayout parentLayout;

            SubCategoryViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
