package com.example.rth.util;

import android.content.Context;

/**
 * Created by rth on 15-9-11.
 */
public class Utils {

    /**
     * 像素转换为dp
     * @param context
     */
    public static float pixelToDp(Context context, int val) {
        float density = context.getResources().getDisplayMetrics().density;
        return val * density;
    }
}
