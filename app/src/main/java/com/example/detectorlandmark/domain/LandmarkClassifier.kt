package com.example.detectorlandmark.domain

import android.graphics.Bitmap

//Function akan sering dipanggil di dalam image analizer ketika kita implement camerax yang berhubungan dengan single frame/
// Model nanti akan dimasukkan ke dalam sebuah frame yg isnya hrus parameyer dibwah
// Code dibwah belum terlalu kompeks karena kita menggunakan pre trained model
interface LandmarkClassifier {
    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>
}