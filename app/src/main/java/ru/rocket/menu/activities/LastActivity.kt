package ru.rocket.menu.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import ru.rocket.menu.R

class LastActivity : AppCompatActivity() {
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last)

        loginButton = findViewById(R.id.startLoginButton)

        loginButton.setOnClickListener {
            val mainIntent = Intent(this@LastActivity, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }
}

