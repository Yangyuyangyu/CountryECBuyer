package com.countryecbuyer.bean.area;

import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 */
public class CitylistBean {
    private String p;
    /**
     * n : 北京
     * a : [{"n":"东城区"},{"n":"西城区"},{"n":"崇文区"},{"n":"宣武区"},{"n":"朝阳区"},{"n":"丰台区"},{"n":"石景山区"},{"n":"海淀区"},{"n":"门头沟区"},{"n":"房山区"},{"n":"通州区"},{"n":"顺义区"},{"n":"昌平区"},{"n":"大兴区"},{"n":"平谷区"},{"n":"怀柔区"},{"n":"密云县"},{"n":"延庆县"}]
     */

    private List<CBean> c;

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
    public List<CBean> getC() {
        return c;
    }

    public void setC(List<CBean> c) {
        this.c = c;
    }
}
