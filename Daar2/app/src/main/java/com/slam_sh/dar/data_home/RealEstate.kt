package com.slam_sh.dar.data_home

data class RealEstate(
    val area: String,
    val area_to: String,
    val city: String,
    val description: String,
    val description_html: String,
    val features: List<String>,
    val iamge: String,
    val id: Int,
    val images: List<String>,
    val latitude: String,
    val longitude: String,
    val municipality: String,
    val price: String,
    val title: String,
    val video: String
)