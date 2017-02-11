package com.example.cay.newsmovie.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.databinding.FragmentWelfareBinding;


/**
 * 福利
 */
public class WelfareFragment extends BaseFragment<FragmentWelfareBinding> {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public int setContent() {
        return R.layout.fragment_welfare;
    }


}
