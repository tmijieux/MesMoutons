package fr.mijieux.mesmoutons.ui.presentsheep

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.mijieux.mesmoutons.R
import fr.mijieux.mesmoutons.core.Mouton
import fr.mijieux.mesmoutons.databinding.FragmentSheepListBinding
import fr.mijieux.mesmoutons.ui.MoutonAdapter

class PresentSheepFragment : Fragment() {

    private lateinit var presentSheepViewModel: PresentSheepViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presentSheepViewModel = ViewModelProvider(this).get(PresentSheepViewModel::class.java)
        val bind = FragmentSheepListBinding.inflate(inflater, container, false)
        val listView: RecyclerView = bind.mainListView
        listView.setHasFixedSize(true)
        listView.layoutManager = LinearLayoutManager(context)

        presentSheepViewModel.moutons.observe(
            viewLifecycleOwner, Observer<List<Mouton>>{ moutons ->
                val adapter = MoutonAdapter(this.context, moutons)
                listView.adapter = adapter
            })
        return bind.root
    }
}