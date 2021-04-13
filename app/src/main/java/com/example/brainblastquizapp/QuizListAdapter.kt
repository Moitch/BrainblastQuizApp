package com.example.brainblastquizapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class QuizListAdapter(
    val context: Context,
    var quizzes: List<Quiz>,
    val itemListener: QuizListActivity
) : RecyclerView.Adapter<QuizListAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quizNameTextView =
            itemView.findViewById<TextView>(com.example.brainblastquizapp.R.id.detailsTitleTextView)
        val quizDescTextView =
            itemView.findViewById<TextView>(com.example.brainblastquizapp.R.id.detailsDescTextView)
        val quizImageView =
            itemView.findViewById<ImageView>(com.example.brainblastquizapp.R.id.detailsImageView)
        val quizViewButton =
            itemView.findViewById<Button>(com.example.brainblastquizapp.R.id.start_btn)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    fun setQuizListModels(quizListModels: List<Quiz>?) {
        if (quizListModels != null) {
            this.quizzes = quizListModels
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            inflater.inflate(R.layout.single_list_item, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizListAdapter.QuizViewHolder, position: Int) {
        val quiz = quizzes[position]
        with(holder) {
            if (quiz != null) {
                quizNameTextView.text = String.format("%s", quiz.name)
            }

            holder.quizViewButton.setOnClickListener{
                itemListener.quizSelected(quiz)
            }
        }


    }
    interface QuizItemListener{
        fun quizSelected(quiz: Quiz)
    }
}