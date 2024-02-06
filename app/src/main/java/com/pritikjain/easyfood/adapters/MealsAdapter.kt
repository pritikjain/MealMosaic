package com.pritikjain.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pritikjain.easyfood.databinding.MealItemBinding
import com.pritikjain.easyfood.model.Meal

class MealsAdapter: RecyclerView.Adapter<MealsAdapter.FavoritesMealsViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    private var MealList = ArrayList<Meal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealsViewHolder {
        return FavoritesMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = meal.strMeal
    }

    class FavoritesMealsViewHolder(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root)

}