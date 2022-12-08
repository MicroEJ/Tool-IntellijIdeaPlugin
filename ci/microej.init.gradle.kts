/*
 * Kotlin
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
settingsEvaluated {
    val artifactoryCrossUsername: String? = System.getProperty("artifactory.cross.username")
    val artifactoryCrossPassword: String? = System.getProperty("artifactory.cross.password")

    val userId: String? = System.getProperty("user.id")

    val fetchLevel: String = System.getProperty("artifacts.fetch.level") ?: ""
    val publishLevel: String = System.getProperty("artifacts.publish.level") ?: ""

    allprojects {
        /**
         * Fetch repositories
         */
        repositories {
            mavenCentral()
            ivy {
                url = uri("https://repository.microej.com/5/artifacts/")
                patternLayout {
                    artifact("[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]")
                    ivy("[organisation]/[module]/[revision]/ivy-[revision].xml")
                    setM2compatible(true)
                }
            }
            ivy {
                name = "microejCross5Release"
                url = uri("https://artifactory.cross/microej-cross5-release")
                credentials {
                    username = artifactoryCrossUsername
                    password = artifactoryCrossPassword
                }
                patternLayout {
                    artifact("[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]")
                    ivy("[organisation]/[module]/[revision]/ivy-[revision].xml")
                    setM2compatible(true)
                }
            }
            ivy {
                name = "microejCrossRelease"
                url = uri("https://artifactory.cross/microej-cross-release")
                credentials {
                    username = artifactoryCrossUsername
                    password = artifactoryCrossPassword
                }
                patternLayout {
                    artifact("[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]")
                    ivy("[organisation]/[module]/[revision]/ivy-[revision].xml")
                    setM2compatible(true)
                }
            }
            if (fetchLevel == "snapshot" || fetchLevel == "personal") {
                ivy {
                    name = "microejCross5Snapshot"
                    url = uri("https://artifactory.cross/microej-cross5-snapshot")
                    credentials {
                        username = artifactoryCrossUsername
                        password = artifactoryCrossPassword
                    }
                    patternLayout {
                        artifact("[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]")
                        ivy("[organisation]/[module]/[revision]/ivy-[revision].xml")
                        setM2compatible(true)
                    }
                }
                ivy {
                    name = "microejCrossSnapshot"
                    url = uri("https://artifactory.cross/microej-cross-snapshot")
                    credentials {
                        username = artifactoryCrossUsername
                        password = artifactoryCrossPassword
                    }
                    patternLayout {
                        artifact("[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]")
                        ivy("[organisation]/[module]/[revision]/ivy-[revision].xml")
                        setM2compatible(true)
                    }
                }
            }
            if (fetchLevel == "personal") {
                ivy {
                    name = "microejPersonalIvy"
                    url = uri("https://artifactory.cross/personal-${userId}")
                    credentials {
                        username = artifactoryCrossUsername
                        password = artifactoryCrossPassword
                    }
                    patternLayout {
                        artifact("[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]")
                        ivy("[organisation]/[module]/[revision]/ivy-[revision].xml")
                        setM2compatible(true)
                    }
                }
                maven {
                    name = "microejPersonalMaven"
                    url = uri("https://artifactory.cross/personal-${userId}")
                    credentials {
                        username = artifactoryCrossUsername
                        password = artifactoryCrossPassword
                    }
                }
            }
        }

        /**
         * Publish repositories
         */
        pluginManager.withPlugin("maven-publish") {
            configure<PublishingExtension> {
                repositories {
                    if (publishLevel == "personal") {
                        maven {
                            name = "microej"
                            url = uri("https://artifactory.cross/personal-${userId}")
                            credentials {
                                username = artifactoryCrossUsername
                                password = artifactoryCrossPassword
                            }
                        }
                    } else if (publishLevel == "snapshot") {
                        maven {
                            name = "microej"
                            url = uri("https://artifactory.cross/microej-cross5-snapshot")
                            credentials {
                                username = artifactoryCrossUsername
                                password = artifactoryCrossPassword
                            }
                        }
                    } else if (publishLevel == "release") {
                        maven {
                            name = "microej"
                            url = uri("https://artifactory.cross/microej-cross5-release")
                            credentials {
                                username = artifactoryCrossUsername
                                password = artifactoryCrossPassword
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Plugins repositories
     */
    pluginManagement {
        repositories {
            ivy {
                name = "microejPersonalIvy"
                url = uri("https://artifactory.cross/personal-${userId}")
                credentials {
                    username = artifactoryCrossUsername
                    password = artifactoryCrossPassword
                }
                patternLayout {
                    artifact("[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]")
                    ivy("[organisation]/[module]/[revision]/ivy-[revision].xml")
                    setM2compatible(true)
                }
            }
            maven {
                name = "microejPersonalMaven"
                url = uri("https://artifactory.cross/personal-${userId}")
                credentials {
                    username = artifactoryCrossUsername
                    password = artifactoryCrossPassword
                }
            }
            gradlePluginPortal()
        }
    }
}
