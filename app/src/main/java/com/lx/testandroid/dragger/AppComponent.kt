package com.lx.testandroid.dragger

import dagger.Component

/**
 * Created on 14/08/2018.

 * @author lx
 */

@Component(modules = [(AppModule::class), (GithubApiModule::class)])
interface AppComponent {
    // inject what
//    fun inject(activity: ReposListActivity)
}