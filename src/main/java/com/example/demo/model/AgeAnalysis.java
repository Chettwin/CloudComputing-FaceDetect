package com.example.demo.model;


import java.util.List;

public class AgeAnalysis {
    private String disc;  //年龄范围
    private int Num=0;     //人数

    private int man=0;
    private int woman=0;

    public int getMan() {
        return man;
    }

    public void setMan() {
        this.man += 1;
    }

    public int getWoman() {
        return woman;
    }

    public void setWoman() {
        this.woman += 1;
    }



    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public int getNum() {
        return Num;
    }

    public void setNum() {

        this.Num += 1;
    }



}
