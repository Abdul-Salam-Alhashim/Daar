package com.slam_sh.dar

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
    }
    var myshered : SharedPreferences? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        id.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))
        Handler().postDelayed({
            id.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler().postDelayed({
                id.visibility=View.GONE
                myshered = getSharedPreferences("myshared", 0)
                var token=myshered?.getString("my_token","")
                if(token!=""){
                    startActivity(Intent(this,MainActivity::class.java))
                }else if(token==""){startActivity(Intent(this,welcome_interface::class.java))}
                finish()
            },500)
        },2500)

      /* id.alpha=0f
        id.animate().setDuration(5000).alpha(9f).withEndAction{
            val i=Intent(this,MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)*/

        }
       // val background = object : Thread(){}
           /* override fun run() {
                try {
                    Thread.sleep(5000)

                    val intent=Intent(baseContext,MainActivity::class.java)
                    startActivity(intent)

                } catch (ex:Exception){

                }
            }
        }
        background.start()
    }*/







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

