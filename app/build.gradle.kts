plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.monthlyreport"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.monthlyreport"
        minSdk = 24
        targetSdk = 34
        versionCode = 7
        versionName = "0.1.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {arg("room.schemaLocation", "$projectDir/schemas")}
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            isDebuggable = false
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

    implementation("androidx.datastore:datastore-preferences-rxjava2:1.1.1")
    implementation("androidx.datastore:datastore-preferences-rxjava3:1.1.1")
    implementation("androidx.datastore:datastore-preferences-core:1.1.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-rxjava2:2.6.1")
    implementation("androidx.room:room-rxjava3:2.6.1")
    implementation("androidx.room:room-guava:2.6.1")
    implementation("androidx.room:room-testing:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
}