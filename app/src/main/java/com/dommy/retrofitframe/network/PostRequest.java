package com.dommy.retrofitframe.network;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Post请求封装
 */
public interface PostRequest{

    /**
     * 发送Post请求
     * @param url URL路径
     * @param requestMap 请求参数
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> postMap(@Url String url, @FieldMap Map<String, String> requestMap);

}
