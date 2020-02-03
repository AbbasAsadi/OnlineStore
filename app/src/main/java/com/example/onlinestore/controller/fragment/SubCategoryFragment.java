package com.example.onlinestore.controller.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;
import com.example.onlinestore.controller.Activity.ProductListActivity;
import com.example.onlinestore.model.categories.CategoryBody;
import com.example.onlinestore.repository.WoocommerceRepository;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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
    private MutableLiveData<List<CategoryBody>> mLiveSubCategoryList;
    private SubCategoryAdapter mAdapter;
    private List<CategoryBody> mSubCategoryList;

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

    private MutableLiveData<List<CategoryBody>> getLiveSubCategoryList() {
        if (mLiveSubCategoryList == null) {
            mLiveSubCategoryList = new MutableLiveData<>();
        }
        return mLiveSubCategoryList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        ButterKnife.bind(this, view);

        FetchSubCategoryAsync async = new FetchSubCategoryAsync();
        async.execute();

        mSubCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(), RecyclerView.VERTICAL, false
        ));

        Observer<List<CategoryBody>> observer = categoryBodies -> {
            if (getLiveSubCategoryList().getValue() != null) {
                updateSubCategoryAdapter();
            } else {
                Toast.makeText(getActivity(), "ایست خالی است!", Toast.LENGTH_SHORT).show();
            }
        };

        getLiveSubCategoryList().observe(this, observer);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //updateSubCategoryAdapter();
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
            holder.SubCategoryTitle.setText(getLiveSubCategoryList().getValue().get(position).getName());
            Picasso.get().load(getLiveSubCategoryList().getValue().get(position).getImage().getSrc()).placeholder
                    (R.drawable.digikala_place_holder).into(holder.subCategoryImage);
            holder.parentLayout.setOnClickListener(view -> {
                //on Category clicked
                startActivity(ProductListActivity
                        .newIntent(getActivity(),
                                getLiveSubCategoryList().getValue().get(position).getId()));
            });
        }

        @Override
        public int getItemCount() {
            //Log.d(TAG, "getItemCount: " + getLiveSubCategoryList().getValue().size() + " id: " + mParentCategoryId);
            return getLiveSubCategoryList().getValue().size();
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

    class FetchSubCategoryAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mSubCategoryList = WoocommerceRepository.getInstance()
                        .getFilteredCategoryList(mParentCategoryId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getLiveSubCategoryList().setValue(mSubCategoryList);
            updateSubCategoryAdapter();
        }
    }

}
