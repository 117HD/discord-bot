plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.20"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.bot"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.github.microutils:kotlin-logging:2.1.23")
    implementation("net.dv8tion:JDA:6.1.1")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("commons-lang:commons-lang:2.6")
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("org.reflections:reflections:0.10.2")
}

application {
    mainClass.set("com.bot.ApplicationKt")
}

kotlin {
    jvmToolchain(11)
}

tasks {
    shadowJar {
        archiveBaseName.set("117hd-discord-bot")
        archiveClassifier.set("")
        archiveVersion.set("")
        manifest {
            attributes(mapOf("Main-Class" to "com.bot.ApplicationKt"))
        }
        mergeServiceFiles()
    }

    named<JavaExec>("run") {
        isEnabled = false
        group = null
    }
}
