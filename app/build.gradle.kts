plugins {
    id("com.android.application")
    id("com.google.gms.google-services")//firebase
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")//parcebol
}

android {
    namespace = "com.example.upeapp12"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.upeapp12"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.squareup.picasso:picasso:2.8")//picasso
    //dependencia firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    implementation("com.google.firebase:firebase-analytics")//analitics
    implementation("com.google.firebase:firebase-auth-ktx")//auth
    implementation("com.google.firebase:firebase-firestore-ktx")//cloub fire store
    implementation("com.google.firebase:firebase-storage-ktx")//cloud storeg
    implementation("com.google.firebase:firebase-database")//database
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}