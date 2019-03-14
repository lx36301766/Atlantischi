package com.lx.testandroid.common

import java.util.Random

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import com.lx.testandroid.R
import com.lx.testandroid.util.NativeSupplier
import com.lx.testandroid.view.VerifyCodeView

class CheckCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_code)

        val imageView = findViewById(R.id.verify_code_img) as ImageView
        val nativeSupplier = NativeSupplier(this)
        imageView.setImageBitmap(nativeSupplier.getVerifyCodeBitmap("" + randomInterval(1000, 9999), 137, 50))

        val btn = findViewById(R.id.btn) as Button
        btn.setOnClickListener {
            val width = NativeSupplier.sp2px(91.1f).toInt()
            val height = NativeSupplier.sp2px(33.3f).toInt()
            imageView.setImageBitmap(nativeSupplier.getVerifyCodeBitmap("" + randomInterval(1000, 9999), width, height))
        }

        val intent = intent
    }

    private fun randomInterval(min: Int, max: Int): Int {
        return Random().nextInt(max - min + 1) + min
    }

}
