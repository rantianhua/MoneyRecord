package com.example.rth.fragments;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rth.BaseHomeFragment;
import com.example.rth.moneyrecord.MainActivity;
import com.example.rth.moneyrecord.R;
import com.example.rth.util.Constants;
import com.example.rth.util.ImageUtil;
import com.example.rth.util.Utils;
import com.example.rth.widgets.CircleImageDrawable;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rth on 15-9-16.
 */
public class RegisterFragment extends BaseHomeFragment {

    private ImageView imgPhoto; //点击添加头像
    private EditText etName;    //用户名输入框
    private EditText etPass;    //密码输入框
    private Button btnRegister; //注册按钮
    private TextView tvBack;    //取消注册

    private String name,pass,photoPath; //名称、密码、保存图片的绝对路径
    private int imgW,imgH;  //图片控件的长度和宽度
    private boolean galleryPic = false; //标识图片是否来自相册

    private ProgressDialog pd;  //等待信息
    private RequestQueue requestQueue;  //Volley的请求队列

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        imgPhoto = (ImageView) view.findViewById(R.id.frag_register_img_photo);
        etName = (EditText) view.findViewById(R.id.frag_register_et_name);
        etPass = (EditText) view.findViewById(R.id.frag_register_et_password);
        btnRegister = (Button) view.findViewById(R.id.frag_register_btn_regidter);
        tvBack = (TextView) view.findViewById(R.id.frag_register_tv_back);
        imgPhoto.post(new Runnable() {
            @Override
            public void run() {
                imgW = imgPhoto.getWidth();
                imgH = imgPhoto.getHeight();
            }
        });
        return view;
    }

    @Override
    protected void initEvent(View view) {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //注册帐号
                register();
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //取消
                cancel();
            }
        });
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置头像
                setPhoto();
            }
        });
    }

    /**
     * 取消注册
     */
    private void cancel() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }

    /**
     * 注册帐号
     */
    private void register() {
        name = etName.getText().toString();
        pass = etPass.getText().toString();
        if(TextUtils.isEmpty(name)) {
            Utils.showToast("请填写用户名", getActivity());
            return;
        }
        if(TextUtils.isEmpty(pass)) {
            Utils.showToast("请填写密码", getActivity());
            return;
        }
        if(TextUtils.isEmpty(photoPath)) {
            Utils.showToast("请设置头像", getActivity());
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(pd.isShowing()) {
                    pd.dismiss();
                }
                if(s != null) {
                    try {
                        JSONObject oj = new JSONObject(s);
                        String msg = oj.getString("msg");
                        if(msg.equals("ok")) {
                            //注册成功
                            int id = oj.getInt("id");
                            registerFinish(id);
                        }else {
                            //注册失败
                            Utils.showToast("注册失败",getActivity());
                        }
                    }catch (Exception e) {
                        Log.e("register","error in onResponse",e);
                    }
                }else {
                    Utils.showToast("注册失败,请联系后台人员",getActivity());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(pd.isShowing()) {
                    pd.dismiss();
                }
                Log.e("onErrorResponse",volleyError.getMessage());
                Utils.showToast("出错了，请检查网络",getActivity());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_name",name);
                params.put("pass",pass);
                params.put("pic_url",photoPath);
                return params;
            }
        };
        pd.setMessage("正在注册...");
        pd.show();
        requestQueue.add(stringRequest);
    }

    /**
     * 注册完成
     * @param id 注册成功后的id
     */
    private void registerFinish(int id) {
        //保存用户信息
        Utils.updateUserInfo(getActivity(),id,name,photoPath,pass,true);
        //进入主页面
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    /**Constants.
     * 设置头像
     */
    private void setPhoto() {
        //选择获取头像的方式
        new AlertDialog.Builder(getActivity()).setItems(new String[]{"打开图库", "拍一张"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0) {
                    //打开系统图库
                    openGallery();
                }else if(i == 1){
                    //打开系统相机
                    openCamera();
                }
            }
        }).show();
    }

    /**
     * 打开系统图库
     */
    private void openGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(i.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(i, Constants.REQUEST_OPEN_GALLERY);
        }
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //确保进程能够获取返回的intent
        if(takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
            photoPath = getActivity().getExternalFilesDir(null)+Constants.USER_PHOTO_NAME;
            Uri imageUri = Uri.fromFile(new File(photoPath));
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePicture, Constants.REQUEST_IMAGE_CPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_IMAGE_CPTURE && resultCode == Activity.RESULT_OK) {
            //拍照获得的图片
            galleryPic = false;
            showBitmap();
        }else if (requestCode == Constants.REQUEST_OPEN_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            //打开图册获得的照片
            galleryPic = true;
            photoPath = ImageUtil.getImgPathFromIntent(data, getActivity());
            showBitmap();
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 显示获取的图片
     */
    private void showBitmap() {
        if(photoPath == null) {
            Utils.showToast("获取图片失败",getActivity());
            return;
        }
        Bitmap bitmap = ImageUtil.decodeSampledBitmapFromResource(photoPath,imgW,imgH);
        if(bitmap != null) {
            CircleImageDrawable drawable = new CircleImageDrawable(bitmap);
            imgPhoto.setImageDrawable(drawable);
        }
        if(galleryPic && bitmap != null) {
            //保存图片到本地
            ImageUtil.savePicToLocal(new File(getActivity().getExternalFilesDir(null)+Constants.USER_PHOTO_NAME),bitmap);
        }
    }


}
