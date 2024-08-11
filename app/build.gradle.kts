plugins {
    alias(libs.plugins.androidApplication)
}
android {
    namespace = "com.hris365"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hris365"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    dataBinding {
        enable = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.jakewharton:butterknife:10.2.3")
    implementation(libs.sdp.android)
    //noinspection UseTomlInstead

    /*implementation 'androidx.appcompat:appcompat:1.5.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'*/
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)

    implementation ("com.github.rey5137:material:1.3.1")
    implementation ("com.android.support:design:28.0.0")
    implementation ("com.github.traex.rippleeffect:library:1.3")
    implementation ("jp.wasabeef:recyclerview-animators:4.0.2")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("com.google.firebase:firebase-messaging:17.3.4")
    implementation ("com.squareup.retrofit2:retrofit:2.7.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.10.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.7.1")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:okhttp-tls:4.9.0")
    implementation ("com.intuit.sdp:sdp-android:1.0.5")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.firebase:firebase-messaging:23.4.1")
    implementation ("com.google.android.gms:play-services-location:21.2.0")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")
//    implementation ("id.zelory:compressor:3.0.1")
}