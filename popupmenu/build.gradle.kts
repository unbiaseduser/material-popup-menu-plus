plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
}

android {
    compileSdk = 33

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    defaultConfig {
        minSdk = 17
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    namespace = "com.sixtyninefourtwenty.materialpopupmenu"

}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.sixtyninefourtwenty"
            artifactId = "material-popup-menu-plus"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("material-popup-menu-plus")
                description.set("Fork of MaterialPopupMenu with more features. I guess.")

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("unbiaseduser")
                        name.set("Dang Quang Trung")
                        email.set("quangtrung02hn16@gmail.com")
                        url.set("https://github.com/unbiaseduser")
                    }
                }
            }
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.core:core:1.10.1")

    testImplementation("junit:junit:4.13.2")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs_minimal:2.0.3")

}
