package com.slam_sh.dar.API

import com.slam_sh.dar.data.Adata
import com.slam_sh.dar.data_home.Data
import com.slam_sh.dar.data_home.home_data
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("register")
    @FormUrlEncoded
    @Headers("Accept: application/json")
    fun register(
        @Field("name") name:String,
    @Field("email") email:String,
    @Field("phone_number") phone_number:Int,
    @Field("password") password:String,
    @Field("password_confirmation") password_confirmation:String
    ): Call<Adata>

    @POST("login")
    @FormUrlEncoded
    @Headers("Accept: application/json")
    fun login(
        @Field("email") email:String,
        @Field("password") password:String
    ): Call<Adata>

    @GET("home_page")
    fun get_home():Call<home_data>
}









