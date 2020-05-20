package com.slam_sh.dar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import kotlinx.android.synthetic.main.activity_welcome_interface.*

class welcome_interface : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_interface)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        window.decorView.layoutDirection= View.LAYOUT_DIRECTION_LTR
        buLong.setOnClickListener {
         var   intent=Intent(this,Login::class.java)
            startActivity(intent)
        }
        buCreate_an_account.setOnClickListener {
            var   intent=Intent(this,create_an_account::class.java)
            startActivity(intent)
        }
        textwel.setOnClickListener {
            var   intent=Intent(this,Main2Activity::class.java)
            startActivity(intent)
        }

    }

        override fun onResume() {
            super.onResume()

            setToImmersiveMode()
        }

        override fun onWindowFocusChanged(hasFocus: Boolean) {
            super.onWindowFocusChanged(hasFocus)

            val runnable = Runnable { setToImmersiveMode() }

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(runnable, 300)
        }

        private fun setToImmersiveMode() {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }



}

