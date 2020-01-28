package com.example.onlinestore;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.onlinestore.utils.utils.sliderr.MainSliderAdapter;
import com.example.onlinestore.utils.utils.sliderr.PicassoImageLoadingService;

import ss.com.bannerslider.Slider;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private Slider mSlider;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slider.init(new PicassoImageLoadingService());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mSlider = view.findViewById(R.id.slider_main_fragment);
        mSlider.setAdapter(new MainSliderAdapter());
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
