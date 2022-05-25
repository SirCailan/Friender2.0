package com.example.friender2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var backPressTime: Long = 0

    override fun onBackPressed() {
        //Ensures that the app doesn't close without warning if the backstack is empty.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val backStackEntryCount = navHostFragment?.childFragmentManager?.backStackEntryCount

        if (backStackEntryCount != null && backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            val backMessage =
                Toast.makeText(this, getString(R.string.backpress_text), Toast.LENGTH_LONG)

            if (backPressTime + 2000 > System.currentTimeMillis()) {
                backMessage.cancel()
                super.onBackPressed()
                return
            } else {
                backMessage.show()
            }

            backPressTime = System.currentTimeMillis()
        }
    }
}