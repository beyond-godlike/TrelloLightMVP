package com.unava.dia.trellolightmvp.api.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Board(var title: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @Ignore
    var tasks: List<Task>? = null
}