/*
Not working right now, was trying to do something similar to the category screen
but realized that it is not grabbing a question from the database. Couldn't figure out
how to grab a singular entry from the database.

If you know how to do this, could possibly send me the documentation for it?
I was having trouble finding it so it would be greatly appreciated.
 */
package com.example.brainblastquizapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizQuestionAdapter(val context: Context,
                          val questions: List<Question>,
                          val itemListener : QuestionItemListener) : RecyclerView.Adapter<QuizQuestionAdapter.QuestionViewHolder>()
{
    inner class QuestionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val answer1TextView = itemView.findViewById<TextView>(R.id.answerTextView)
        val answer2TextView = itemView.findViewById<TextView>(R.id.answerTextView)
        val answer3TextView = itemView.findViewById<TextView>(R.id.answerTextView)
        val answer4TextView = itemView.findViewById<TextView>(R.id.answerTextView)


    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.answer_item_grid, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        with (holder){
            answer1TextView.text = question.A
            answer2TextView.text = question.B
            answer3TextView.text = question.C
            answer4TextView.text = question.D
        }

        holder.itemView.setOnClickListener{
            itemListener.questionSelected(question)
        }
    }

    interface QuestionItemListener{
        fun questionSelected(question: Question)
    }

}

