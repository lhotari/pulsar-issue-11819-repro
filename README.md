# Repro for apache/pulsar#11819

This repo is a reproduction of the issue described in apache/pulsar#11819.

Before running, start Pulsar standalone

```shell
docker run --rm -p 8080:8080 -p 6650:6650 apachepulsar/pulsar:4.0.1 bin/pulsar standalone
```

Reproduce the issue

```shell
./gradlew run
```
    
Then run the app with the workaround (`pulsar-client-original` dependency) to see it succeed
    
```shell
./gradlew -PuseWorkaround run
```

There's also another workaround which replaces the Pulsar shaded dependencies with the unshaded ones (`-original`). 
To run it:

```shell
./gradlew -PuseResolutionStrategyWorkaround run
```

Please check the [build.gradle.kts](build.gradle.kts) file for the workarounds. The example is for the Gradle Kotlin syntax. Similar workarounds can be applied to the Groovy syntax.