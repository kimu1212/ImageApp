package com.example.kimuratakuya.imageapp.activity

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.kimuratakuya.imageapp.R
import android.util.Log

import kotlinx.android.synthetic.main.activity_imgpro.*
import kotlinx.android.synthetic.main.content_main.*

class ImgproActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("debug", "in New activity")
        setContentView(R.layout.activity_imgpro)
        setSupportActionBar(toolbar)
        val intent = intent
        displayBitmap(intent.extras.get("IMG") as Uri)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    /**
     * @param uri Bitmapのパス
     * ギャラリーで選択した画像の描画
     */
    private fun displayBitmap(uri: Uri?) {
        val pfDescriptor: ParcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = pfDescriptor.fileDescriptor
        val bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        pfDescriptor.close()
        imv.setImageBitmap(bmp)
    }
}
