package com.LH1100775.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.LH1100775.brainblastquizapp.databinding.ActivityQuizListBinding


class QuizListActivity : AppCompatActivity(), QuizListAdapter.QuizItemListener {

    private lateinit var binding: ActivityQuizListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model : QuizListViewModel by viewModels()
        model.getQuizzes().observe(this, { quizzes ->
            var recyclerAdapter = QuizListAdapter(this, quizzes, this)
            binding.quizzesRecyclerView.adapter = recyclerAdapter
        })

        // Send user to the profile screen
        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        // Send user to the leaderboard screen
        binding.leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun quizSelected(quiz: Quiz) {
        val intent = Intent(this, QuizDetailsActivity::class.java)
        intent.putExtra(QuizDetailsActivity.QUIZ_NAME, quiz)
        startActivity(intent)
    }
}
