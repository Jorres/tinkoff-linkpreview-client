plugins {
    kotlin("js") version "1.4.32"
    // kotlin("jvm") version "1.4.32"
    kotlin("plugin.serialization") version "1.4.32"
}

group = "me.jorres"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-js"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

    implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.2")
    implementation("io.ktor:ktor-client-js:1.5.3")
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}