package com.example.examenreproductor.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import com.example.examenreproductor.R
import com.example.examenreproductor.databinding.CustomButtonBinding

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var b: CustomButtonBinding
    init {
        b = CustomButtonBinding.inflate(LayoutInflater.from(context), this, true)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
        setText(attributes.getString(R.styleable.CustomButton_buttonText))
        setColor(attributes.getColor(R.styleable.CustomButton_buttonColor, 0))
        attributes.recycle()
    }

    fun setText(text: String?) {
        b.internalButton.text = text
    }

    fun setColor(color: Int) {
        val cornerRadius = 30f * resources.displayMetrics.density

        // Determine text color based on background brightness
        val brightness = Color.red(color) * 0.299 + Color.green(color) * 0.587 + Color.blue(color) * 0.114
        val textColor = if (brightness > 186) context.getColor(R.color.primary_color) else Color.WHITE
        b.internalButton.setTextColor(textColor)

        // Default state drawable
        val defaultDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(color)
            this.cornerRadius = cornerRadius
        }

        // Pressed state drawable (darker color using HSV)
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= 0.8f // Reduce brightness
        val darkerColor = Color.HSVToColor(hsv)

        val pressedDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(darkerColor)
            this.cornerRadius = cornerRadius
        }

        // Focused state drawable (lighter color)
        val focusedColor = Color.rgb(
            (Color.red(color) * 0.9f + 255 * 0.1f).toInt(),
            (Color.green(color) * 0.9f + 255 * 0.1f).toInt(),
            (Color.blue(color) * 0.9f + 255 * 0.1f).toInt()
        )
        val focusedDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(focusedColor)
            this.cornerRadius = cornerRadius
        }

        val stateListDrawable = StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
            addState(intArrayOf(android.R.attr.state_focused), focusedDrawable)
            addState(intArrayOf(), defaultDrawable) // Default state
        }

        b.internalButton.background = stateListDrawable
    }

    override fun setOnClickListener(l: OnClickListener?) {
        b.internalButton.setOnClickListener(l)
    }
}
