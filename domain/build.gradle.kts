plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // okHttp3, logger
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    //Kotlinx - for Flow
    implementation(libs.kotlinx.coroutines.core)

    // Hilt
    implementation(libs.hilt.core)
}