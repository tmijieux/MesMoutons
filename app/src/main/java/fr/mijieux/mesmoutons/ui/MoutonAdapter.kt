package fr.mijieux.mesmoutons.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.mijieux.mesmoutons.core.Mouton
import fr.mijieux.mesmoutons.core.TypeMouton
import fr.mijieux.mesmoutons.ui.presentsheep.MoutonItemView

enum class EntryType(val value:Int) {
    HEADER(0),
    SHEEP(1),
}

class MoutonItem(
    val type: EntryType, // 1 = header , 0 = mouton
    val mouton: Mouton? = null,
    val headerText: String?= null
) {

}

class MoutonViewHolder(val view: MoutonItemView): RecyclerView.ViewHolder(view) {
}

class MoutonAdapter(
        private val context: Context?,
        myList: List<Mouton>
    ) : RecyclerView.Adapter<MoutonViewHolder>()  {


    private val myItemList: MutableList<MoutonItem> = mutableListOf<MoutonItem>()

    init {
        val moutons = myList.sortedBy { -it.type.value }
        var i = -1
        for (mouton in moutons) {
            if (i != mouton.type.value) {

                i = mouton.type.value
                val typeName = mouton.type.toString().toLowerCase().capitalize()
                myItemList.add(MoutonItem(EntryType.HEADER, headerText = typeName))
            }
            myItemList.add(MoutonItem(EntryType.SHEEP,mouton=mouton))
        }
    }

    override fun getItemCount(): Int {
        return myItemList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return myItemList[position].type.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoutonViewHolder {

        return MoutonViewHolder(MoutonItemView(parent.context))
    }

    override fun onBindViewHolder(holder: MoutonViewHolder, position: Int) {
        val item = myItemList[position]
        holder.view.bindItem(item)
    }
}