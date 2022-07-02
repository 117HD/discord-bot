
plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "1.7.0"
}

group = "com.bot"
version = "1.0"

repositories {
    mavenCentral()
}
dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.github.microutils:kotlin-logging:2.1.23")
    // https://mvnrepository.com/artifact/net.dv8tion/JDA
    implementation("net.dv8tion:JDA:5.0.0-alpha.13")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("commons-lang:commons-lang:2.6")
    implementation("org.jsoup:jsoup:1.14.3")

}
