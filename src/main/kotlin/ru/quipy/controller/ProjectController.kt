package ru.quipy.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.*
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {
    @PostMapping("/{projectName}")
    fun createProject(@PathVariable projectName: String, @RequestParam creatorId: UUID): ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectName, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getState(@PathVariable projectId: UUID): ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PutMapping("/add-user/{projectId}}")
    fun addUserToProject(@PathVariable projectId: UUID, @RequestParam userId: UUID): ProjectUpdatedEvent {
        return projectEsService.update(projectId) {
            it.addUser(userId)
        }
    }

    @PutMapping("/remove-user/{projectId}}")
    fun removeUserFromProject(@PathVariable projectId: UUID, @RequestParam userId: UUID): ProjectUpdatedEvent {
        return projectEsService.update(projectId) {
            it.removeUser(userId)
        }
    }

    @PutMapping("/add-status/{projectId}}")
    fun createStatus(@PathVariable projectId: UUID, @RequestParam status: StatusDto): ProjectUpdatedEvent {
        return projectEsService.update(projectId) {
            it.createStatus(status.name, status.color)
        }
    }

    @PutMapping("/remove-status/{projectId}}")
    fun removeStatus(@PathVariable projectId: UUID, @RequestParam statusId: UUID): ProjectUpdatedEvent {
        return projectEsService.update(projectId) {
            it.removeStatus(statusId)
        }
    }

    @PutMapping("/add-task/{projectId}}")
    fun addTask(@PathVariable projectId: UUID, @RequestParam taskId: UUID): TaskAddedEvent {
        return projectEsService.update(projectId) {
            it.addTask(taskId)
        }
    }

    @PutMapping("/remove-task/{projectId}}")
    fun removeTask(@PathVariable projectId: UUID, @RequestParam taskId: UUID): TaskRemovedEvent {
        return projectEsService.update(projectId) {
            it.removeTask(taskId)
        }
    }

    @DeleteMapping("/{projectId}}")
    fun deleteProject(@PathVariable projectId: UUID): ProjectDeletedEvent {
        return projectEsService.update(projectId) {
            it.delete()
        }
    }
}

data class StatusDto(
    val name: String,
    val color: String,
)