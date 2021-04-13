package com.example.brainblastquizapp

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository(private val onFirestoreTaskComplete: OnFirestoreTaskComplete) {
    private val db = FirebaseFirestore.getInstance()
    private val quizRef = db.collection("QuizList")
    fun getQuizData() {
        quizRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onFirestoreTaskComplete.quizListDataAdded(task.result!!.toObjects(Quiz::class.java)
                )
            } else {
                onFirestoreTaskComplete.onError(task.exception)
            }
        }
    }

    interface OnFirestoreTaskComplete {
        fun quizListDataAdded(quizListModelsList: List<Quiz>)
        fun onError(e: Exception?)
    }
}