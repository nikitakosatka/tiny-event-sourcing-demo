package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = UserAggregate::class, subscriberName = "user-subscriber",
)
class AnnotationBasedUserEventsSubscriber {
    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedUserEventsSubscriber::class.java)

    @SubscribeEvent
    fun userCreatedSubscriber(event: UserCreatedEvent) {
        logger.info("User ${event.userId} created")
    }

    @SubscribeEvent
    fun userProfileUpdatedSubscriber(event: UserProfileUpdatedEvent) {
        logger.info("User {} profile updated: {}", event.userId, event.profile)
    }
}