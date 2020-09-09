package fr.mijieux.mesmoutons.ui.addsheep

import android.annotation.SuppressLint
import android.content.Context
import android.database.DataSetObserver
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.core.view.setPadding
import fr.mijieux.mesmoutons.core.Mouton
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.TEXT_ALIGNMENT_INHERIT
import fr.mijieux.mesmoutons.R


class MotherAdapter(private val context: Context, private val brebis: List<Mouton>) : SpinnerAdapter {

    private fun getTextForPosition(position: Int): String {
        return if (position == 0) {
            return "Inconnue"
        } else {
            brebis[position-1].numero?.toString() ?: "Pas de numéro"
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): TextView {
        val view = if (convertView != null && convertView is TextView) {
             convertView
        } else {
            TextView(context, null, R.style.Widget_AppCompat_TextView_SpinnerItem)
        }
        view.text = getTextForPosition(position)
        view.setSingleLine(true)
        view.ellipsize = TextUtils.TruncateAt.MARQUEE
        view.textAlignment = TEXT_ALIGNMENT_INHERIT
        view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val scale = context.resources.displayMetrics.density
        view.setPadding((12 * scale).toInt())
        return view
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        parent?.setPadding(0, 0, 0, 0)
        val scale = context.resources.displayMetrics.density
        return getView(position, convertView, parent).also { it.setPadding((20*scale).toInt())}
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {/*nothing change in this adapter */ }
    override fun unregisterDataSetObserver(observer: DataSetObserver?) {/* nothing change in this adapter*/ }
    override fun getCount(): Int = brebis.size+1
    override fun getItem(position: Int): Mouton? = if (position>0) brebis[position-1] else null
    override fun getItemId(position: Int): Long = position.toLong()
    override fun hasStableIds(): Boolean = true
    override fun getItemViewType(position: Int): Int = 0
    override fun getViewTypeCount(): Int = 1
    override fun isEmpty(): Boolean = false

}