package test

import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.impl.schema.AvroSchema
import java.time.Instant

fun main() {
    PulsarClient.builder()
        .serviceUrl("pulsar://localhost:6650")
        .build().use { pulsarClient ->
            val producer = pulsarClient.newProducer(AvroSchema.of(SomeObject::class.java))
                .topic("some-topic")
                .create()
            val someObject = SomeObject.newBuilder()
                .setStartedAt(Instant.now())
                .build()
            val messageId = producer.newMessage().value(someObject).send()
            println("Sent message successfully with message ID: $messageId")
        }
}