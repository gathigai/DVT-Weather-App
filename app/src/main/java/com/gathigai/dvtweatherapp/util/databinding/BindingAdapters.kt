package com.gathigai.dvtweatherapp.util.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gathigai.dvtweatherapp.util.getDayOfWeek

@BindingAdapter("app:showDate")
fun showDay(view: TextView, text: String?){
    view.text = getDayOfWeek(text?.toInt()!!)
}
