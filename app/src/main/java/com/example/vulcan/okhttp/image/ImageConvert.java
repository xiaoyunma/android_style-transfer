package com.example.vulcan.okhttp.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageConvert {  //Bitmap转换为Drawable
    public static Drawable bitmapToDrawable(Bitmap bitmap){
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        Drawable drawable = (Drawable)bitmapDrawable;
        return drawable;
    }

    //Drawable转换为Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable){
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(drawableWidth, drawableHeight, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawableWidth, drawableHeight);
        drawable.draw(canvas);
        return bitmap;
    }

    //Bitmap转换成字节数组
    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 , outputStream);
        return outputStream.toByteArray();
    }

    //字节数组转换成Bitmap
    public static Bitmap byteArrayToBitmap(byte[] bytes){
        if(bytes.length > 0){
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return null;
    }

    //Drawable转化成字节数组
    public static byte[] drawableTobyteArray(Drawable drawable){
        Bitmap bitmap = drawableToBitmap(drawable);
        return bitmapToByteArray(bitmap);
    }

    //字节数组转化成Drawable
    public static Drawable byteArrayToDrawable(byte[] bytes){
        Bitmap bitmap = byteArrayToBitmap(bytes);
        Drawable drawable = bitmapToDrawable(bitmap);
        return drawable;
    }

    //将字节数组转化成InputStream
    public static InputStream byteArrayToInputStream(byte[] bytes){
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return bais;
    }

    //InputStream转化成字节数组
    public static byte[] inputStreamToByteArray(InputStream inputStream){
        String tempString = "";
        byte[] readBytes = new byte[1024];
        int readLength = -1;
        try {
            while (inputStream.read(readBytes, 0, 1024) != -1) {
                tempString += new String(readBytes).trim();
            }
            return tempString.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Bitmap转换成InputStream
    public static InputStream bitmapToInputStream(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return bais;
    }

    //将InputStream转化成Bitmap
    public static Bitmap inputStreamToBitmap(InputStream inputStream){
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    //Drawable转化成InputStream
    public static InputStream drawableToInputStream(Drawable drawable){
        Bitmap bitmap = drawableToBitmap(drawable);
        InputStream inputStream = bitmapToInputStream(bitmap);
        return inputStream;
    }

    //InputStream转化成Drawable
    public static Drawable inputStreamToDrawable(InputStream inputStream){
        Bitmap bitmap = inputStreamToBitmap(inputStream);
        Drawable drawable = bitmapToDrawable(bitmap);
        return drawable;
    }

}
