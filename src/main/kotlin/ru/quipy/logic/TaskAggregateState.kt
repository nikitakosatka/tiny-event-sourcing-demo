package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

// Service's business logic
class TaskAggregateState : AggregateState<UUID, TaskAggregate> {
    private lateinit var taskId: UUID
    private lateinit var name: String
    private lateinit var projectId: UUID
    private lateinit var statusId: UUID
    private lateinit var assignedUserId: UUID
    private lateinit var createdAt: LocalDateTime
    private lateinit var completedAt: LocalDateTime

    override fun getId() = taskId

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        taskId = event.taskId
        projectId = event.projectId
        name = event.title
        statusId = event.statusId
        assignedUserId = event.assignedUserId
        createdAt = LocalDateTime.ofEpochSecond(event.createdAt, 0, ZoneOffset.UTC)
    }

    @StateTransitionFunc
    fun taskUpdatedApply(event: TaskUpdatedEvent) {
        when (event.update.eventType) {
            TaskUpdateEventType.SET_STATUS -> {
                if (event.update.statusId == DefaultStatus.STATUS_COMPLETED_ID) {
                    completedAt = LocalDateTime.now()
                    statusId = event.update.statusId
                }
            }
            TaskUpdateEventType.RENAME -> name = event.update.name!!
        }
    }

    object DefaultStatus {
        val STATUS_CREATED_ID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")
        val STATUS_COMPLETED_ID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000001")
    }
}
data class TaskUpdateEntity(
    val eventType: TaskUpdateEventType,
    val statusId: UUID?,
    val name: String?,
)

enum class TaskUpdateEventType {
    SET_STATUS,
    RENAME,
}