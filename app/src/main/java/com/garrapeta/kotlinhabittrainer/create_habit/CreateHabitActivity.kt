package com.garrapeta.kotlinhabittrainer.create_habit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.garrapeta.kotlinhabittrainer.R
import kotlinx.android.synthetic.main.activity_create_habit.*
import java.io.IOException

class CreateHabitActivity : AppCompatActivity() {

    private val TAG = CreateHabitActivity::class.java.simpleName

    private val REQUEST_CODE_CHOOSE_IMG = 1

    private var imageBitmap : Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)

        btn_habit_choose_image.setOnClickListener { onChooseImage() }
        btn_habit_save.setOnClickListener { onSaveHabit() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_CHOOSE_IMG -> onImageChosen(resultCode, data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun onChooseImage() {
        val targetIntent = Intent()
        targetIntent.type = "image/*"
        targetIntent.action = Intent.ACTION_GET_CONTENT

        val createChooserIntent : Intent = Intent.createChooser(targetIntent, "Choose image for habit")
        startActivityForResult(createChooserIntent, REQUEST_CODE_CHOOSE_IMG)

        Log.d(TAG, "Opening image chooser")
    }

    private fun onImageChosen(resultCode: Int, dataIntent: Intent?) {
        if (Activity.RESULT_OK != resultCode) {
            return
        }

        if (dataIntent != null && dataIntent.data != null) {
            val bitmap = tryReadBitmap(dataIntent.data)

            bitmap?.let {
                Log.d(TAG, "Bitmap set")
                img_habit_image.setImageBitmap(it)
                imageBitmap = bitmap
            }
        }
    }

    private fun tryReadBitmap(data: Uri?): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, data)
        } catch (e : IOException) {
            Log.e(TAG, "Error decoding bitmap", e)
            null
        }
    }


    private fun onSaveHabit() {

        if (et_habit_title.text.toString().isBlank() || et_habit_description.text.toString().isBlank()) {
            displayError("Title and description should not be null")
            return
        } else if (imageBitmap == null) {
            displayError("Picture should not be null")
            return
        }

        TODO("Store the habit")

    }

    private fun displayError(message: String) {
        with (txt_habit_create_error) {
            visibility = View.VISIBLE
            text = message
        }
    }

}