package com.lx.testandroid.dragger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class DraggerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dragger)
    }

    private val appComponent: AppComponent? = null

    private fun setupCompoent() {
        appComponent = DaggerAppComponent.builder()
                .githubApiModule(GithubApiModule())
                .appModule(AppModule(this))
                .build()
    }

    fun getsInstance(): AppApplication {
        return sInstance
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

}
