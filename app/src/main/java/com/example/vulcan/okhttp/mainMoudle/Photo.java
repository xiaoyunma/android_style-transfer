package com.example.vulcan.okhttp.mainMoudle;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.vulcan.okhttp.R;
import com.example.vulcan.okhttp.image.ImageConvert;

public class Photo {
    public static Init init=new Init();
    public static Bitmap bitmap=null;
}
   class Init extends Application {
    public Init(){}
    public  Bitmap getBitmap(){
        Resources resources = getApplicationContext().getResources();
        Drawable drawable = resources.getDrawable(R.drawable.cubist);
        return ImageConvert.drawableToBitmap(drawable);
    }
}

