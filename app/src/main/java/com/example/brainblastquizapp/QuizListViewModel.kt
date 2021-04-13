package com.example.brainblastquizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizListViewModel : ViewModel(), FirebaseRepository.OnFirestoreTaskComplete {
    private val quizListModelData: MutableLiveData<List<Quiz>> = MutableLiveData<List<Quiz>>()

    fun getQuizListModelData(): LiveData<List<Quiz>> {
        return quizListModelData
    }

    private val firebaseRepository = FirebaseRepository(this)

    override fun quizListDataAdded(quizListModelsList: List<Quiz>) {
        quizListModelData.value = quizListModelsList
    }

    override fun onError(e: Exception?) {

    }

    init {
        firebaseRepository.getQuizData()
    }
}