package com.example.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.brainblastquizapp.databinding.ActivityPlayCategoriesBinding

class PlayCategoriesActivity : AppCompatActivity(), CategoriesGridAdapter.CategoryItemListener {

    private lateinit var binding : ActivityPlayCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model : CategoryListViewModel by viewModels()
        model.getCategories().observe(this, Observer<List<Category>> { categories ->
            var recyclerAdapter = CategoriesGridAdapter(this, categories, this)
            binding.categoriesRecyclerView.adapter = recyclerAdapter
        })

        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun categorySelected(category: Category) {
        val intent = Intent(this, QuizQuestionActivity::class.java)
        intent.putExtra("name", category.name)
        startActivity(intent)
    }
}