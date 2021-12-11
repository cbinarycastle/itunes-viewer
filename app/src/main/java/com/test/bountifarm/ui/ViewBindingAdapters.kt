package com.test.bountifarm.ui

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
fun imageUrl(imageView: ImageView, imageUrl: String, placeholder: Drawable?) {
    Glide.with(imageView)
        .load(imageUrl)
        .placeholder(placeholder)
        .into(imageView)
}

@BindingAdapter("duration")
fun duration(textView: TextView, duration: Duration) {
    val seconds = duration.seconds
    val minutesPart = seconds % 3600 / 60
    val secondsPart = seconds % 60
    val durationForDisplay = String.format("%02d:%02d", minutesPart, secondsPart)
    textView.text = durationForDisplay
}

@BindingAdapter("year")
fun year(textView: TextView, dateTime: LocalDateTime) {
    textView.text = dateTime.year.toString()
}