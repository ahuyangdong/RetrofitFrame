# RetrofitFrame

![platform](https://img.shields.io/badge/platform-Android-lightgrey.svg)
![Gradle](https://img.shields.io/badge/Gradle-3.1.2-brightgreen.svg)
![ide](https://img.shields.io/badge/IDE-Android%20Studio-brightgreen.svg)
![progress](http://progressed.io/bar/100?title=completed)
[![last commit](https://img.shields.io/github/last-commit/ahuyangdong/RetrofitFrame.svg)](https://github.com/ahuyangdong/RetrofitFrame/commits/master)
![repo size](https://img.shields.io/github/repo-size/ahuyangdong/RetrofitFrame.svg)
[![Licence](https://img.shields.io/github/license/ahuyangdong/RetrofitFrame.svg)](https://github.com/ahuyangdong/RetrofitFrame/blob/master/LICENSE)

Android网络框架Retrofit2使用封装，框架主要包括：
- Get请求
- Post请求
- 文件上传
- 文件下载

演示效果：

![image](https://github.com/ahuyangdong/RetrofitFrame/raw/master/images/demo.gif)
## 主要包含
1. 添加HTTPS支持
2. 自定义OkHttpClient
3. 封装各类Get/Post请求方法
4. 显示文件下载进度等

## 封装的方法
发送GET网络请求
> com.dommy.retrofitframe.network.RetrofitRequest#sendGetRequest

发送post网络请求
> com.dommy.retrofitframe.network.RetrofitRequest#sendPostRequest

发送上传文件网络请求
> com.dommy.retrofitframe.network.RetrofitRequest#fileUpload

文件下载
> com.dommy.retrofitframe.network.RetrofitRequest#fileDownload


## 讲解参见
https://blog.csdn.net/ahuyangdong/article/details/82760382