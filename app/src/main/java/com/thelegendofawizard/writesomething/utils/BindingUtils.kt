package com.thelegendofawizard.writesomething.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.thelegendofawizard.writesomething.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@BindingAdapter("imagebyte")
fun loadImage(view: ImageView, byteArray: ByteArray?) {
    Glide.with(view)
        .load(byteArray)
        .into(view)
}

class BindingUtils(val repository: Repository) {

    companion object {
        @JvmStatic
        @BindingAdapter("imagebyte")
        fun loadImage(view: ImageView, string: String) {
            CoroutineScope(IO).launch {
                val byteArray = repository.databaseGetDataFromString(string)
                Glide.with(view)
                    .load(byteArray)
                    .into(view)
            }
        }
    }
}


