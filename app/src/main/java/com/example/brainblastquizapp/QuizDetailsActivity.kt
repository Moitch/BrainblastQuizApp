package com.example.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.brainblastquizapp.databinding.ActivityQuizDetailsBinding

class QuizDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizDetailsBinding

    private var passedQuiz: Quiz? = null
    private var numOfQuestions: Long = 0

    companion object{
        const val QUIZ_NAME = "QUIZ_NAME"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passedQuiz = intent.getParcelableExtra(QUIZ_NAME)

        val imageUrl: String? = passedQuiz?.image

        Glide.with(applicationContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(binding.quizImageView)

        binding.quizTitleTextView.text = passedQuiz?.name
        binding.quizDescTextView.text = passedQuiz?.desc
        binding.totalQuestions.text = passedQuiz?.questions.toString()
        numOfQuestions = passedQuiz?.questions!!


        binding.startBtn.setOnClickListener {
            val intent = Intent(this, QuizQuestionActivity::class.java)
            intent.putExtra("quizID", passedQuiz!!.quiz_id)
            intent.putExtra("totalQuestions", numOfQuestions)
            startActivity(intent)
        }
    }
}