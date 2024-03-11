package com.b1nd.alimo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.b1nd.alimo.presentation.utiles.Env

@Entity(Env.TokenTable)
data class TokenEntity(
    @PrimaryKey val idx: Int,
    val token: String,
    val refreshToken: String
) {
    constructor(token: String, refreshToken: String) : this(0, token, refreshToken)
}