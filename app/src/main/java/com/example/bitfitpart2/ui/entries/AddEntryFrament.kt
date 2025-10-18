package com.example.bitfitpart2.ui.entries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bitfitpart2.data.BitFitDatabase
import com.example.bitfitpart2.data.EntryEntity
import com.example.bitfitpart2.databinding.FragmentAddEntryBinding
import kotlinx.coroutines.launch
import java.util.*

class AddEntryFragment : Fragment() {
    private var _binding: FragmentAddEntryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val name = binding.foodNameInput.text.toString().trim()
            val caloriesText = binding.caloriesInput.text.toString().trim()

            if (name.isEmpty() || caloriesText.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val calories = caloriesText.toIntOrNull()
            if (calories == null) {
                Toast.makeText(requireContext(), "Calories must be a number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val entry = EntryEntity(
                calories = calories,
                date = Date().time,
                note = name
            )

            lifecycleScope.launch {
                BitFitDatabase.getInstance(requireContext()).entryDao().insert(entry)
                Toast.makeText(requireContext(), "Saved!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
