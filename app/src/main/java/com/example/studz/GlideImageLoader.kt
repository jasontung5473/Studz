package com.example.studz

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.studz.R
import java.io.IOException

class GlideImageLoader(val context: Context) {

    fun loadImage(image: Any, imageView: ImageView) {
        try {
            // Load the user image in the ImageView.
            //********** need to import dependencies ***********
            Glide
                .with(context)
                .load(image)
                .centerCrop()
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher2)
                .into(imageView)
        }
        catch(e: IOException) {
            e.printStackTrace()
        }
    }

}