package com.dommy.retrofitframe.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Get请求封装
 */
public interface GetRequest {

    /**
     * 发送Get请求请求
     * @param url URL路径
     * @return
     */
    @GET
    Call<ResponseBody> getUrl(@Url String url);

}
