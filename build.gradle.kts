object Versions {
    const val kotlin: String = "1.3.71"
    const val spek: String = "2.0.9"
    const val junit: String = "5.6.2"
    const val tornadofx: String = "1.7.17"
}

plugins {
    kotlin("jvm") version "1.3.71"
    application
}

group = "gov.nasa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClassName = "gov.nasa.mars.MainKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:${Versions.junit}")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform {
            includeEngines("spek2")
        }
    }
}

