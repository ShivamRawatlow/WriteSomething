package com.thelegendofawizard.writesomething.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if(!url.isNullOrBlank()){
        Glide.with(view)
            .load(url)
            .centerCrop()
            .into(view)
    }
}

