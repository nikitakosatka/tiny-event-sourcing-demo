package ru.quipy.logic

import org.apache.logging.log4j.util.Strings
import ru.quipy.api.*
import java.util.*

fun ProjectAggregateState.create(id: UUID, title: String, creatorId: UUID): ProjectCreatedEvent {
    if (title.isBlank()) {
        throw IllegalArgumentException("Blank name: $title")
    }
    return ProjectCreatedEvent(
        projectId = id,
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectAggregateState.delete(): ProjectDeletedEvent {
    return ProjectDeletedEvent(projectId = this.getId())
}

fun ProjectAggregateState.addUser(userId: UUID): ProjectUpdatedEvent {
    if (participants.contains(userId)) {
        throw IllegalArgumentException("User already added: $userId")
    }
    return ProjectUpdatedEvent(
        projectId = this.getId(),
        update = ProjectUpdateEntity(
            eventType = ProjectUpdateEventType.ADD_USER,
            userId = userId,
            status = null,
        )
    )
}

fun ProjectAggregateState.removeUser(userId: UUID): ProjectUpdatedEvent {
    if (!participants.contains(userId)) {
        throw IllegalArgumentException("User's not project participant: $userId")
    }
    return ProjectUpdatedEvent(
        projectId = this.getId(),
        update = ProjectUpdateEntity(
            eventType = ProjectUpdateEventType.REMOVE_USER,
            userId = userId,
            status = null,
        )
    )
}

fun ProjectAggregateState.createStatus(statusName: String, statusColor: String): ProjectUpdatedEvent {
    if (statusName.isBlank()) {
        throw IllegalArgumentException("Blank status name: $statusName")
    }
    if (statusColor.isBlank()) {
        throw IllegalArgumentException("Blank status color: $statusColor")
    }
    if (statusNames.contains(statusName)) {
        throw IllegalArgumentException("Status already exists: $statusName")
    }
    val status = StatusEntity(
        name = statusName,
        color = statusColor
    )
    return ProjectUpdatedEvent(
        projectId = this.getId(),
        update = ProjectUpdateEntity(
            eventType =  ProjectUpdateEventType.ADD_STATUS,
            userId = null,
            status = status,
        )
    )
}

fun ProjectAggregateState.removeStatus(statusId: UUID): ProjectUpdatedEvent {
    if (!statuses.contains(statusId)) {
        throw IllegalArgumentException("Status's not assigned to project: $statusId")
    }
    val status = StatusEntity(
        id = statusId,
        name = Strings.EMPTY,
        color = Strings.EMPTY
    )
    return ProjectUpdatedEvent(
        projectId = this.getId(),
        update = ProjectUpdateEntity(
            eventType = ProjectUpdateEventType.REMOVE_STATUS,
            userId = null,
            status = status,
        )
    )
}

fun ProjectAggregateState.addTask(taskId: UUID): TaskAddedEvent {
    if (tasks.contains(taskId)) {
        throw IllegalArgumentException("Task already added: $taskId")
    }
    return TaskAddedEvent(
        projectId = this.getId(),
        taskId = UUID.randomUUID(),
    )
}

fun ProjectAggregateState.removeTask(taskId: UUID): TaskRemovedEvent {
    if (!tasks.contains(taskId)) {
        throw IllegalArgumentException("Task's not assigned to project: $taskId")
    }
    return TaskRemovedEvent(
        projectId = this.getId(),
        taskId = taskId,
    )
}
