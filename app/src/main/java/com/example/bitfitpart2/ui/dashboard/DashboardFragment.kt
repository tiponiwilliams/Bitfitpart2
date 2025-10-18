package com.example.bitfitpart2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bitfitpart2.data.BitFitDatabase
import com.example.bitfitpart2.data.EntryEntity
import com.example.bitfitpart2.databinding.FragmentDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = BitFitDatabase.getInstance(requireContext()).entryDao()

        viewLifecycleOwner.lifecycleScope.launch {
            val entries = withContext(Dispatchers.IO) { dao.getAllEntriesFlow().first() }
            applySummary(entries)
        }
    }

    private fun applySummary(entries: List<EntryEntity>) {
        val total = entries.size
        val avg = if (entries.isNotEmpty()) entries.map { it.calories }.average().roundToInt() else 0
        val max = entries.maxOfOrNull { it.calories } ?: 0
        val min = entries.minOfOrNull { it.calories } ?: 0

        binding.totalCountValue.text = total.toString()
        binding.averageCaloriesValue.text = "$avg kcal"
        binding.maxCaloriesValue.text = "$max kcal"
        binding.minCaloriesValue.text = "$min kcal"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
