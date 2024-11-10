package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.logic.UserProfileEntity
import java.util.*

const val USER_CREATED_EVENT = "USER_CREATED_EVENT"
const val USER_PROFILE_UPDATED_EVENT = "USER_PROFILE_UPDATED_EVENT"

// API
@DomainEvent(name = USER_CREATED_EVENT)
class UserCreatedEvent(
    val userId: UUID,
    val nickname: String,
    val userName: String,
    val password: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<UserAggregate>(
    name = USER_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = USER_PROFILE_UPDATED_EVENT)
class UserProfileUpdatedEvent(
    val userId: UUID,
    val profile: UserProfileEntity,
    createdAt: Long = System.currentTimeMillis(),
) : Event<UserAggregate>(
    name = USER_PROFILE_UPDATED_EVENT,
    createdAt = createdAt,
)
