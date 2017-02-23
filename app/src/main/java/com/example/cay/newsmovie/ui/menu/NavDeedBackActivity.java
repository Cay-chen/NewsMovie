package com.example.cay.newsmovie.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.base.BaseActivity;
import com.example.cay.newsmovie.databinding.ActivityNavDeedBackBinding;
import com.example.cay.newsmovie.utils.PerfectClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


public class NavDeedBackActivity extends BaseActivity<ActivityNavDeedBackBinding> {
    private EditText mEditText;
    private boolean isSubmit = false;
    private static final String TAG = "Cay";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_deed_back);
        setTitle("问题反馈");
        showContentView();
        mEditText = bindingView.etCon;
        bindingView.tvQq.setOnClickListener(listener);
        bindingView.btnSubmit.setOnClickListener(listener);
        bindingView.tvFaq.setOnClickListener(listener);
    }

    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            switch (v.getId()) {
              /*  case R.id.tv_issues:
                    Uri issuesUrl = Uri.parse("https://github.com/youlookwhat/CloudReader/issues");
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, issuesUrl);
                    startActivity(intent2);

//                    String issuesUrl = "https://github.com/youlookwhat/CloudReader/issues";
//                    String issuesUrl = "http://jingbin.me/2017/11/23/%E5%BC%80%E5%8F%91%E4%B8%AD%E6%89%80%E9%81%87%E9%97%AE%E9%A2%98%E5%BD%92%E7%BA%B3/";
//                    WebViewActivity.loadUrl(NavDeedBackActivity.this, issuesUrl, "加载中...");
                    break;*/
                case R.id.tv_qq:
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=276495166";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    break;
               case R.id.btn_submit:
                   if (mEditText.getText().toString().trim().isEmpty()) {
                       Toast.makeText(NavDeedBackActivity.this, "请输入提交内容", Toast.LENGTH_SHORT).show();
                   } else {
                       if (!isSubmit) {
                           isSubmit = true;
                           submit1();
                       }
                   }
                    break;
                case R.id.tv_faq:
                    IssueActivity.start(NavDeedBackActivity.this);
                    break;
            }
        }
    };

    private void submit1() {
        OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/UpIssueServer").addParams("issue",mEditText.getText().toString()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                isSubmit = false;
                Toast.makeText(NavDeedBackActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String response, int id) {
                if (response.trim().equals("1")) {
                    mEditText.setText("");
                    isSubmit = false;
                    Toast.makeText(NavDeedBackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                } else {
                    isSubmit = false;
                    Toast.makeText(NavDeedBackActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDeedBackActivity.class);
        mContext.startActivity(intent);
    }
}
