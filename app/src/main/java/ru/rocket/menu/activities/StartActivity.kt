package ru.rocket.menu.activities

import android.Manifest


import android.content.Context
import android.content.Intent

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import ru.rocket.menu.R
import ru.rocket.menu.fragments.start.IntroFragment
import ru.rocket.menu.util.IS_AUTH
import ru.rocket.menu.util.USER_DATA

private const val PERMISSION_REQUEST_CODE = 1

class StartActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        sharedPreferences = this.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
    }

    public override fun onStart() {
        super.onStart()
        getPermission()
        if (isNotAuth()) {
            sentToIntroFragment()
        }
        sentToIntroFragment()
    }

    private fun isNotAuth(): Boolean = sharedPreferences.getString(IS_AUTH, "").isNullOrBlank()

    private fun getPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun sentToMainActivity() {
        val mainIntent = Intent(this@StartActivity, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun sentToIntroFragment() {
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.startContainer)
        if (fragment == null) {
            fragment = IntroFragment()
            fm.beginTransaction()
                .add(R.id.startContainer, fragment)
                .commit()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != PERMISSION_REQUEST_CODE || grantResults.size != PERMISSION_REQUEST_CODE) {
            finish()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
