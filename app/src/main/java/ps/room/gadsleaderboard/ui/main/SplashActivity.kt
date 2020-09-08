package ps.room.gadsleaderboard.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ps.room.gadsleaderboard.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    private fun toMainActivity() {
       startActivity(Intent(this,MainActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        Handler().also {
            it.postDelayed(::toMainActivity,1000)
        }
    }
}