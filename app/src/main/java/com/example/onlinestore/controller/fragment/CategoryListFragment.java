package com.example.onlinestore.controller.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.onlinestore.R;
import com.example.onlinestore.repository.WoocommerceRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment {
    private static final String TAG = "CategoryListFragment";
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;
    private int mPosition;
    private CategoryViewPagerAdapter mPagerAdapter;
    public CategoryListFragment() {
        // Required empty public constructor
    }

    private CategoryListFragment(int position) {
        mPosition = position;
    }

    public static CategoryListFragment newInstance(int position) {

        Bundle args = new Bundle();

        CategoryListFragment fragment = new CategoryListFragment(position);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.back_toolbar)
    void backPress() {
        getActivity().finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, view);
        updateCategoryAdapter();

        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(mPosition);

        Log.d(TAG, "onCreateView: " + WoocommerceRepository.getInstance().getParentCategoryList().size());

        TabLayoutMediator.TabConfigurationStrategy strategy = (tab, position) ->
                tab.setText(WoocommerceRepository.getInstance().getParentCategoryList().get(position).getName());
        new TabLayoutMediator(tabLayout, viewPager, strategy).attach();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCategoryAdapter();
    }

    private void updateCategoryAdapter() {
        if (mPagerAdapter == null) {
            mPagerAdapter = new
                    CategoryViewPagerAdapter(CategoryListFragment.this);
        } else {
            mPagerAdapter.notifyDataSetChanged();
        }
        viewPager.setAdapter(mPagerAdapter);
    }


    class CategoryViewPagerAdapter extends FragmentStateAdapter {

        CategoryViewPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return SubCategoryFragment
                    .newInstance(WoocommerceRepository.getInstance().getParentCategoryList()
                            .get(position).getId());
        }

        @Override
        public int getItemCount() {
            return WoocommerceRepository.getInstance().getParentCategoryList().size();
        }
    }

}
