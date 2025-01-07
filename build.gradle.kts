plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.avro:avro:1.11.4")
    if (findProperty("useWorkaround") != null) {
        implementation("org.apache.pulsar:pulsar-client-original:4.0.1")
    } else {
        implementation("org.apache.pulsar:pulsar-client:4.0.1")
    }
}

application {
    mainClass.set("test.AppKt")
}