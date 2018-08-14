package com.lx.testandroid.dragger

import android.app.Application
import android.icu.util.TimeUnit
import com.lx.testandroid.R
import dagger.Module
import retrofit2.Retrofit
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created on 14/08/2018.

 * @author lx
 */

@Module
class GithubApiModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient()
//        okHttpClient.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
//        okHttpClient.readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
        return okHttpClient
    }

    @Provides
    fun provideRetrofit(application: Application, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("api_github")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build()
    }

    @Provides
    protected fun provideGitHubService(retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java!!)
    }

}