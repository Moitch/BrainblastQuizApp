/*
Not working right now, was trying to do something similar to the category screen
but realized that it is not grabbing a question from the database. Couldn't figure out
how to grab a singular entry from the database.

If you know how to do this, could possibly send me the documentation for it?
I was having trouble finding it so it would be greatly appreciated.
 */

package com.example.brainblastquizapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class QuizQuestionViewModel : ViewModel(){
    private val questions = MutableLiveData<List<Question>>()

    init {
        loadQuestions()
    }

    //Gets list as LiveData
    fun getQuestions() : LiveData<List<Question>> {
        return questions
    }

    private fun loadQuestions(){
        val db = FirebaseFirestore.getInstance().collection("quizQuestions")

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
}