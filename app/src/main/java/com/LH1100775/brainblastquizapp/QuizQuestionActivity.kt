/*
Not working right now.
 */

package com.LH1100775.brainblastquizapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.LH1100775.brainblastquizapp.databinding.ActivityQuizQuestionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class QuizQuestionActivity : AppCompatActivity() {
    private var allQuestions: MutableList<Question> = ArrayList()
    private var totalQuestions: Long = 10
    private var chosenQuestions: MutableList<Question> = ArrayList()
    private var currentQuestion: Int = 0
    private var questionActive: Boolean = false
    private var countDownTimer: CountDownTimer? = null

    private var correctAnswers: Int = 0
    private var wrongAnswers: Int = 0
    private var notAnswered: Int = 0

    private val db = FirebaseFirestore.getInstance()
    private var firebaseAuth: FirebaseAuth? = null
    private var currentUserID: String? = null

    private lateinit var binding: ActivityQuizQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val quizID: String = intent.getStringExtra("QUIZ_ID").toString()
        totalQuestions = intent.getLongExtra("totalQuestions", 5)

        firebaseAuth = FirebaseAuth.getInstance();

        //Get User ID
        if (firebaseAuth!!.currentUser != null) {
            currentUserID = firebaseAuth!!.currentUser?.uid;
        } else {
            //Go Back to Home Page
        }


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
            checkAnswer(binding.buttonA)
        }

        binding.buttonB.setOnClickListener {
            checkAnswer(binding.buttonB)
        }

        binding.buttonC.setOnClickListener {
            checkAnswer(binding.buttonC)
        }

        binding.buttonD.setOnClickListener {
            checkAnswer(binding.buttonD)
        }
        binding.nextQButton.setOnClickListener {
            if (currentQuestion == (totalQuestions.toInt() - 1)) {
                submitResults()
            } else {
                currentQuestion++
                startQuestion(currentQuestion)
                resetUI()
            }
        }
    }

    private fun submitResults() {
        val quizID: String = intent.getStringExtra("QUIZ_ID").toString()

        var results: HashMap<String, Any> = HashMap()
        results["correct"] = correctAnswers
        results["wrong"] = wrongAnswers

        val db1 = FirebaseFirestore.getInstance().collection("QuizList")
        db1.document(quizID).collection("Results").document(currentUserID!!).set(results).addOnCompleteListener {
            val intent = Intent(this, QuizResultActivity::class.java)
            intent.putExtra("QUIZ_ID", quizID)
            startActivity(intent)
        }
    }

    private fun resetUI() {
        binding.answerCheckTextView.visibility = View.INVISIBLE
        binding.nextQButton.visibility = View.INVISIBLE
        binding.nextQButton.isEnabled = false
    }

    private fun checkAnswer(buttonPressed: Button?) {
        if (questionActive) {
            if (chosenQuestions[currentQuestion].answer?.equals(buttonPressed?.text) == true) {
                Log.i("ANSWER", "Correct answer")
                correctAnswers++

                binding.answerCheckTextView.text = "Correct Answer"
                binding.answerCheckTextView.setTextColor(Color.parseColor("#39FF14"));
            } else {
                Log.i("ANSWER", "Wrong answer")
                wrongAnswers++

                binding.answerCheckTextView.text = "Wrong Answer\n Correct Answer : " + chosenQuestions[currentQuestion].answer
                binding.answerCheckTextView.setTextColor(Color.parseColor("#FF073A"));
            }
            // After user select answer disable answering again.
            questionActive = false
            countDownTimer?.cancel()
            showFeedback()
        }
    }

    private fun showFeedback() {
        if (currentQuestion == (totalQuestions.toInt() - 1)) {
            binding.nextQButton.text = "Finish Quiz"
        }
        binding.answerCheckTextView.visibility = View.VISIBLE
        binding.nextQButton.visibility = View.VISIBLE
        binding.nextQButton.isEnabled = true
    }

    private fun loadQuestion() {
        binding.questionTextView.text = "What is your name?"

        //Configure buttons
        setUpButtons()

        startQuestion(0)
    }

    private fun startQuestion(questionNum: Int) {
        // Load question
        binding.questionNumberTextView.text = "Question ${questionNum + 1}"
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


        countDownTimer = object : CountDownTimer(timeLeft?.times(1000)!!, 10) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                // When time is finished, prevent user from answering.
                questionActive = false
                binding.answerCheckTextView.setTextColor(Color.parseColor("#FF073A"));
                binding.answerCheckTextView.text = "Time is up! No answer was selected"
                wrongAnswers++
                showFeedback()
            }
        }
        (countDownTimer as CountDownTimer).start()
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

        binding.answerCheckTextView.visibility = View.INVISIBLE
        binding.nextQButton.visibility = View.INVISIBLE
        binding.nextQButton.isEnabled = false
    }

    private fun pickQuestions() {
        for (i in 0 until totalQuestions) {
            val randomNumber: Int = getRandomInt(0, allQuestions.size)
            chosenQuestions.add(allQuestions[randomNumber])
            allQuestions.removeAt(randomNumber)

            Log.i("QUESTION_LIST", "Question" + i + " : " + chosenQuestions[i.toInt()].question)
        }
    }


    private fun getRandomInt(min: Int, max: Int): Int {
        return (Math.random() * (max - min)).toInt() + min
    }

}