package com.example.brainblastquizapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.brainblastquizapp.databinding.ActivityQuizQuestionBinding
import com.google.firebase.firestore.FirebaseFirestore

class QuizQuestionActivity : AppCompatActivity()//, QuizQuestionAdapter.QuestionItemListener
{
    private lateinit var binding: ActivityQuizQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance().collection("quizQuestions").document("HObECKaBOQf8rk7cGrnH")

//        val model : QuizQuestionViewModel by viewModels()
//        model.getQuestions().observe(this, Observer<List<Question>> { questions ->
//            var recyclerAdapter = QuizQuestionAdapter(this, questions, this)
//            binding.answersRecyclerView.adapter = recyclerAdapter
//        })
//    }
//
//    override fun questionSelected(question: Question) {
//        val intent = Intent(this, MainMenuActivity::class.java)
//        startActivity(intent)
    }

}