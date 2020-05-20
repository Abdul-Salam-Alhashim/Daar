package com.slam_sh.dar

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.slam_sh.dar.API.ApiService
import com.slam_sh.dar.API.URL
import com.slam_sh.dar.data.Adata
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Login : AppCompatActivity() {
    var myshered : SharedPreferences? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in2,R.anim.fade_out2)
        setContentView(R.layout.activity_login)
        window.decorView.layoutDirection= View.LAYOUT_DIRECTION_LTR
        sign_in.setOnClickListener {
          try{
              login(edemail.text.toString(),
                    ed_passw.text.toString())
                    progressBar2.visibility= View.VISIBLE
            }catch (e:Exception){ Toast.makeText(this@Login,"ادخل جميع الحقول من فضلك",Toast.LENGTH_LONG).show() }

        }
        textLong.setOnClickListener {
            var intent = Intent(this, create_an_account::class.java)
            startActivity(intent)
        }


    }




        fun login (email:String,password:String){
            val retrofit= Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
                .build()
            val api: ApiService =retrofit.create(ApiService::class.java)
            val call =api.login(email,password)
            call.enqueue(object : Callback<Adata> {
                override fun onFailure(call: Call<Adata>, t: Throwable) {
                    progressBar2.visibility= View.GONE
                    Toast.makeText(this@Login,"غير قادر على الاتصال تاكد من اتصالك بالانترنت",
                        Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Adata>, response: Response<Adata>) {
                    progressBar2.visibility= View.GONE

                    when(response.body()?.data?.global?.user_not_found){
                        "user_not_found"->
                            Toast.makeText(this@Login,"لايوجد حساب مسبق لهذا البريد الاكتروني", Toast.LENGTH_LONG).show()
                    }
                    when(response.body()?.data?.global?.credentials){
                        "invalid_credentials"->
                            Toast.makeText(this@Login,"كلمة المرور غير صحيحة",
                                Toast.LENGTH_LONG).show()
                    }
                    if (response.body()?.data?.global?.email== "validation.required"&&response.body()?.data?.global?.password=="validation.required" ||
                        response.body()?.data?.global?.email== "validation.required"||response.body()?.data?.global?.password=="validation.required" ){
                        Toast.makeText(this@Login,"ادخل جميع الحقول من فضلك",Toast.LENGTH_LONG).show()
                }








                    myshered = getSharedPreferences("myshared", 0)
                    var editor: SharedPreferences.Editor = myshered!!.edit()
                    editor.putString("my_token",response.body()?.data?.token)
                    editor.putString("my_email",response.body()?.data?.email)
                    editor.putString("my_name",response.body()?.data?.name)
                    editor.putString("my_phone",response.body()?.data?.phone_number)
                    editor.putString("my_image",response.body()?.data?.image)
                    editor.commit()



                    if (response.body()?.data?.token!=null)
                        startActivity(Intent(this@Login,MainActivity::class.java))
                }

            })

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
