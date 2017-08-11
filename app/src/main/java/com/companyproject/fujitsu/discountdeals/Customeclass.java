package com.companyproject.fujitsu.discountdeals;

/**
 * Created by lue on 18-05-2017.
 */

public class Customeclass {

    private  String text1;
    private  String text2;

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

//    public Customeclass(String storeid) {
//        this.storeid = storeid;
//    }

    private String storeid;

    public Customeclass() {
    }

    public Customeclass(String text1, String text2,String storeid) {
        this.text1 = text1;
        this.text2 = text2;
        this.storeid = storeid;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

}
