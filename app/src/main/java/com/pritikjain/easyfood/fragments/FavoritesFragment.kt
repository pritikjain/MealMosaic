package com.pritikjain.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pritikjain.easyfood.adapters.MealsAdapter
import com.pritikjain.easyfood.databinding.FragmentFavoritesBinding
import com.pritikjain.easyfood.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealsAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mealsAdapter = MealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareFavoritesRecyclerView()
        observerFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,

            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = mealsAdapter.differ.currentList[position]
                viewModel.deleteMeal(mealsAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(deletedMeal)
                    }).show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)


    }

    private fun prepareFavoritesRecyclerView() {
        binding.rvFavorites.apply{
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL,false)
            adapter = mealsAdapter
        }
    }

    private fun observerFavorites(){
        viewModel.observerFavoritesLiveData().observe(viewLifecycleOwner, Observer { meals ->
            mealsAdapter.differ.submitList(meals)

        })
    }
}