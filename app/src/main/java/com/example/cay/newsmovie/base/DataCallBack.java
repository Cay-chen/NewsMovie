package com.example.cay.newsmovie.base;

import java.util.List;

/**
 * Created by Cay on 2017/2/21.
 */

public interface DataCallBack {
    void err();

    void success(List list);

}
