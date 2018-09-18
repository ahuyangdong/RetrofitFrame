package com.dommy.retrofitframe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dommy.retrofitframe.network.FileRequest;
import com.dommy.retrofitframe.network.RetrofitRequest;
import com.dommy.retrofitframe.network.result.BaseResult;
import com.dommy.retrofitframe.network.result.WeatherResult;
import com.dommy.retrofitframe.util.Constant;
import com.dommy.retrofitframe.util.StorageUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final int DOWNLOAD_ING = 1;// 下载中
    private static final int DOWNLOAD_FINISH = 2;// 下载结束
    private static final int DOWNLOAD_ERROR = -1;// 下载出错

    @BindView(R.id.txt_content)
    TextView tvContent;

    private String savePath;// 下载保存路径
    private int progress;// 记录进度条数量
    private int totalByte; // 总大小
    private int downByte; // 已下载

    private boolean cancelUpdate = false;// 是否取消更新
    private String fileName = "news.apk"; // 下载存储的文件名

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_ING:
                    // 正在下载
                    showProgress();
                    break;
                case DOWNLOAD_FINISH:
                    // 下载完成
                    onDownloadFinish();
                    break;
                case DOWNLOAD_ERROR:
                    // 出错
                    onDownloadError();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    /**
     * Get请求
     */
    @OnClick(R.id.btn_get)
    void onGet() {
        // 这里放显示loading
        tvContent.setText("loading...");

        String url = Constant.URL_WEATHER;
        url += "?city=合肥";
        RetrofitRequest.sendGetRequest(url, WeatherResult.class, new RetrofitRequest.ResultHandler<WeatherResult>(this) {
            @Override
            public void onBeforeResult() {
                // 这里可以放关闭loading
            }

            @Override
            public void onResult(WeatherResult weatherResult) {
                String weather = new Gson().toJson(weatherResult);
                tvContent.setText(weather);
            }

            @Override
            public void onAfterFailure() {
                // 这里可以放关闭loading
            }
        });
    }

    /**
     * Post请求
     */
    @OnClick(R.id.btn_post)
    void onPost() {
        // 这里放显示loading
        tvContent.setText("loading...");

        String url = Constant.URL_LOGIN;
        Map<String, String> paramMap = new HashMap<>(1);
        paramMap.put("key", "00d91e8e0cca2b76f515926a36db68f5");
        paramMap.put("phone", "13594347817");
        paramMap.put("passwd", "123456");
        RetrofitRequest.sendPostRequest(url, paramMap, WeatherResult.class, new RetrofitRequest.ResultHandler<WeatherResult>(this) {
            @Override
            public void onBeforeResult() {
                // 这里可以放关闭loading
            }

            @Override
            public void onResult(WeatherResult weatherResult) {
                String weather = new Gson().toJson(weatherResult);
                tvContent.setText(weather);
            }

            @Override
            public void onAfterFailure() {
                // 这里可以放关闭loading
            }
        });
    }

    /**
     * 文件上传请求
     *  <p>没有找到文件上传的接口，仅写下代码示例</p>
     */
    @OnClick(R.id.btn_upload)
    void onFileUpload() {
        // 这里放显示loading
        tvContent.setText("上传中...");
        File file = null;
        try {
            // 通过新建文件替代文件寻址
            file = File.createTempFile("abc", "txt");
        } catch (IOException e) {
        }
        String url = Constant.URL_LOGIN;
        RetrofitRequest.fileUpload(url, file, BaseResult.class, new RetrofitRequest.ResultHandler<BaseResult>(this) {
            @Override
            public void onBeforeResult() {
                // 这里可以放关闭loading
            }

            @Override
            public void onResult(BaseResult baseResult) {
                tvContent.setText("上传成功");
            }

            @Override
            public void onAfterFailure() {
                // 这里可以放关闭loading
            }
        });
    }

    /**
     * 文件下载
     *  <p>用百度手机助手APK文件作为下载示例</p>
     */
    @OnClick(R.id.btn_download)
    void onFileDownload() {
        // 这里放显示loading
        tvContent.setText("下载中...");

        RetrofitRequest.fileDownload(Constant.URL_DOWNLOAD, new RetrofitRequest.DownloadHandler() {
            @Override
            public void onBody(ResponseBody body) {
                if (!writeResponseBodyToDisk(body)) {
                    mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onError() {
                mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
            }
        });
    }

    /**
     * 写文件入磁盘
     *
     * @param body 请求结果
     * @return boolean 是否下载写入成功
     */
    private boolean writeResponseBodyToDisk(ResponseBody body) {
        savePath = StorageUtil.getDownloadPath();
        File apkFile = new File(savePath, fileName);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            byte[] fileReader = new byte[4096];
            // 获取文件大小
            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(apkFile);

            // byte转Kbyte
            BigDecimal bd1024 = new BigDecimal(1024);
            totalByte = new BigDecimal(fileSize).divide(bd1024, BigDecimal.ROUND_HALF_UP).setScale(0).intValue();

            // 只要没有取消就一直下载数据
            while (!cancelUpdate) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    // 下载完成
                    mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                    break;
                }
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
                // 计算进度
                progress = (int) (((float) (fileSizeDownloaded * 100.0 / fileSize)));
                downByte = new BigDecimal(fileSizeDownloaded).divide(bd1024, BigDecimal.ROUND_HALF_UP).setScale(0).intValue();
                // 子线程中，借助handler更新界面
                mHandler.sendEmptyMessage(DOWNLOAD_ING);
            }
            outputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 显示下载进度
     */
    private void showProgress() {
        String text = progress + "%  |  " + downByte + "Kb / " + totalByte + "Kb";
        tvContent.setText(text);
    }

    /**
     * 下载出错处理
     */
    private void onDownloadError() {
        tvContent.setText("下载出错");
    }

    /**
     * 下载完成
     */
    private void onDownloadFinish() {
        tvContent.setText("下载已完成");
    }
}
