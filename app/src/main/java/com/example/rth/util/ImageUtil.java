package com.example.rth.util;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by rth on 15-9-16.
 * 处理图片的公共方法
 */
public class ImageUtil {

    /**
     * 从intetnt中获取图片的路径
     * @param intent
     * @param activity
     * @return
     */
    public static String getImgPathFromIntent(Intent intent,Activity activity) {
        try {
            Uri selectedImage = intent.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = activity.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            return picturePath;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到指定路径的图片的缩略图
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(String pathName,int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }


    /**
     * 计算inSampleSize，用于压缩图片
     *
     * @param options   原图片的参数
     * @param reqWidth  要求的图片的宽度
     * @param reqHeight 要求的图片的高度
     * @return  压缩图片的比例
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight)
    {
        // 源图片的宽度
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth && height > reqHeight)
        {
            // 计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 保存图片到本地
     * @param file  要保存的文件
     * @param bitmap    要保存的图片
     */
    public static void savePicToLocal(File file,Bitmap bitmap) {
        if(file == null || bitmap == null) return;
        if(file.exists()) {
            file.delete();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            file = null;
            bitmap = null;
        }
    }
}
