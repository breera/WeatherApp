plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    testOptions {
        tasks.withType<Test> {
            useJUnitPlatform() // Enables JUnit 5
        }
    }
    namespace = "com.breera.feature_home"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL")}\"")
            buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY")}\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL")}\"")
            buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY")}\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        this.unitTests.isReturnDefaultValues = true
        this.unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui)
    implementation(libs.bundles.koin)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.sdp.compose)
    implementation(libs.compose.constraint.layout)
    implementation(project(":theme"))
    implementation(project(":core"))
    testImplementation(libs.kotlinx.coroutines.test)
    implementation(libs.bundles.coil)
    debugImplementation(libs.ui.tooling)
    testImplementation(libs.bundles.test.dependencies)
   // testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

subprojects {
    tasks.withType<Test> {
        useJUnitPlatform() // Enables JUnit 5
    }
}