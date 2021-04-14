package com.LH1100775.brainblastquizapp

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.LH1100775.brainblastquizapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
//        Make sure the user is not null before displaying the data.
        if (user != null) {
            // Displays the username and when the account was created.
            binding.profileUsernameTextView.text = getString(R.string.username, user.displayName)
            binding.displayNameTextView.text = getString(R.string.username, user.displayName)
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateString = simpleDateFormat.format(user.metadata!!.creationTimestamp)
            binding.dateJoinedTextView.text = String.format("%s", dateString)
        }
//        Send user to the categories screen
        binding.playButton.setOnClickListener {
            val intent = Intent(this, QuizListActivity::class.java)
            startActivity(intent)
        }
//        Send user to the leaderboard screen
        binding.leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }
}