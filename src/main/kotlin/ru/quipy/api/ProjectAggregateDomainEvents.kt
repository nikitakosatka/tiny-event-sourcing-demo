package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.logic.ProjectUpdateEntity
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val PROJECT_UPDATED_EVENT = "PROJECT_UPDATED_EVENT"
const val PROJECT_DELETED_EVENT = "PROJECT_DELETED_EVENT"
const val TASK_ADDED_EVENT = "TASK_ADDED_EVENT"
const val TASK_REMOVED_EVENT = "TASK_REMOVED_EVENT"

// API
@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = PROJECT_UPDATED_EVENT)
class ProjectUpdatedEvent(
    val projectId: UUID,
    val update: ProjectUpdateEntity,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = PROJECT_DELETED_EVENT)
class ProjectDeletedEvent(
    val projectId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_DELETED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TASK_ADDED_EVENT)
class TaskAddedEvent(
    val projectId: UUID,
    val taskId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_ADDED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TASK_REMOVED_EVENT)
class TaskRemovedEvent(
    val projectId: UUID,
    val taskId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_REMOVED_EVENT,
    createdAt = createdAt,
)