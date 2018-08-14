package com.lx.testandroid.dragger

import android.app.Application
import dagger.Module
import dagger.Provides



/**
 * Created on 14/08/2018.

 * @author lx
 */

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }
}
