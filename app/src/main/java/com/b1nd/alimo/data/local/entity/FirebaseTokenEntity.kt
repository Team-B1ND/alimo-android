package com.b1nd.alimo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.b1nd.alimo.presentation.utiles.Env


@Entity(Env.FirebaseTokenTable)
data class FirebaseTokenEntity(
    @PrimaryKey
    val idx: Int,
    val fcmToken: String
){
    constructor(fcmToken: String): this(0, fcmToken)
}
