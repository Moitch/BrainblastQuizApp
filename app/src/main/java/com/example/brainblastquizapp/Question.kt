package com.example.brainblastquizapp

import com.google.firebase.firestore.DocumentId

data class Question(
    @DocumentId
    var question_id: String?=null,
    var question: String?=null,
    var answer: String?=null,
    var A: String?=null,
    var B: String?=null,
    var C: String?=null,
    var D: String?=null,
    var timer: Long?=null
)