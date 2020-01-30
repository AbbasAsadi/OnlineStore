package com.example.onlinestore.utils.sliderr;

import android.widget.ImageView;

import com.example.onlinestore.R;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {
    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideimageSlideViewHolder) {
        switch (position) {
            case 0:
                imageSlideimageSlideViewHolder.bindImageSlide(R.drawable.slider_16197);
                imageSlideimageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 1:
                imageSlideimageSlideViewHolder.bindImageSlide(R.drawable.slider_17672);
                imageSlideimageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 2:
                imageSlideimageSlideViewHolder.bindImageSlide(R.drawable.slider_18057);
                imageSlideimageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 3:
                imageSlideimageSlideViewHolder.bindImageSlide(R.drawable.slider_18463);
                imageSlideimageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 4:
                imageSlideimageSlideViewHolder.bindImageSlide(R.drawable.slider_18845);
                imageSlideimageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 5:
                imageSlideimageSlideViewHolder.bindImageSlide(R.drawable.slider_19000);
                imageSlideimageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //imageSlideimageSlideViewHolder.bindImageSlide("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
                break;
        }
    }
}
