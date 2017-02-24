package com.example.cay.newsmovie.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.base.BaseActivity;
import com.example.cay.newsmovie.bean.UpDdtaBackBean;
import com.example.cay.newsmovie.databinding.ActivityNavDeedBackBinding;
import com.example.cay.newsmovie.http.HttpUtils;
import com.example.cay.newsmovie.ui.activity.IssueActivity;
import com.example.cay.newsmovie.utils.PerfectClickListener;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NavDeedBackActivity extends BaseActivity<ActivityNavDeedBackBinding> {
    private EditText mEditText;
    private boolean isSubmit = false;
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
        HttpUtils.getInstance().getMyObservableClient().upIssueData(mEditText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpDdtaBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpDdtaBackBean value) {
                        if (value.getResMsg().trim().equals("1")) {
                            mEditText.setText("");
                            isSubmit = false;
                            Toast.makeText(NavDeedBackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                        } else {
                            isSubmit = false;
                            Toast.makeText(NavDeedBackActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isSubmit = false;
                        Toast.makeText(NavDeedBackActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDeedBackActivity.class);
        mContext.startActivity(intent);
    }
}
