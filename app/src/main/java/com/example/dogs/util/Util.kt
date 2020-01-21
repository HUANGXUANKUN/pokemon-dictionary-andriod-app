package com.example.dogs.util

import android.app.DownloadManager
import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dogs.R


//val PERMISSION_SEND_SMS = 123

fun getProgressDrawable(context: Context) : CircularProgressDrawable {

    // give a spinner image while loading other images
    return CircularProgressDrawable(context).apply{
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_dog_icon) //give a default image if fail to load

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

/**
 * Data binding.
 */
@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?){
    view.loadImage(url, getProgressDrawable(view.context))
}