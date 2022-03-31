plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "com.azkz"
version = "0.0.1-SNAPSHOT"

val ktor_version = "1.6.8"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.azkz.reservationchecker.MainKt")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-gson:$ktor_version")
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}