package com.LH1100775.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.LH1100775.brainblastquizapp.databinding.ActivityQuizResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


class QuizResultActivity : AppCompatActivity() {


    private val db = FirebaseFirestore.getInstance()
    private var firebaseAuth: FirebaseAuth? = null
    private var currentUserID: String? = null

    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val quizID: String = intent.getStringExtra("QUIZ_ID").toString()

        binding.playButton.setOnClickListener {
            val intent = Intent(this, QuizListActivity::class.java)
            startActivity(intent)
        }

        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth!!.currentUser != null) {
            currentUserID = firebaseAuth!!.currentUser?.uid
        }

        db.collection("QuizList").document(quizID).collection("Results").document(currentUserID!!).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result: DocumentSnapshot? = task.result
                val correct = result?.getLong("correct")
                val wrong = result?.getLong("wrong")
                binding.correctAnswerTextView.text = correct.toString()
                binding.wrongAnswerTextView.text = wrong.toString()


                //Calculate progress bar
                val total = correct!! + wrong!!
                binding.totalScoreTextVIew.text = "$correct/$total"
                val percent = correct * 100 / total
                binding.percentageTextView.text = "$percent%"
                binding.progressBar.progress = percent.toInt()
            }
        }
    }
}