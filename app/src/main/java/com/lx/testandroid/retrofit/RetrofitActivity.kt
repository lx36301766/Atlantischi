package com.lx.testandroid.retrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created on 17/07/2018.

 * @author lx
 */

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestGet()
        requestPost()
    }

    fun requestGet() {
        //步骤4:创建Retrofit对象
        val retrofit = Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build()

        // 步骤5:创建 网络请求接口 的实例
        val request = retrofit.create(GetRequest_Interface::class.java)
        //对 发送请求 进行封装
        val call = request.getCall()

        call.enqueue(object : Callback<Translation> {
            override fun onFailure(call: Call<Translation>?, t: Throwable?) {
                System.out.println("连接失败")
            }

            override fun onResponse(call: Call<Translation>?, response: Response<Translation>?) {
                response?.body()?.show()
            }

        })
    }

    fun requestPost() {

        //步骤4:创建Retrofit对象
        val retrofit = Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
//                .addCallAdapterFactory()
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build()

        // 步骤5:创建 网络请求接口 的实例
        val request = retrofit.create(PostRequest_Interface::class.java)

        //对 发送请求 进行封装(设置需要翻译的内容)
        val call = request.getCall("I love you")

        //步骤6:发送网络请求(异步)
        call.enqueue(object : Callback<Translation1> {
            override fun onFailure(call: Call<Translation1>?, throwable: Throwable?) {
                System.out.println("请求失败")
                System.out.println(throwable?.message)
            }

            override fun onResponse(call: Call<Translation1>?, response: Response<Translation1>?) {
                // 步骤7：处理返回的数据结果：输出翻译的内容
                System.out.println(response?.body()?.translateResult?.get(0)?.tgt)
            }

        })
    }


}
