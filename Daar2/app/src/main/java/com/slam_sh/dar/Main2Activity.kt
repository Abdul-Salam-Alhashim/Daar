package com.slam_sh.dar

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.slam_sh.dar.API.ApiService
import com.slam_sh.dar.API.URL
import com.slam_sh.dar.data_home.home_data
import kotlinx.android.synthetic.main.activity_main.horizontalScrollView
import kotlinx.android.synthetic.main.activity_main.recucler_home
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Main2Activity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        window.decorView.layoutDirection= View.LAYOUT_DIRECTION_LTR
        overridePendingTransition(R.anim.fade_in2, R.anim.fade_out2)
        fitshData()




        recucler_home.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        horizontalScrollView.layoutManager=
            LinearLayoutManager(this@Main2Activity, LinearLayout.HORIZONTAL,true)

        imageButton5.setOnClickListener {
            Toast.makeText(this,"يرجى تسجيل الدخول للاستفادة من جميع مزايا التطبيق ",Toast.LENGTH_SHORT).show()
        }

        bu_login.setOnClickListener {
            var intent=Intent(this,Login::class.java)
            startActivity(intent)

        }
        bu_sing.setOnClickListener {
            var intent=Intent(this,create_an_account::class.java)
            startActivity(intent)

        }








    }

    fun fitshData(){
        val retrofit=
            Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()
        val Api: ApiService = retrofit.create(ApiService::class.java)
        val call = Api.get_home()
        var item = call.enqueue(object : Callback<home_data> {
            override fun onFailure(call: Call<home_data>, t: Throwable) {
                Toast.makeText(this@Main2Activity,"الرجاء التاكد من اتصالك بالانترنت", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<home_data>, response: Response<home_data>) {

                var Data_Recucler1=response.body()?.data?.news_and_events
                recucler_home.adapter= adapter_home(Data_Recucler1)


                var Data_Recucler2=response.body()?.data?.real_estates
                horizontalScrollView.adapter=adapter_home2(Data_Recucler2!!)




                val imageList = ArrayList<SlideModel>()
                imageList.add(SlideModel(response.body()?.data?.sliders?.get(0)?.iamge!!,  true))
                imageList.add(SlideModel(response.body()?.data?.sliders?.get(1)?.iamge!!,true))
                imageList.add(SlideModel(response.body()?.data?.sliders?.get(2)?.iamge!!,true ))
                imageList.add(SlideModel(response.body()?.data?.sliders?.get(3)?.iamge!!,true ))
                val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
                imageSlider
                    .setImageList(imageList)
                imageSlider.startSliding(300000) // with new period
                imageSlider.startSliding()
                imageSlider.stopSliding()
                imageSlider.setItemClickListener(object : ItemClickListener {
                    override fun onItemSelected(position: Int) {
                        // You can listen here
                    }
                })
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
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY


                )
    }

}

