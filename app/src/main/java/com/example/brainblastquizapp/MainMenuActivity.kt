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
//        Get the current user to display their data whenever needed
        val user = FirebaseAuth.getInstance().currentUser
//        Ensure the user is not null
        if (user != null) {
           binding.welcomeMessageTextView.text = getString(R.string.welcome_back, user.displayName)
        }
//        Send user to the categories screen
        binding.playButton.setOnClickListener {
            val intent = Intent(this, QuizListActivity::class.java)
            startActivity(intent)
        }
//        Send user to the profile screen
        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
//        Send user to the leaderboard screen
        binding.leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }
}