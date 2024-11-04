package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "project-subscriber",
)
class AnnotationBasedProjectEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedProjectEventsSubscriber::class.java)

    @SubscribeEvent
    fun projectCreatedSubscriber(event: ProjectCreatedEvent) {
        logger.info("Project ${event.projectId} created")
    }

    @SubscribeEvent
    fun projectUpdatedSubscriberSubscriber(event: ProjectUpdatedEvent) {
        logger.info("Project updated: {}", event.update)
    }

    @SubscribeEvent
    fun projectDeletedSubscriber(event: ProjectDeletedEvent) {
        logger.info("Project ${event.projectId} deleted")
    }

    @SubscribeEvent
    fun taskAddedSubscriber(event: TaskAddedEvent) {
        logger.info("Task ${event.taskId} added to project ${event.projectId}")
    }

    @SubscribeEvent
    fun taskRemovedSubscriber(event: TaskRemovedEvent) {
        logger.info("Task ${event.taskId} removed from project ${event.projectId}")
    }
}