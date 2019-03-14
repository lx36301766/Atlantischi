package com.lx.testandroid.rx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lx.testandroid.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.util.*

@Suppress("all")
class RxTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_test)

        val list = mutableListOf(
                LaunchImage().apply {
                    tag = "a"
                    showRule = 2
                    showRuleLimit = 3
                    priority = 2
                    showCount = 2
                },
                LaunchImage().apply {
                    tag = "b"
                    showRule = 1
                    showRuleLimit = 3
                    priority = 0
                    showCount = 3
                },
                LaunchImage().apply {
                    tag = "c"
                    showRule = 2
                    showRuleLimit = 3
                    priority = 1
                    showCount = 3
                },
                LaunchImage().apply {
                    tag = "d"
                    showRule = 2
                    showRuleLimit = 3
                    priority = 3
                    showCount = 3
                }
        )
        list.sort()
        Log.d("lxx", list.toString())
        Observable.fromIterable(list)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
//                .filter { launchImage ->
//                    val currentTime = System.currentTimeMillis() / 1000
//                    val startTime = launchImage.showTimeBegin.toLong()
//                    val endTime = launchImage.showTimeEnd.toLong()
//                    currentTime in (startTime + 1)..(endTime - 1)
//                }
                .takeUntil { launchImage ->
                    Log.d("lxx", "takeUntil: $launchImage")
                    if (launchImage.showRule == 1) {
                        Log.d("lxx", "match")
                        return@takeUntil true
                    } else if (launchImage.showRule == 2) {
                        if (launchImage.showCount < launchImage.showRuleLimit) {
                            Log.d("lxx", "match")
                            return@takeUntil true
                        }
                    } else {
//                        JMLog.w(TAG, "Unknown rule -> " + launchImage.showRule)
                    }
                    return@takeUntil false
                }
                .lastElement()
                .subscribe { launchImage ->
                    Log.d("lxx", "subscribe -> $launchImage")
                }
//                .subscribe(object : Observer<LaunchImage>{
//
//                    override fun onComplete() {
//                        Log.d("lxx", "onComplete")
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                        Log.d("lxx", "onSubscribe")
//                    }
//
//                    override fun onNext(launchImage: LaunchImage) {
//                        Log.d("lxx", "onNext -> $launchImage")
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Log.d("lxx", "onComplete")
//                    }
//                })

    }



}
