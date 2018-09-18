package com.dommy.retrofitframe.network.result;

import com.google.gson.JsonObject;

public class WeatherResult extends BaseResult {
    private int code;
    private String msg;
    private JsonObject data; // 数据部分也是一个bean，用JsonObject代替了

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
