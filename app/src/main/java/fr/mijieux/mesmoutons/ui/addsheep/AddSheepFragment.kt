package fr.mijieux.mesmoutons.ui.addsheep

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.mijieux.mesmoutons.R
import fr.mijieux.mesmoutons.core.*
import fr.mijieux.mesmoutons.databinding.AddSheepFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext



class AddSheepFragment : Fragment(), CoroutineScope {

    companion object {
        fun newInstance() = AddSheepFragment()
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun saveMouton(mouton: Mouton, dao: MoutonDao) {
        launch {
            try {
                dao.insertAll(mouton)
                findNavController().navigateUp()
            } catch (e: Exception) {
                AlertDialog
                    .Builder(requireContext())
                    .setTitle("Erreur")
                    .setMessage("Ce numéro est déjà utilisé")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
        }
    }

    private lateinit var viewModel: AddSheepViewModel
    private lateinit var born: AddSheepFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        born = AddSheepFragmentBinding.inflate(inflater, container, false)
        return born.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddSheepViewModel::class.java)
        // TODO: Use the ViewModel

        super.onCreate(savedInstanceState)

        val mouton = Mouton()
        val dao = Database.getInstance(requireContext().applicationContext).moutonDao()
        val now = LocalDate.now()
        val fmt = DateTimeFormatter.ofPattern("dd LLLL yyyy")
        val startYear = now.year
        val startMonth = now.monthValue
        val startDay = now.dayOfMonth

        dao.getAllGenre(SexeMouton.FEMELLE.value).observe(viewLifecycleOwner, Observer { moutons ->

            // val adapter = ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, myIds)
            val adapter = MotherAdapter(requireContext(), moutons)
            born.numeroMere.adapter = adapter
        })

        val dialog = DatePickerDialog(requireContext(), { _, year, month, day ->
            val date = LocalDate.of(year, month + 1, day)
            born.dateNaissance.setText(date.format(fmt))
            mouton.dateNaissance = LocalDateTime.of(year, month + 1, day, 0, 0, 0)
        }, startYear, startMonth - 1, startDay)
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.clearDate)) { _, _ ->
            born.dateNaissance.setText("")
            mouton.dateNaissance = null
        }
        val dialog2 = DatePickerDialog(requireContext(), { _, year, month, day ->
            val date = LocalDate.of(year, month + 1, day)
            born.dateAchat.setText(date.format(fmt))
            mouton.dateAchat = LocalDateTime.of(year, month + 1, day, 0, 0, 0)
        }, startYear, startMonth - 1, startDay)
        dialog2.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.clearDate)) { _, _ ->
            born.dateAchat.setText("")
            mouton.dateAchat = null
        }

        born.dateNaissance.setOnClickListener { dialog.show() }
        born.dateAchat.setOnClickListener { dialog2.show() }


        born.typeRadio.setOnCheckedChangeListener { _, checkedId ->
            val canChoose = checkedId !in setOf(R.id.belier, R.id.brebis)
            born.sexeRadio.isEnabled = canChoose
            born.femelle.isEnabled = canChoose
            born.male.isEnabled = canChoose

            if (!canChoose) {
                born.male.isChecked = checkedId == R.id.belier
                born.femelle.isChecked = checkedId == R.id.brebis
            }
            born.sexeRadio.visibility = if (canChoose) { View.VISIBLE } else { View.GONE }
            mouton.type = when (checkedId) {
                R.id.belier -> TypeMouton.BELIER
                R.id.brebis -> TypeMouton.BREBIS
                R.id.agneau -> TypeMouton.AGNEAU
                else -> TypeMouton.INDETERMINE
            }
        }
        born.sexeRadio.setOnCheckedChangeListener { _, checkedId ->
            mouton.sexe = when (checkedId) {
                R.id.male -> SexeMouton.MALE
                R.id.femelle -> SexeMouton.FEMELLE
                else -> SexeMouton.INCONNU
            }
        }

        born.confirmButton.setOnClickListener here@{
            val text = born.numberInput.text.toString()
            mouton.numero = text.toLongOrNull()

            val pos: Int = born.numeroMere.selectedItemPosition
            val adapter = born.numeroMere.adapter as MotherAdapter
            mouton.parentId = if (pos > 0) { adapter.getItem(pos)!!.id } else { null }
            val prixStr = born.prix.text.toString()
            mouton.prixAchat = prixStr.toFloatOrNull()

            val vendorName = born.nomVendeur.text.toString()
            mouton.nomVendeur = if (vendorName.isNotBlank()) vendorName else null

            saveMouton(mouton, dao)
        }
    }

}