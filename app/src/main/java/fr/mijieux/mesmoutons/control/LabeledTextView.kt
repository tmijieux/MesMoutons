package fr.mijieux.mesmoutons.control

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import fr.mijieux.mesmoutons.R

class LabeledTextView(context: Context, attributesSet: AttributeSet): LinearLayoutCompat(context, attributesSet) {

    val label = TextView(context)
    val mainText = TextView(context)

    var title: CharSequence
        get() = label.text
        set(value) { label.text = value }
    var content: CharSequence
        get() = mainText.text
        set(value) { mainText.text = value }

    init {
        orientation = VERTICAL
        val scale = context.resources.displayMetrics.density
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom+(12*scale).toInt())
        val grey = Color.parseColor("#777777")
        label.setTextColor(grey)

        val black = Color.parseColor("#121212")
        mainText.setTextColor(black)

        context.theme.obtainStyledAttributes(attributesSet, R.styleable.LabeledTextView, 0, 0).apply {
            try {
                label.text = getString(R.styleable.LabeledTextView_label) ?: ""
                mainText.text = getString(R.styleable.LabeledTextView_main_text) ?: ""
            } finally {
                recycle()
            }
        }
        this.addView(label)
        this.addView(mainText)
    }
}