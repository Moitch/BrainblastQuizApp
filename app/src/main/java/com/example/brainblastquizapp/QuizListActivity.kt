package com.example.brainblastquizapp

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brainblastquizapp.databinding.ActivityQuizListBinding


class QuizListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizListBinding

    private var listView: RecyclerView? = null
    private var adapter: QuizListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model : QuizListViewModel by viewModels()
        model.getQuizListModelData().observe(this, { quizzes ->
            var recyclerAdapter = QuizListAdapter(this, quizzes, this)
            binding.quizzesRecyclerView.adapter = recyclerAdapter
        })
    }

    fun quizSelected(quiz: Quiz) {
        val intent = Intent(this, QuizQuestionActivity::class.java)
        intent.putExtra("name", quiz.name)
        startActivity(intent)
    }
    fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        listView = view.findViewById(R.id.list_view)
        listProgress = view.findViewById<View>(R.id.list_progress)
        adapter = QuizListAdapter(this)
        listView.setLayoutManager(LinearLayoutManager(getContext()))
        listView.setHasFixedSize(true)
        listView.setAdapter(adapter)
    }
//
//        fun onActivityCreated(savedInstanceState: Bundle?) {
//            super.onActivityCreated(savedInstanceState)
//            quizListViewModel = ViewModelProvider(activity!!).get(
//                QuizListViewModel::class.java
//            )
//            quizListViewModel!!.getQuizListModelData().observe(viewLifecycleOwner,
//                { quizListModels ->
//                    adapter?.setQuizListModels(quizListModels)
//                    adapter!!.notifyDataSetChanged()
//                })
//        }
}
