package com.LH1100775.brainblastquizapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class QuizListViewModel : ViewModel(){

    private val quizzes = MutableLiveData<List<Quiz>>()

    init {
        loadQuizzes()
    }

    //Gets list as LiveData
    fun getQuizzes() : LiveData<List<Quiz>> {
        quizzes
        return quizzes
    }

    private fun loadQuizzes(){
        val db = FirebaseFirestore.getInstance().collection("QuizList")

        db.addSnapshotListener{ documents, exception ->
            Log.i("DB_RESPONSE", "# of elements returned ${documents?.size()}")

            if(exception != null){
                Log.w("DB_RESPONSE", "Listen failed", exception)
                return@addSnapshotListener
            }

            val quizList = ArrayList<Quiz>()

            //Ensures that the documents object is not null
            documents?.let {
                //Loop over documents returned and update list
                for (document in documents){
                    val quiz = document.toObject(Quiz::class.java)
                    Log.i("QUIZ_INFO", "${quiz.name}, number of questions ${quiz.questions}")
                    quizList.add(quiz)
                }
                quizzes.value = quizList
            }

        }
    }
}

//    private val quizListModelData: MutableLiveData<List<Quiz>> = MutableLiveData<List<Quiz>>()
//
//    fun getQuizListModelData(): LiveData<List<Quiz>> {
//        return quizListModelData
//    }
//
//    private val firebaseRepository = FirebaseRepository(this)
//
//    override fun quizListDataAdded(quizListModelsList: List<Quiz>) {
//        quizListModelData.value = quizListModelsList
//    }
//
//    override fun onError(e: Exception?) {
//
//    }
//
//    init {
//        firebaseRepository.getQuizData()
//    }