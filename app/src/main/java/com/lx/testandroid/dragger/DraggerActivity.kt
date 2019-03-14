package com.lx.testandroid.dragger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lx.testandroid.App
import com.lx.testandroid.R

class DraggerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dragger)
    }

    private val appComponent: AppComponent? = null

//    private fun setupCompoent() {
//        appComponent = DaggerAppComponent.builder()
//                .githubApiModule(GithubApiModule())
//                .appModule(AppModule(this))
//                .build()
//    }
//
//    fun getsInstance(): App {
//        return sInstance
//    }
//
//    fun getAppComponent(): AppComponent {
//        return appComponent
//    }

}
