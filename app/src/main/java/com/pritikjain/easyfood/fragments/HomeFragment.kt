package com.pritikjain.easyfood.fragments

import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pritikjain.easyfood.R
import com.pritikjain.easyfood.activities.CategoryMealsActivity
import com.pritikjain.easyfood.activities.MealActivity
import com.pritikjain.easyfood.adapters.CategoriesAdapter
import com.pritikjain.easyfood.adapters.MostPopularAdapter
import com.pritikjain.easyfood.bottomSheet.MealBottomSheetFragment

import com.pritikjain.easyfood.databinding.FragmentHomeBinding
import com.pritikjain.easyfood.model.MealsByCategory
import com.pritikjain.easyfood.model.Meal

import com.pritikjain.easyfood.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter : MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.pritikjain.easyfood.fragments.idMeal"
        const val MEAL_NAME = "com.pritikjain.easyfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.pritikjain.easyfood.fragments.thumbMeal"

        const val CATEGORY_NAME = "com.pritikjain.easyfood.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        //viewModel = (activity as MainActivity).viewModel
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        popularItemsAdapter = MostPopularAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // prepare the recyclerView
        preparePopularItemsRecyclerView()

        // 1st : One way to Make sure during configuration change my Meals data don't get change is
        // Remove : viewModel.getRandomMeal() and add getRandomMeal in init() block of ViewModel
        viewModel.getRandomMeal()

        // 2nd :  Second way  : Make sure during configuration change my Meals data don't get change is
        // nothing to be added here but in HomeFragment add private var saveStateRandomMeal : Meal? = null
        // And open viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observerPopularItems()
        onPopularItemsClick()
        onPopularItemLongClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories();
        observerCategories()
        onCategoryClick()

        // Set the action between HomeFragment to Search fragment
        onSearchIconClick()

    }



    private fun prepareCategoriesRecyclerView() {
        binding.recViewCategories.apply{
            layoutManager = GridLayoutManager(activity, 3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }


    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }


    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
                val intent = Intent(activity,MealActivity::class.java)
                intent.putExtra(MEAL_ID,randomMeal.idMeal)
                intent.putExtra(MEAL_NAME,randomMeal.strMeal)
                intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
                startActivity(intent)
            }

    }

    private fun onPopularItemsClick() {
       popularItemsAdapter.onItemClick = { meal ->
           val intent  =  Intent(activity,MealActivity::class.java)
           intent.putExtra(MEAL_ID, meal.idMeal)
           intent.putExtra(MEAL_NAME, meal.strMeal)
           intent.putExtra(MEAL_THUMB,meal.strMealThumb)
           startActivity(intent)
       }
    }

    private fun onPopularItemLongClick(){
        popularItemsAdapter.onLongItemClick = { meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Bottom Info")

        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,Observer
            { meal ->
                if (meal != null) {
                    Glide.with(this@HomeFragment)
                        .load(meal.strMealThumb)
                        .into(binding.imgRandomMeal)
                    this.randomMeal = meal
                }
        })
    }

    private fun observerPopularItems() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,Observer
            { mealList ->
                    popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
            })
    }

    private fun observerCategories(){
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories ->
                categoriesAdapter.setCategoryList(categories)

            })

    }




}