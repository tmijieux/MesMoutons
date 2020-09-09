package fr.mijieux.mesmoutons.ui.deadsheep

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
import fr.mijieux.mesmoutons.ui.MoutonAdapter

class DeadSheepFragment : Fragment() {

    private lateinit var deadSheepViewModel: DeadSheepViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        deadSheepViewModel = ViewModelProvider(this).get(DeadSheepViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sheep_list, container, false)
        val listView: RecyclerView = root.findViewById(R.id.main_list_view)
        listView.setHasFixedSize(true)
        listView.layoutManager = LinearLayoutManager(context)


        deadSheepViewModel.moutons.observe(
            viewLifecycleOwner, Observer<List<Mouton>>{ moutons ->
                Log.d("debug", "coucou moutons = ${moutons}")
                val adapter = MoutonAdapter(this.context, moutons)
                listView.adapter = adapter
            })
        return root
    }
}