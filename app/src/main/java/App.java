import android.app.Application;

import com.example.onlinestore.utils.utils.sliderr.PicassoImageLoadingService;

import ss.com.bannerslider.Slider;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Slider.init(new PicassoImageLoadingService());
    }
}
