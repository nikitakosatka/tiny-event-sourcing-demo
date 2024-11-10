package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    private lateinit var projectName: String
    private lateinit var createdAt: LocalDateTime
    private lateinit var deletedAt: LocalDateTime

    var participants = mutableSetOf<UUID>()
    var tasks = mutableSetOf<UUID>()
    var statusNames = mutableSetOf<String>()
    var statuses = mutableMapOf<UUID, StatusEntity>()

    override fun getId() = projectId

    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectName = event.title
        participants.add(event.creatorId)
        createdAt = LocalDateTime.ofEpochSecond(event.createdAt, 0, ZoneOffset.UTC)
    }

    @StateTransitionFunc
    fun projectUpdatedApply(event: ProjectUpdatedEvent) {
        when (event.update.eventType) {
            ProjectUpdateEventType.ADD_USER -> event.update.userId?.let { participants.add(it) }
            ProjectUpdateEventType.REMOVE_USER -> event.update.userId?.let { participants.remove(it) }
            ProjectUpdateEventType.ADD_STATUS -> event.update.status?.let {
                statuses[it.id] = it
                statusNames.add(it.name)
            }
            ProjectUpdateEventType.REMOVE_STATUS -> event.update.status?.let {
                val status = statuses[it.id]
                statuses.remove(it.id)
                statusNames.remove(status?.name)
            }
        }
    }

    @StateTransitionFunc
    fun projectDeletedApply(event: ProjectDeletedEvent) {
        deletedAt = LocalDateTime.ofEpochSecond(event.createdAt, 0, ZoneOffset.UTC)
    }

    @StateTransitionFunc
    fun taskAddedApply(event: TaskAddedEvent) {
        tasks.add(event.taskId)
    }

    @StateTransitionFunc
    fun taskRemovedApply(event: TaskRemovedEvent) {
        tasks.remove(event.taskId)
    }
}

data class StatusEntity(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val color: String
)

data class ProjectUpdateEntity(
    val eventType: ProjectUpdateEventType,
    val userId: UUID?,
    val status: StatusEntity?,
)

enum class ProjectUpdateEventType {
    ADD_USER,
    REMOVE_USER,
    ADD_STATUS,
    REMOVE_STATUS,
}