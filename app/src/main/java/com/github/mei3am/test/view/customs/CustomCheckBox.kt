package com.github.mei3am.test.view.customs

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatCheckBox
import com.github.mei3am.test.R

class CustomCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatCheckBox(context, attrs) {

    init {
        attrs?.let {
            initAttrs(context, attrs)
        }
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        val attributeArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCheckBox)

        var drawableLeft: Drawable? = null
        var drawableRight: Drawable? = null
        var drawableBottom: Drawable? = null
        var drawableTop: Drawable? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawableLeft = attributeArray.getDrawable(R.styleable.CustomCheckBox_drawableLeftCompat)
            drawableRight = attributeArray.getDrawable(R.styleable.CustomCheckBox_drawableRightCompat)
            drawableBottom = attributeArray.getDrawable(R.styleable.CustomCheckBox_drawableBottomCompat)
            drawableTop = attributeArray.getDrawable(R.styleable.CustomCheckBox_drawableTopCompat)
        } else {
            val drawableLeftId = attributeArray.getResourceId(R.styleable.CustomCheckBox_drawableLeftCompat, -1)
            val drawableRightId = attributeArray.getResourceId(R.styleable.CustomCheckBox_drawableRightCompat, -1)
            val drawableBottomId = attributeArray.getResourceId(R.styleable.CustomCheckBox_drawableBottomCompat, -1)
            val drawableTopId = attributeArray.getResourceId(R.styleable.CustomCheckBox_drawableTopCompat, -1)

            val minusOne = -1
            if (drawableLeftId != minusOne)
                drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId)
            if (drawableRightId != minusOne)
                drawableRight = AppCompatResources.getDrawable(context, drawableRightId)
            if (drawableBottomId != minusOne)
                drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId)
            if (drawableTopId != minusOne)
                drawableTop = AppCompatResources.getDrawable(context, drawableTopId)

        }
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
        attributeArray.recycle()
    }
}
