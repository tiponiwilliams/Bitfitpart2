package com.example.bitfitpart2.ui.entries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfitpart2.R
import com.example.bitfitpart2.data.BitFitDatabase
import com.example.bitfitpart2.data.EntryEntity
import com.example.bitfitpart2.databinding.FragmentEntriesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EntriesFragment : Fragment() {

    private var _binding: FragmentEntriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EntriesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EntriesListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        val dao = BitFitDatabase.getInstance(requireContext()).entryDao()

        viewLifecycleOwner.lifecycleScope.launch {
            dao.getAllEntriesFlow().collectLatest { entries: List<EntryEntity> ->
                adapter.submitList(entries)
                binding.emptyText.visibility = if (entries.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        binding.fabAdd.setOnClickListener {
            // show a quick toast so you know it's firing (optional)
            // Toast.makeText(requireContext(), "FAB clicked", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_entriesFragment_to_addEntryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
