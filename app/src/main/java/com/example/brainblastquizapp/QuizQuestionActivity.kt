/*
Not working right now.
 */

package com.example.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
    private var currentQuestion: Int = 0
    private var questionActive: Boolean = false

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

        binding.buttonA.setOnClickListener {
            optionSelected(binding.buttonA.text)
        }

        binding.buttonB.setOnClickListener {
            optionSelected(binding.buttonB.text)
        }

        binding.buttonC.setOnClickListener {
            optionSelected(binding.buttonC.text)
        }

        binding.buttonD.setOnClickListener {
            optionSelected(binding.buttonD.text)
        }
    }

    private fun optionSelected(selectedAnswer: CharSequence?) {
        if(questionActive){
            if(chosenQuestions[currentQuestion].answer?.equals(selectedAnswer) == true){
                Log.i("ANSWER", "Correct answer")
            } else{
                Log.i("ANSWER", "Incorrect answer")
            }
        }
    }

    private fun loadQuestion() {
        binding.questionTextView.text = "What is your name?"

        //Configure buttons
        setUpButtons()

        startQuestion(1)
    }

    private fun startQuestion(questionNum: Int) {
        // Load question
        binding.questionNumberTextView.text = "Question $questionNum"
        binding.questionTextView.text = (chosenQuestions[questionNum].question)

        // Load answers
        binding.buttonA.text = (chosenQuestions[questionNum].A)
        binding.buttonB.text = (chosenQuestions[questionNum].B)
        binding.buttonC.text = (chosenQuestions[questionNum].C)
        binding.buttonD.text = (chosenQuestions[questionNum].D)

        // Question is done loading, allow to be answered
        questionActive = true

        // Start timer
        startCountdown(questionNum)
    }

    private fun startCountdown(questionNum: Int) {

        val timeLeft: Long? = chosenQuestions[questionNum].timer
        binding.timerTextView.text = (timeLeft.toString())


        val timer = object: CountDownTimer(timeLeft?.times(1000)!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                // When time is finished, prevent user from answering.
                questionActive = false
            }
        }
        timer.start()
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