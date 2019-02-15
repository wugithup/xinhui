package com.shuto.mam.webclient.beans.stpi.pi_importdata.datatype;

import cc.aicode.e2e.extension.ExcelType;

public class MyDataType extends ExcelType<MyDataType> {
    private String value;

    @Override
    public MyDataType parseValue(String value) {
        this.value = "新-->" + value + "<--";
        return this;
    }

    @Override
    public String toString() {
        return "MyDataType [value=" + value + "]";
    }
}
