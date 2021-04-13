package com.example.brainblastquizapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriesGridAdapter(val context: Context,
                            val categories: List<Category>,
                            val itemListener: CategoryItemListener) : RecyclerView.Adapter<CategoriesGridAdapter.CategoryViewHolder>()
{
    inner class CategoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val categoryTextView = itemView.findViewById<TextView>(R.id.questionNumberTextView)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.category_item_grid, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        with (holder){
            categoryTextView.text = String.format("%s", category.name)
        }

        holder.itemView.setOnClickListener{
            itemListener.categorySelected(category)
        }
    }

    interface CategoryItemListener{
        fun categorySelected(category: Category)
    }
}

