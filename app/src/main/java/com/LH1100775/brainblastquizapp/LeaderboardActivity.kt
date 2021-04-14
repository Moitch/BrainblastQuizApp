package com.LH1100775.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.LH1100775.brainblastquizapp.databinding.ActivityLeaderboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderboardBinding

    private var firebaseAuth: FirebaseAuth? = null
    private var currentUserID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.playButton.setOnClickListener {
            val intent = Intent(this, QuizListActivity::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance();

        //Get User ID
        if (firebaseAuth!!.currentUser != null) {
            currentUserID = firebaseAuth!!.currentUser?.uid;
        } else {
            //Go Back to Home Page
        }

        val db = FirebaseFirestore.getInstance().collection("QuizList")

        db.addSnapshotListener { documents, exception ->
            Log.i("DB_RESPONSE", "# of elements returned ${documents?.size()}")

            if (exception != null) {
                Log.w("DB_RESPONSE", "Listen failed", exception)
                return@addSnapshotListener
            }

            val quizList = ArrayList<Quiz>()

            //Ensures that the documents object is not null
            documents?.let {
                //Loop over documents returned and update list
                for (document in documents) {
                    val quiz = document.toObject(Quiz::class.java)
                    Log.i("QUIZ_INFO", "${quiz.name}, number of questions ${quiz.questions}")
                    quizList.add(quiz)
                }

            }

            for (i in 0 until quizList.size){
                val db1 = FirebaseFirestore.getInstance().collection("QuizList")
                db1.document(quizList[i].quiz_id!!).collection("Results").document(currentUserID!!).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result: DocumentSnapshot? = task.result
                        val correct = result?.getLong("correct")
                        if (correct != null) {
                            val wrong = result?.getLong("wrong")
                            val total = correct!! + wrong!!
                            val percent = (correct * 100) / total;
                            if(i == 0){
                                binding.quiz1TextView.text = quizList[i].name
                                binding.quiz1ScoreTextView.text = "$percent%"
                            }else {
                                binding.quiz2TextView.text = quizList[i].name
                                binding.quiz2ScoreTextView.text = "$percent%"
                            }
                        }else{
                            if(i == 0){
                                binding.quiz1TextView.text = quizList[i].name
                                binding.quiz1ScoreTextView.text = "N/A"
                            }else {
                                binding.quiz2TextView.text = quizList[i].name
                                binding.quiz2ScoreTextView.text = "N/A"
                            }
                        }
                    }
                    else{
                        if(i == 0){
                            binding.quiz1TextView.text = quizList[i].name
                            binding.quiz1ScoreTextView.text = "N/A"
                        }else {
                            binding.quiz2TextView.text = quizList[i].name
                            binding.quiz2ScoreTextView.text = "N/A"
                        }
                    }
                }
            }
        }
    }
}