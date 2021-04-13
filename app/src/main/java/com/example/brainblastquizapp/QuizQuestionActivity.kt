/*
Not working right now.
 */

package com.example.brainblastquizapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.brainblastquizapp.databinding.ActivityQuizQuestionBinding
import com.google.firebase.firestore.FirebaseFirestore

class QuizQuestionActivity : AppCompatActivity()//, QuizQuestionAdapter.QuestionItemListener
{
    private lateinit var binding: ActivityQuizQuestionBinding
    private val questions = MutableLiveData<List<Question>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadQuestions()
//        val model : QuizQuestionViewModel by viewModels()
//        model.getQuestions().observe(this, { questions ->
//            var recyclerAdapter = QuizQuestionAdapter(this, questions, this)
//            binding.answersRecyclerView.adapter = recyclerAdapter
//        })
//        binding.categoryTextView.text = String.format("%s", questions.question)
//        answerTextView.text = String.format("%s", question.A)
    }

    private fun loadQuestions(){
        val db = FirebaseFirestore.getInstance().collection("quizes/history/quizQuestions")

        db.addSnapshotListener{ documents, exception ->
            Log.i("DB_RESPONSE", "# of elements returned ${documents?.size()}")

            if(exception != null){
                Log.w("DB_RESPONSE", "Listen failed", exception)
                return@addSnapshotListener
            }

            val questionList = ArrayList<Question>()

            //Ensures that the documents object is not null
            documents?.let {
                //Loop over documents returned and update list
                for (document in documents){
                    val question = document.toObject(Question::class.java)
                    questionList.add(question)
                }
                questions.value = questionList
            }

        }
    }

//    override fun questionSelected(question: Question) {
//        val intent = Intent(this, MainMenuActivity::class.java)
//        startActivity(intent)
//    }

}