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
        // Connects the data to the recycler view.
        val model : CategoryListViewModel by viewModels()
        model.getCategories().observe(this, Observer<List<Category>> { categories ->
            var recyclerAdapter = CategoriesGridAdapter(this, categories, this)
            binding.categoriesRecyclerView.adapter = recyclerAdapter
        })
//        Send user to the profile screen
        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
//        Send user to the leaderboard screen
        binding.leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }
//        When the category is selected, send user to the quiz question screen while passing through the name of the category.
    override fun categorySelected(category: Category) {
        val intent = Intent(this, QuizQuestionActivity::class.java)
        intent.putExtra("name", category.name)
        startActivity(intent)
    }
}