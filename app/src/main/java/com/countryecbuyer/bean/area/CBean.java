package com.countryecbuyer.bean.area;

import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 */
public class CBean {
    private String n;
    /**
     * n : 东城区
     */

    private List<ABean> a;

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public List<ABean> getA() {
        return a;
    }

    public void setA(List<ABean> a) {
        this.a = a;
    }
}
