package com.slam_sh.dar

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.slam_sh.dar.API.ApiService


import com.slam_sh.dar.API.URL
import com.slam_sh.dar.data.Adata

import kotlinx.android.synthetic.main.activity_create_an_account.*
import kotlinx.android.synthetic.main.header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class create_an_account : AppCompatActivity() {
    var myshered : SharedPreferences? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in2,R.anim.fade_out2)
        setContentView(R.layout.activity_create_an_account)
        window.decorView.layoutDirection= View.LAYOUT_DIRECTION_LTR
        sign_up.setOnClickListener {
            try {
            signUp(edName.text.toString(),
                ed_email.text.toString(),
                edphone_number.text.toString().toInt() ,
                ed_password.text.toString(),
                ed_password_confirmation.text.toString())
                progressBar.visibility= View.VISIBLE
            }catch (e:Exception){Toast.makeText(this@create_an_account,"ادخل جميع الحقول من فضلك",Toast.LENGTH_LONG).show()}

        }
        text_create.setOnClickListener {
           var intent=Intent(this,Login::class.java)
            startActivity(intent)

        }
    }
    fun signUp( name:String,email:String,phone_number:Int,password:String,password_confirmation:String){
        val retrofit=Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
            .build()
        val api:ApiService=retrofit.create(ApiService::class.java)
        val call =api.register(name,email,phone_number,password,password_confirmation)
        call.enqueue(object :Callback<Adata>{
            override fun onFailure(call: Call<Adata>, t: Throwable) {
                //NetworkTask(this@create_an_account).execute()
                progressBar.visibility= View.GONE
                Toast.makeText(this@create_an_account,"غير قادر على الاتصال تاكد من اتصالك بالانترنت",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Adata>, response: Response<Adata>) {
               // NetworkTask(this@create_an_account).execute()
                progressBar.visibility= View.GONE

                when(response.body()?.data?.global?.email){
                    "validation.email"->
                        Toast.makeText(this@create_an_account,"البريد الاكتروني خطأ",Toast.LENGTH_LONG).show()
                    "validation.unique"->
                        Toast.makeText(this@create_an_account,"البريد الاكتروني موجود من السابق",Toast.LENGTH_LONG).show()
                }
                when(response.body()?.data?.name){
                    "validation.max.string"->
                        Toast.makeText(this@create_an_account,"تم تجاوز الحد الاقصى من الاحرف في الاسم",Toast.LENGTH_LONG).show()

                }
                when(response.body()?.data?.global?.password_and_password_confirmation_not_match){
                    "password_and_password_confirmation_not_match"->
                        Toast.makeText(this@create_an_account,"يرجا التاكد من كلمة المرور",Toast.LENGTH_LONG).show()

                }

                myshered = getSharedPreferences("myshared", 0)
                var editor:SharedPreferences.Editor = myshered!!.edit()
                editor.putString("my_token",response.body()?.data?.token)
                editor.putString("my_email",response.body()?.data?.email)
                editor.putString("my_name",response.body()?.data?.name)
                editor.putString("my_phone",response.body()?.data?.phone_number)
                editor.putString("my_image",response.body()?.data?.image)
                editor.commit()
                if (response.body()?.data?.token!=null)
                startActivity(Intent(this@create_an_account,MainActivity::class.java))
            }

        })

    }














   /* class NetworkTask(var activity: create_an_account) : AsyncTask<Void, Void, Void>(){

        var dialog = Dialog(activity,android.R.style.Theme_Translucent_NoTitleBar)

        override fun onPreExecute() {
            val view = activity.layoutInflater.inflate(R.layout.full_scren_progress_bar,null)
            dialog.setContentView(view)
            dialog.setCancelable(false)
            dialog.show()
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            Thread.sleep(500)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            dialog.dismiss()
        }

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
