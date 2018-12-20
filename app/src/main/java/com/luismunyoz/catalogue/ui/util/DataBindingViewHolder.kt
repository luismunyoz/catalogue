package com.luismunyoz.catalogue.ui.util

import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import android.graphics.drawable.Drawable
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.luismunyoz.catalogue.BR

class DataBindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Any, listener: Any){
        binding.setVariable(BR.item, item)
        //binding.setVariable(BR.listener, listener)
        binding.executePendingBindings()
    }

    companion object {

        const val DEFAULT_SIZE_MULTIPLIER = 0.4f

        @BindingAdapter("srcCompat")
        @JvmStatic
        fun setImage(imageView: ImageView, resourceId: Int){
            val drawable : Drawable? = VectorDrawableCompat.create(imageView.resources, resourceId,
                    imageView.context.theme)
            imageView.setImageDrawable(drawable)
        }

        @BindingAdapter("srcCompat")
        @JvmStatic
        fun setImage(imageView: ImageView, drawable: Drawable){
            imageView.setImageDrawable(drawable)
        }

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(imageView: ImageView, url: String){
            Glide.with(imageView.context).load(url).into(imageView)
        }

        @BindingAdapter("reducedImageUrl")
        @JvmStatic
        fun reducedLoadImage(imageView: ImageView, url: String){
            Glide.with(imageView.context).load(url).sizeMultiplier(DEFAULT_SIZE_MULTIPLIER)
                    .into(imageView)
        }
    }
}