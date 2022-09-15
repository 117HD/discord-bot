
plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

application {
    mainClass.set("com.bot.ApplicationKt")
}

group = "com.bot"
version = "1.0"

repositories {
    mavenCentral()
}
dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.github.microutils:kotlin-logging:2.1.23")
    implementation("net.dv8tion:JDA:5.0.0-alpha.19")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("commons-lang:commons-lang:2.6")
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("org.reflections:reflections:0.10.2")

}
