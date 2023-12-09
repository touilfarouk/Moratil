package com.example.moratil.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moratil.domain.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = true)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}