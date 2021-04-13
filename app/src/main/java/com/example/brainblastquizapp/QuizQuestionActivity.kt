/*
Not working right now.
 */

package com.example.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.brainblastquizapp.databinding.ActivityQuizQuestionBinding
import com.google.firebase.firestore.FirebaseFirestore


class QuizQuestionActivity : AppCompatActivity()
{
    private var allQuestions: MutableList<Question> = ArrayList()
    private var totalQuestions: Long = 10
    private var chosenQuestions: MutableList<Question> = ArrayList()

    private lateinit var binding: ActivityQuizQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val quizID: String = intent.getStringExtra("quizID").toString()
        totalQuestions = intent.getLongExtra("totalQuestions", 5)

        val db = FirebaseFirestore.getInstance()

        //Query Firestore Data
        db.collection("QuizList").document(quizID).collection("Questions").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                allQuestions = task.result!!.toObjects(Question::class.java)
                pickQuestions()
                loadQuestion()
            } else {
                binding.questionNumberTextView.text = ("Error : " + task.exception!!.message)
            }
        }
    }

    private fun loadQuestion() {
        binding.questionNumberTextView.text = "Question 1"
        binding.questionTextView.text = "What is your name?"

        //Configure buttons
        setUpButtons()
    }

    private fun setUpButtons() {
        binding.buttonA.isEnabled = true
        binding.buttonB.isEnabled = true
        binding.buttonC.isEnabled = true
        binding.buttonD.isEnabled = true

        binding.buttonA.visibility = View.VISIBLE
        binding.buttonB.visibility = View.VISIBLE
        binding.buttonC.visibility = View.VISIBLE
        binding.buttonD.visibility = View.VISIBLE

        binding.buttonA.text = "Mitchell"
        binding.buttonB.text = "Mitchel"
        binding.buttonC.text = "Michell"
        binding.buttonD.text = "Michel"

        binding.answerCheckTextView.visibility = View.INVISIBLE
        binding.nextQButton.visibility = View.INVISIBLE
        binding.nextQButton.isEnabled = false
    }

    private fun pickQuestions() {
        for (i in 0 until totalQuestions){
            val randomNumber: Int = getRandomInt(0, allQuestions.size)
            chosenQuestions.add(allQuestions[randomNumber])
            allQuestions.removeAt(randomNumber)

            Log.i("QUESTION_LIST", "Question" + i + " : " + chosenQuestions[i.toInt()].question)
        }
    }


    private fun getRandomInt(min: Int, max: Int): Int {
        return (Math.random() * (max - min)).toInt() + min
    }
    fun questionSelected(question: Question) {
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

}