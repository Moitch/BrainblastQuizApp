package com.example.brainblastquizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.brainblastquizapp.databinding.ActivityMainMenuBinding
import com.google.firebase.auth.FirebaseAuth

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
           binding.welcomeMessageTextView.text = getString(R.string.welcome_back, user.displayName)
        }

        binding.playButton.setOnClickListener {
            val intent = Intent(this, PlayCategoriesActivity::class.java)
            startActivity(intent)
        }

        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }
}