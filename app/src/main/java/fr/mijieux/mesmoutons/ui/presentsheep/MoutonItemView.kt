package fr.mijieux.mesmoutons.ui.presentsheep

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import fr.mijieux.mesmoutons.R
import fr.mijieux.mesmoutons.ui.EntryType
import fr.mijieux.mesmoutons.ui.MoutonItem
import fr.mijieux.mesmoutons.ui.sheepdetails.SheepDetailsFragmentArgs

class MoutonItemView : LinearLayout {

    private val textView: TextView
    private val colors: ColorStateList

    constructor(context: Context?): super(context) {
        super.setPadding(30,30,30,30)
        textView = TextView(context)
        this.addView(textView)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams = params
        colors = textView.textColors
    }

    fun bindItem(entry: MoutonItem) {
        if (entry.type == EntryType.HEADER) {
            textView.text = entry.headerText
            setBackgroundColor(Color.argb(0xFF,0x6A,0x6A,0x64))
            textView.setTextColor(Color.WHITE)

        } else {
            if (entry.mouton != null) {
                val numero = entry.mouton.numero
                textView.text = numero?.toString() ?: "Pas de numéro"
                textView.setTextColor(colors)

                this.setOnClickListener {
                    val direction = PresentSheepFragmentDirections.actionShowDetails(entry.mouton.id!!)
                    findNavController().navigate(direction)
                }
            }
        }
    }
}
