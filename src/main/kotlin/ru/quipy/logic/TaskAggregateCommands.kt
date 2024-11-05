package ru.quipy.logic

import org.apache.logging.log4j.util.Strings
import ru.quipy.api.*
import java.util.*

fun TaskAggregateState.create(id: UUID, projectId: UUID, title: String, assignedUserId: UUID): TaskCreatedEvent {
    if (title.isBlank()) {
        throw IllegalArgumentException("Blank name: $title")
    }
    return TaskCreatedEvent(
        taskId = id,
        projectId = projectId,
        title = title,
        statusId = TaskAggregateState.DefaultStatus.STATUS_CREATED_ID,
        assignedUserId = assignedUserId,
    )
}

fun TaskAggregateState.rename(name: String): TaskUpdatedEvent {
    if (name.isBlank()) {
        throw IllegalArgumentException("Blank name: $name")
    }
    return TaskUpdatedEvent(
        taskId = this.getId(),
        update = TaskUpdateEntity(
            eventType =  TaskUpdateEventType.RENAME,
            statusId = null,
            name = name,
        )
    )
}

fun TaskAggregateState.setStatus(statusId: UUID): TaskUpdatedEvent {
    return TaskUpdatedEvent(
        taskId = this.getId(),
        update = TaskUpdateEntity(
            eventType =  TaskUpdateEventType.RENAME,
            statusId = statusId,
            name = null,
        )
    )
}
