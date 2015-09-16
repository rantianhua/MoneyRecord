package com.example.rth.util;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by rth on 15-9-13.
 */
public class VolleyUtils {

    private static VolleyUtils volleyUtils; //单一静态变量

    private RequestQueue requestQueue;  //请求队列

    private VolleyUtils() {
        //requestQueue = Volley.newRequestQueue()
    }
}
