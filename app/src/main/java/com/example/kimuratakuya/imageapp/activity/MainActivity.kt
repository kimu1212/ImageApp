package com.example.kimuratakuya.imageapp.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.example.kimuratakuya.imageapp.R
import android.util.Log

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 0 // TODO: 編集画面への遷移
    private val RESULT_PICK_IMAGEFILE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "画像を選択して下さい", Snackbar.LENGTH_LONG)
                    .setAction("PUSH") {
                        selectPhoto()
                    }
                    .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings ->
                true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * ギャラリーで画像選択
     */
    private fun selectPhoto() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, RESULT_PICK_IMAGEFILE)
    }

    /**
     * @param img: 画像データ
     * 編集画面に画像データを追加
     */
    private fun transitionImgPro(img: Bitmap?) { // TODO: 画像編集
        val intent = Intent(this, ImgproActivity::class.java)
        intent.putExtra("IMG_DATA", img)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            Log.d("TAG", "in resultCode")
            if (resultData?.data != null) {
                try {
                    Log.d("TAG", "in try")
                    val uri: Uri? = resultData.data
                    val pfDescriptor: ParcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
                    val fileDescriptor = pfDescriptor.fileDescriptor
                    val bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                    pfDescriptor.close()
                    imv.setImageBitmap(bmp)
                    transitionImgPro(bmp)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                Toast.makeText(this, "ok", Toast.LENGTH_LONG).show()
            }
        }
    }
}
