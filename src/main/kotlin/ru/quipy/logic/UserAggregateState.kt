package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

// Service's business logic
class UserAggregateState : AggregateState<UUID, UserAggregate> {
    private lateinit var userId: UUID
    private lateinit var nickname: String
    private lateinit var name: String
    private lateinit var createdAt: LocalDateTime

    override fun getId() = userId

    @StateTransitionFunc
    fun userCreatedApply(event: UserCreatedEvent) {
        userId = event.userId
        nickname = event.nickname
        name = event.userName
        createdAt = LocalDateTime.ofEpochSecond(event.createdAt, 0, ZoneOffset.UTC)
    }

    @StateTransitionFunc
    fun userProfileUpdatedApply(event: UserProfileUpdatedEvent) {
        if (event.profile.nickname?.isNotBlank() == true) {
            nickname = event.profile.nickname
        }
        if (event.profile.name?.isNotBlank() == true) {
            name = event.profile.name
        }
    }
}

data class UserProfileEntity(
    val nickname: String?,
    val name: String?,
)
