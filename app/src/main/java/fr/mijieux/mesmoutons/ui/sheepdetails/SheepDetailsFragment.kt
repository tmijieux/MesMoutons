package fr.mijieux.mesmoutons.ui.sheepdetails

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.mijieux.mesmoutons.R
import fr.mijieux.mesmoutons.core.Mouton
import fr.mijieux.mesmoutons.databinding.SheepDetailsFragmentBinding
import java.time.format.DateTimeFormatter

class SheepDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = SheepDetailsFragment()
    }

    private lateinit var viewModel: SheepDetailsViewModel
    private lateinit var binding: SheepDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SheepDetailsFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idMouton = arguments?.getLong("idMouton") ?: -1L
        viewModel = ViewModelProvider(this, SheepDetailsViewModel.SheepDetailsViewModelFactory(idMouton)).get(SheepDetailsViewModel::class.java)

        viewModel.moutonData.observe(viewLifecycleOwner) here@{ it: Mouton? ->
            if (it == null) {
                return@here
            }

            val fmt = DateTimeFormatter.ofPattern("dd LLLL yyyy")

            binding.sheepNumber.content = it.numero?.toString() ?: "Pas de numéro"
            binding.birthDay.content = it.dateNaissance?.format(fmt) ?: "Inconnue"
            binding.mother.content = it.parentId?.toString() ?: "Inconnue"
            binding.type.content = it.type.toString().toLowerCase().capitalize()
            binding.sexe.content = it.sexe.toString().toLowerCase().capitalize()
            binding.buyDate.content = it.dateAchat?.format(fmt) ?: "N/A"
            binding.buyPrice.content = it.prixAchat?.toString() ?: "N/A"
            binding.vendorName.content = it.nomVendeur ?: "N/A"
        }
        // TODO: Use the ViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                AlertDialog
                    .Builder(requireContext())
                    .setMessage("Êtes vous sur de vouloir supprimer ce mouton ?")
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        viewModel.deleteMouton()
                        findNavController().navigateUp()
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}