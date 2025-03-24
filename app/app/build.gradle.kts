plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "bitc.fullstack.FlightLog"
  compileSdk = 35

  defaultConfig {
    applicationId = "bitc.fullstack.FlightLog"
    minSdk = 28
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  viewBinding{
    enable = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)


// https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
  implementation(libs.retrofit)

  // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
  implementation(libs.converter.gson)

  // https://mvnrepository.com/artifact/com.google.code.gson/gson
  implementation(libs.gson)

  // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-scalars
  implementation(libs.converter.scalars)

  // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-simplexml
  implementation(libs.converter.simplexml)



  //api 서버 로그 확인용라이브러리
  implementation("com.squareup.okhttp3:okhttp:4.9.2")
  implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")
}