package com.example.detectorlandmark.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.detectorlandmark.domain.Classification
import com.example.detectorlandmark.domain.LandmarkClassifier

//Dapat mengkonvert ke bitmap langsung tapi tidak ideal
//Karena pada model size yang di inginkan yaitu 321px x 321px
//Camera pasti default potrait krna itu butuh cropping
//pada analyzer aku ingin crop dulu jga. Jadi wkttu anlyzer tdk 4k
class LandmarkImageAnalyzer(
    private val classifier: LandmarkClassifier,
    private val onResult: (List<Classification>) -> Unit
) : ImageAnalysis.Analyzer {
    //Tidak ingin analisis image di setiap single frame dengan sangat cepat kemudian text muncul dengan sangat cepat. Sehingga sulit dibaca
    //Ingin analisis image di 1 second, sehingga aku ingin skip 60 frame setelah analisis frame yang dimana performa better dan result memberikan UX yang lebih baik
    private var frameSkipCounter = 0

    //convert bitmap to AI Model
    override fun analyze(image: ImageProxy) {
        //Ketika framerate ada di 60 frames per second. Baru kita analisis image
        if (frameSkipCounter % 60 == 0){
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(321, 321)

            val result = classifier.classify(bitmap, rotationDegrees)
            onResult(result)
        }
        frameSkipCounter++
        image.close()
    }
}