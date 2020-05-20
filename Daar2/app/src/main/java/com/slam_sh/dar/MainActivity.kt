package com.slam_sh.dar

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView
import com.slam_sh.dar.API.ApiService
import com.slam_sh.dar.API.URL
import com.slam_sh.dar.data_home.home_data
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var myshered : SharedPreferences? =null
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.layoutDirection= View.LAYOUT_DIRECTION_LTR
        overridePendingTransition(R.anim.fade_in2, R.anim.fade_out2)
        fitshData()




        recucler_home.layoutManager=LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        horizontalScrollView.layoutManager=LinearLayoutManager(this@MainActivity,LinearLayout.HORIZONTAL,true)







        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navView.bringToFront()

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.group)


    }

    fun fitshData(){
        val retrofit=Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()
        val Api:ApiService = retrofit.create(ApiService::class.java)
        val call = Api.get_home()
        var item = call.enqueue(object :Callback <home_data>{
            override fun onFailure(call: Call<home_data>, t: Throwable) {
                Toast.makeText(this@MainActivity,"الرجاء التاكد من اتصالك بالانترنت",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<home_data>, response: Response<home_data>) {

            var Data_Recucler1=response.body()?.data?.news_and_events
                recucler_home.adapter= adapter_home(Data_Recucler1)

                var Data_Recucler2=response.body()?.data?.real_estates
                horizontalScrollView.adapter=adapter_home2(Data_Recucler2!!)



                var databack:SharedPreferences=getSharedPreferences("myshared",0)
                name_header.text= databack.getString("my_name","لاتملك حساب للخروج منه")
                email_header.text=databack.getString("my_email","لاتملك حساب للخروج منه")
                var my_image=databack.getString("my_image","لاتملك حساب للخروج منه")
                var Image: ImageView?=image_header
                Glide.with(this@MainActivity).load(my_image).into(Image!!)



               /* app:placeholder="@drawable/group917"
                app:error_image="@drawable/group917"*/
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













    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                startActivity(Intent(this@MainActivity,MainActivity::class.java))
                return true
            }
            R.id.nav_share -> {
                val sent = Intent()
                sent.action = Intent.ACTION_SEND
                sent.putExtra(Intent.EXTRA_TEXT , "https://play.google.com/store/apps/details?id=package com.slam_sh.appc حمل تطبيق تعلم السي بلس بلس ")
                sent.type = "text/plain"
                startActivity(Intent.createChooser(sent,"إختار التطبيق الذي تريد المشاركه معه:"))
                return true
            }
            R.id.nav_logout -> {
                myshered = getSharedPreferences("myshared", 0)
                var editor:SharedPreferences.Editor = myshered!!.edit()
                editor.putString("my_token",null)
                editor.commit()
                finishAffinity()

                return true
            }
            R.id.nav_exit -> {
                finishAffinity()
                return true
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
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

















