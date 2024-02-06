package com.pritikjain.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pritikjain.easyfood.R
import com.pritikjain.easyfood.adapters.MealsAdapter
import com.pritikjain.easyfood.databinding.FragmentSearchBinding
import com.pritikjain.easyfood.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchMealAdapter : MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        searchMealAdapter = MealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root;

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareSearchRecyclerView()

        binding.imageSearchArrow.setOnClickListener {
            searchMeals();
        }

        observeSearchedMealsLiveData()


        // for search to happen itself
        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(searchQuery.toString())
            }
        }

    }

    private fun prepareSearchRecyclerView() {
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(activity,2, GridLayoutManager.VERTICAL, false)
            adapter = searchMealAdapter

        }
    }
    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if(searchQuery.isNotEmpty()){
           viewModel.searchMeals(searchQuery)
        } else {
            Toast.makeText(requireActivity(),"Please type meal name in the search edit box ", Toast.LENGTH_LONG).show()
        }
    }
    private fun observeSearchedMealsLiveData() {
        viewModel.observerSearchMealsLiveData().observe(viewLifecycleOwner, Observer { mealsList ->
            searchMealAdapter.differ.submitList(mealsList)
        })
    }

}