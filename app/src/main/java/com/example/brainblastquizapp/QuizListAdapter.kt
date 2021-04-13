package com.example.brainblastquizapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class QuizListAdapter(val context: Context,
                      val quizzes: List<Quiz>,
                      val itemListener: QuizItemListener) : RecyclerView.Adapter<QuizListAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quizNameTextView = itemView.findViewById<TextView>(R.id.detailsTitleTextView)
        val quizDescTextView = itemView.findViewById<TextView>(R.id.detailsDescTextView)
        val quizImageView = itemView.findViewById<ImageView>(R.id.quizImageView)
        val quizViewButton = itemView.findViewById<Button>(R.id.start_btn)
    }

    override fun getItemCount(): Int {
        Log.i("QUIZ_INFO", "${quizzes.size}")
        return quizzes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.single_list_item, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizzes[position]
        with(holder) {

            quizNameTextView.text = quiz.name
            quizDescTextView.text = quiz.desc
            val imageUrl: String? = quiz.image

            Glide.with(holder.itemView.context)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .into(quizImageView)
        }

        holder.quizViewButton.setOnClickListener{
            itemListener.quizSelected(quiz)
        }
    }
    interface QuizItemListener{
        fun quizSelected(quiz: Quiz)
    }
}