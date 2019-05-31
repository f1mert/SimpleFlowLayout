package com.f1mert.flowlayout.simpleFlowLayout.bean;

public class TagModel<T> {

    private T t;

    private MarginModel marginModel;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public MarginModel getMarginModel() {
        return marginModel;
    }

    public void setMarginModel(MarginModel marginModel) {
        this.marginModel = marginModel;
    }
}
