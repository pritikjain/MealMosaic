package com.pritikjain.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pritikjain.easyfood.databinding.PopularItemsBinding
import com.pritikjain.easyfood.model.MealsByCategory

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>(){

    private var mealsList = ArrayList<MealsByCategory>()
    lateinit var onItemClick:((MealsByCategory) -> Unit)
    var onLongItemClick:((MealsByCategory) -> Unit)? =  null

    fun setMeals(mealsList: ArrayList<MealsByCategory> ){
        this.mealsList = mealsList;
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener{
            onLongItemClick?.invoke(mealsList[position])
            true
        }
    }

    class PopularMealViewHolder(val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

}