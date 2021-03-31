package com.example.brainblastquizapp

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.brainblastquizapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            binding.profileUsernameTextView.text = getString(R.string.username, user.displayName)
            binding.displayNameTextView.text = getString(R.string.username, user.displayName)
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateString = simpleDateFormat.format(user.metadata!!.creationTimestamp)
            binding.dateJoinedTextView.text = String.format("%s", dateString)
        }

        binding.playButton.setOnClickListener {
            val intent = Intent(this, PlayCategoriesActivity::class.java)
            startActivity(intent)
        }
    }
}