package com.pritikjain.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pritikjain.easyfood.R
import com.pritikjain.easyfood.activities.CategoryMealsActivity
import com.pritikjain.easyfood.adapters.CategoriesAdapter
import com.pritikjain.easyfood.databinding.FragmentCategoriesBinding
import com.pritikjain.easyfood.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: HomeViewModel

    companion object {
        const val CATEGORY_NAME = "com.pritikjain.easyfood.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        categoriesAdapter = CategoriesAdapter();
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        prepareRecyclerView()

        //viewModel.getCategories();
        observerCategories()
        onCategoryClick()

    }





    private fun prepareRecyclerView() {
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }


}