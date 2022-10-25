package com.tjEnterprises.phase10Counter.data.highscores

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Highscores (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "playerName") val playerName: String,
    @ColumnInfo(name = "punkte") val punkte: Int
)