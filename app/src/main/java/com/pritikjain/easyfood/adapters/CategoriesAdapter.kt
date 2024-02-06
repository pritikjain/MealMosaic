package com.pritikjain.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pritikjain.easyfood.databinding.CategoryItemBinding
import com.pritikjain.easyfood.model.Category

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {


    private var categoriesList = ArrayList<Category>()
    var onItemClick: ((Category) -> Unit)? = null

    fun setCategoryList(categoriesList: List<Category>) {
       this.categoriesList = categoriesList as ArrayList<Category>
       notifyDataSetChanged();

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = categoriesList[position].strCategory;

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(categoriesList[position])
        }
    }

    class CategoryViewHolder(var binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

}