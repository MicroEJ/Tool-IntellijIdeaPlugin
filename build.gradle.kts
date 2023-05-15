/*
 * Kotlin
 *
 * Copyright 2022-2023 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.4.0"
    id("maven-publish")
}

group = "com.microej.tools"
version = "0.3.0"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("java"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("212")
        untilBuild.set("")
    }

    // To be configured to publish the plugin on the Jetbrains marketplace
    // signPlugin {
    //     certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    //     privateKey.set(System.getenv("PRIVATE_KEY"))
    //     password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    // }
    //
    // publishPlugin {
    //     token.set(System.getenv("PUBLISH_TOKEN"))
    // }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
