package ru.quipy.logic

import org.apache.logging.log4j.util.Strings
import ru.quipy.api.*
import java.util.*

fun UserAggregateState.create(id: UUID, nickname: String, name: String, password: String): UserCreatedEvent {
    if (nickname.isBlank()) {
        throw IllegalArgumentException("Blank nickname: $nickname")
    }
    if (name.isBlank()) {
        throw IllegalArgumentException("Blank name: $name")
    }
    if (password.length < 6) {
        throw IllegalArgumentException("Password length must be more then 6")
    }
    return UserCreatedEvent(
        userId = id,
        nickname = nickname,
        userName = name,
        password = password,
    )
}

fun UserAggregateState.updateProfile(profile: UserProfileEntity): UserProfileUpdatedEvent {
    return UserProfileUpdatedEvent(
        userId = this.getId(),
        profile = profile
    )
}
