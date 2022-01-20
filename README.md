# Kutuki_Project

<h3>DEMO</h3>

<img height='450' src="https://user-images.githubusercontent.com/40376163/150238907-03085056-b422-4ac6-900a-fef9242fbb02.gif"/>


# Open Source Library
* [Retrofit](https://square.github.io/retrofit/)
* [Glide](https://github.com/bumptech/glide)
* [RxJava](https://github.com/ReactiveX/RxAndroid)
* [Exo Player](https://github.com/google/ExoPlayer)

# Things we used while making this application
* Exo Player
* RecyclerView
* MVVM
* Data Binding
* RxJava

# Tech Stack ✨
* Kotlin
* Android Studio
* RxJava


# Clone this Repo To Your System Using Android Studio
* Open your Android Studio then go to the File > New > Project from Version Control.
* After clicking on the Project from Version Control a pop-up screen will arise. In the Version control choose Git from the drop-down menu.
* Then at last paste the link in the URL and choose your Directory. Click on the Clone button and you are done.


# Dependencies
```
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-android-extensions'
    id 'kotlin-kapt'
}

buildFeatures {
    dataBinding true
}
```
```
//Retrofit
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:3.12.3'
//Glide
implementation 'com.github.bumptech.glide:glide:4.12.0'
//kapt
kapt 'androidx.room:room-compiler:2.4.1'
implementation 'io.reactivex.rxjava3:rxandroid:3.0.0'
implementation 'io.reactivex.rxjava3:rxjava:3.1.3'
implementation 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'
//expo player
implementation 'com.google.android.exoplayer:exoplayer:2.16.1'
```
