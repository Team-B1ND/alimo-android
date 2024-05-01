package com.b1nd.alimo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.b1nd.alimo.data.model.ExampleModel
import com.b1nd.alimo.presentation.utiles.Env

@Entity(
    tableName = Env.ExampleTable
)
data class ExampleEntity(
    @PrimaryKey(autoGenerate = true) val idx: Int,
    val name: String,
) {
    constructor(name: String) : this(0, name)

    fun toModel() = ExampleModel(
        id = idx,
        name = name
    )
}