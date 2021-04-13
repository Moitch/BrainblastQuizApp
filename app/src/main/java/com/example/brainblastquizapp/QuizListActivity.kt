package com.example.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.brainblastquizapp.databinding.ActivityQuizListBinding


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
    }

    override fun quizSelected(quiz: Quiz) {
        val intent = Intent(this, QuizDetailsActivity::class.java)
        intent.putExtra(QuizDetailsActivity.QUIZ_NAME, quiz)
        startActivity(intent)
    }
}
