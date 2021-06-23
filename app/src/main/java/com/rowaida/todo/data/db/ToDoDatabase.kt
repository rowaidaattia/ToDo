package com.rowaida.todo.data.db

import android.content.Context
import androidx.room.*

@Database(entities = [UserEntity::class, NoteEntity::class], version = 1)
//@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "todo.db"

        private var instance: ToDoDatabase? = null

        private fun create(context: Context): ToDoDatabase =
            Room.databaseBuilder(context, ToDoDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()


        fun getInstance(context: Context): ToDoDatabase =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun userDao(): UserDao

    abstract fun noteDao(): NoteDao

    abstract fun userWithNotesDao(): UserWithNotesDao

}