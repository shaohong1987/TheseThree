package com.shaohong.thesethree.model;

import com.shaohong.thesethree.bean.CommonData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaohong on 2017/5/10.
 */

public class HomeModel {

    public List<CommonData> getBanner() {
        List<CommonData> list = new ArrayList<>();
        CommonData commonData = new CommonData();
        commonData.setImage("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
        commonData.setTitle("图片1");
        list.add(commonData);
        commonData = new CommonData();
        commonData.setImage("http://pic18.nipic.com/20111215/577405_080531548148_2.jpg");
        commonData.setTitle("图片2");
        list.add(commonData);
        commonData = new CommonData();
        commonData.setImage("http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg");
        commonData.setTitle("图片3");
        list.add(commonData);
        commonData = new CommonData();
        commonData.setImage("http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg");
        commonData.setTitle("图片4");
        list.add(commonData);
        return list;
    }
}
