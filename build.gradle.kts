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
    // when using the workaround, it's necessary to ensure that Netty libraries are aligned and use a recent Netty version
    implementation(platform("io.netty:netty-bom:4.1.116.Final"))
}

application {
    mainClass.set("test.AppKt")
}

if (findProperty("useResolutionStrategyWorkaround") != null) {
    configurations {
        all {
            resolutionStrategy {
                eachDependency {
                    if ((requested.group == "org.apache.bookkeeper" || requested.group == "io.streamnative") &&
                        requested.name in listOf("circe-checksum", "cpu-affinity", "native-io")
                    ) {
                        // Workaround for invalid metadata for Bookkeeper dependencies which contain
                        // <packaging>nar</packaging> in pom.xml
                        artifactSelection {
                            selectArtifact("jar", null, null)
                        }
                    } else if (requested.name == "pulsar-client" || requested.name == "pulsar-client-all") {
                        // replace pulsar-client and pulsar-client-all with pulsar-client-original
                        useTarget("${requested.group}:pulsar-client-original:${requested.version}")
                    } else if (requested.name == "pulsar-client-admin") {
                        // replace pulsar-client-admin with pulsar-client-admin-original
                        useTarget("${requested.group}:pulsar-client-admin-original:${requested.version}")
                    }
                }
            }
        }
    }
}