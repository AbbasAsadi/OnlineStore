package com.example.onlinestore.utils.sliderr;

import android.widget.ImageView;

import com.example.onlinestore.R;
import com.example.onlinestore.repository.WoocommerceRepository;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {
    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        switch (position) {
            case 0:
                imageSlideViewHolder.bindImageSlide(R.drawable.slider_16197);
                imageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 1:
                imageSlideViewHolder.bindImageSlide(R.drawable.slider_17672);
                imageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 2:
                imageSlideViewHolder.bindImageSlide(R.drawable.slider_18057);
                imageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 3:
                imageSlideViewHolder.bindImageSlide(R.drawable.slider_18463);
                imageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 4:
                imageSlideViewHolder.bindImageSlide(R.drawable.slider_18845);
                imageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 5:
                imageSlideViewHolder.bindImageSlide(R.drawable.slider_19000);
                imageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //imageSlideViewHolder.bindImageSlide("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
                break;
        }
    }
}
