package com.example.rth.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.rth.util.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rth on 15-9-16.
 */
public class LoginFragment extends BaseHomeFragment {

    private EditText etName;    //用户名输入框
    private EditText etPass;    //密码输入框
    private Button btnLogin;    //登录按钮
    private TextView tvRegister;    //注册

    private String name,pass;
    RequestQueue request;   //Voelly请求队列
    private ProgressDialog pd;  //提示信息

    //StringRequest的回调接口
    private final Response.Listener<String> response = new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            afterResponse(s);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = Volley.newRequestQueue(getActivity());
        pd = new ProgressDialog(getActivity());
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        etName = (EditText) view.findViewById(R.id.frag_login_et_name);
        etPass = (EditText) view.findViewById(R.id.frag_login_et_password);
        btnLogin = (Button) view.findViewById(R.id.frag_login_btn_login);
        tvRegister = (TextView) view.findViewById(R.id.frag_login_tv_register);
        return view;
    }

    @Override
    protected void initEvent(View view) {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statrLogin();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //注册
                getFragmentManager().beginTransaction().replace(R.id.activity_start_container,
                        new RegisterFragment()).commit();
            }
        });
    }

    /**
     * 开始登录帐号
     */
    private void statrLogin() {
        //先判断用户名和密码有没有填写
        name = etName.getText().toString();
        pass = etPass.getText().toString();
        if(TextUtils.isEmpty(name)) {
            Utils.showToast("请填写用户名",getActivity());
            return;
        }
        if(TextUtils.isEmpty(pass)) {
            Utils.showToast("请填写密码",getActivity());
            return;
        }
        //开始登录
        String url = "http://1.moneyrecord.sinaapp.com/api/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(pd.isShowing()) {
                    pd.dismiss();
                }
                Utils.showToast("登录失败，请检查网络",getActivity());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> maps = new HashMap<>();
                maps.put("user_name",name);
                maps.put("pass",pass);
                return maps;
            }
        };
        pd.setMessage("正在登录...");
        pd.show();
        request.add(stringRequest);
    }

    /**
     * 收到服务器的回复
     * @param s json数据
     */
    private void afterResponse(String s) {
        if(pd.isShowing()) {
            pd.dismiss();
        }
        if(s != null) {
            JSONObject oj=null;
            try {
                oj = new JSONObject(s);
                String res = oj.getString("msg");
                if(res.equals("ok")) {
                    //登录成功
                    int id = oj.getInt("message");
                    Utils.updateUserInfo(getActivity(), id, name, null, pass, true);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else {
                    Utils.showToast(oj.getString("message"),getActivity());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            Utils.showToast("登录失败",getActivity());
        }
    }
}
