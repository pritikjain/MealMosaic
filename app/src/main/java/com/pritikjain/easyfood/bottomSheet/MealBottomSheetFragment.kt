package com.pritikjain.easyfood.bottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pritikjain.easyfood.activities.MealActivity
import com.pritikjain.easyfood.databinding.FragmentMealsBottomSheetBinding
import com.pritikjain.easyfood.fragments.HomeFragment
import com.pritikjain.easyfood.viewmodel.HomeViewModel
import com.pritikjain.easyfood.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MEAL_ID = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [MealBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MealBottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var mealId: String? = null
    private var mealName: String? = null
    private var mealThumb: String? = null

    private lateinit var binding: FragmentMealsBottomSheetBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealViewModel: MealViewModel


    companion object {
        @JvmStatic
        fun newInstance(mealId: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, mealId)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel=ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        mealViewModel = ViewModelProvider(requireActivity())[MealViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealsBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let{
            mealViewModel.getMealDetail(it)
        }
        observerBottomSheetMealLiveData()

        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener{
            val intent = Intent(activity, MealActivity::class.java)
            intent.apply {
                putExtra(HomeFragment.MEAL_ID,mealId)
                putExtra(HomeFragment.MEAL_NAME,mealName)
                putExtra(HomeFragment.MEAL_THUMB,mealThumb)
            }
            startActivity(intent)
        }
    }


    private fun observerBottomSheetMealLiveData() {
        mealViewModel.observeMealDetailsLiveData().observe(viewLifecycleOwner, Observer  { meal ->
            if (meal != null) {
                Glide.with(this@MealBottomSheetFragment)
                    .load(meal.strMealThumb)
                    .into(binding.imgBottomSheet)
                binding.tvBottomSheetArea.text = meal.strArea
                binding.tvBottomSheetCategory.text = meal.strCategory
                binding.tvBottomSheetMealName.text = meal.strMeal

                mealName = meal.strMeal
                mealThumb = meal.strMealThumb
            }
        })
    }
}