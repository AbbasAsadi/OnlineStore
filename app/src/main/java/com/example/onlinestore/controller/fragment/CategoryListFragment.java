package com.example.onlinestore.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinestore.R;
import com.example.onlinestore.model.categories.CategoryBody;
import com.example.onlinestore.network.WoocommerceRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment {
    private static final String TAG = "CategoryListFragment";
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;

    private List<CategoryBody> mCategoriesList;
    private WoocommerceRepository mRepository;
    private CategoryViewPagerAdapter mPagerAdapter;

    public static CategoryListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = WoocommerceRepository.getInstance();
        mCategoriesList = mRepository.getCategoriesList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this , view);
        mPagerAdapter = new CategoryViewPagerAdapter(CategoryListFragment.this);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(3);

        List<CategoryBody> parentCategoryList = mRepository.getFilteredCategoryList(0);
        Log.d(TAG, "onCreateView: " + parentCategoryList.size());

        TabLayoutMediator.TabConfigurationStrategy strategy = (tab, position) -> {
            if (position < parentCategoryList.size())
                tab.setText(parentCategoryList.get(position).getName());
            else
                onDetach();
        };
        new TabLayoutMediator(tabLayout , viewPager , strategy).attach();


        return view;
    }



    class CategoryViewPagerAdapter extends FragmentStateAdapter {

        CategoryViewPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }
        List<CategoryBody> mParentCategoryList = mRepository.getFilteredCategoryList(0);

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return SubCategoryFragment
                    .newInstance(mParentCategoryList
                            .get(position).getId());
        }

        @Override
        public int getItemCount() {
            return mParentCategoryList.size();
        }
    }
}
