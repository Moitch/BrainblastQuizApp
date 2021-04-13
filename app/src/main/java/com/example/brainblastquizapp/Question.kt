package com.example.brainblastquizapp

data class Question(
        var id: Int?=null,
    var question: String?=null,
    var category: String?=null,
    var A: String?=null,
    var B: String?=null,
    var C: String?=null,
    var D: String?=null,
    var answer: Int?=null
)