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
@RequestMapping("/users")
class UserController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {
    @PostMapping("/create")
    fun createUser(@RequestParam createDto: UserCreateDto): UserCreatedEvent {
        return userEsService.create { it.create(UUID.randomUUID(), createDto.nickname, createDto.name, createDto.password) }
    }

    @GetMapping("/{userId}")
    fun getState(@PathVariable userId: UUID): UserAggregateState? {
        return userEsService.getState(userId)
    }

    @PutMapping("/profile/{userId}")
    fun setStatus(@PathVariable userId: UUID, @RequestParam profile: UserProfileEntity): UserProfileUpdatedEvent {
        return userEsService.update(userId) {
            it.updateProfile(profile)
        }
    }
}

data class UserCreateDto(
    val nickname: String,
    val name: String,
    val password: String,
)