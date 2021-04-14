package com.example.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.brainblastquizapp.databinding.ActivityQuizDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


class QuizDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizDetailsBinding

    private var firebaseAuth: FirebaseAuth? = null
    private var currentUserID: String? = null

    private var passedQuiz: Quiz? = null
    private var numOfQuestions: Long = 0

    companion object {
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
            intent.putExtra("QUIZ_ID", passedQuiz!!.quiz_id)
            intent.putExtra("totalQuestions", numOfQuestions)
            startActivity(intent)
        }

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

        loadPreviousBest()
    }

    private fun loadPreviousBest() {
        passedQuiz = intent.getParcelableExtra(QUIZ_NAME)
        val quizID = passedQuiz?.quiz_id

        firebaseAuth = FirebaseAuth.getInstance();

        //Get User ID
        if (firebaseAuth!!.currentUser != null) {
            currentUserID = firebaseAuth!!.currentUser?.uid;
        } else {
            //Go Back to Home Page
        }

        val db1 = FirebaseFirestore.getInstance().collection("QuizList")
        db1.document(quizID!!).collection("Results").document(currentUserID!!).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result: DocumentSnapshot? = task.result
                val correct = result?.getLong("correct")
                if (correct != null) {
                    val wrong = result?.getLong("wrong")
                    val total = correct!! + wrong!!
                    //Calculate Progress
                    binding.lastScore.text = "$correct/$total"
                    val percent = (correct * 100) / total;

                    binding.lastScore.text = "$percent%"
                }else{
                    binding.lastScore.text = "N/A"
                }
            }
            else{
                binding.lastScore.text = "N/A"
            }
        }
    }
}
