package com.example.brainblastquizapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CategoryListViewModel : ViewModel(){
    private val categories = MutableLiveData<List<Category>>()

    init {
        loadCategories()
    }

    //Gets list as LiveData
    fun getCategories() : LiveData<List<Category>> {
        return categories
    }

    private fun loadCategories(){
        val db = FirebaseFirestore.getInstance().collection("quizes")
            .orderBy("name", Query.Direction.ASCENDING)

        db.addSnapshotListener{ documents, exception ->
            Log.i("DB_RESPONSE", "# of elements returned ${documents?.size()}")

            if(exception != null){
                Log.w("DB_RESPONSE", "Listen failed", exception)
                return@addSnapshotListener
            }

            val categoryList = ArrayList<Category>()

            //Ensures that the documents object is not null
            documents?.let {
                //Loop over documents returned and update list
                for (document in documents){
                    val category = document.toObject(Category::class.java)
                    categoryList.add(category)
                }
                categories.value = categoryList
            }

        }
    }
}