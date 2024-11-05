package ru.quipy.controller

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
@RequestMapping("/tasks")
class TaskController(
    val taskEsService: EventSourcingService<UUID, TaskAggregate, TaskAggregateState>
) {
    @PostMapping("/create")
    fun createTask(@RequestParam createDto: TaskCreateDto): TaskCreatedEvent {
        return taskEsService.create { it.create(UUID.randomUUID(), createDto.projectId, createDto.name, createDto.userId) }
    }

    @GetMapping("/{taskId}")
    fun getState(@PathVariable taskId: UUID): TaskAggregateState? {
        return taskEsService.getState(taskId)
    }

    @PutMapping("/status/{taskId}}")
    fun setStatus(@PathVariable taskId: UUID, @RequestParam statusId: UUID): TaskUpdatedEvent {
        return taskEsService.update(taskId) {
            it.setStatus(statusId)
        }
    }

    @PutMapping("/rename/{taskId}}")
    fun rename(@PathVariable taskId: UUID, @RequestParam name: String): TaskUpdatedEvent {
        return taskEsService.update(taskId) {
            it.rename(name)
        }
    }
}

data class TaskCreateDto(
    val projectId: UUID,
    val name: String,
    val userId: UUID
)