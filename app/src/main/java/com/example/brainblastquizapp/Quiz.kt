package com.example.brainblastquizapp

import com.google.firebase.firestore.DocumentId

data class Quiz (
    @DocumentId
    var quiz_id: String?=null,
    var name: String?=null,
    var questions: Long?=null,
    var desc: String?=null,
    var image: String?=null
        )
