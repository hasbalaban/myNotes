package com.example.mynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.locks.Lock
import javax.inject.Inject


@Database(entities = arrayOf(Notes::class,Category::class), exportSchema = false, version =1)
abstract class NoteDatabaseService :
    RoomDatabase() {

   abstract fun noteDao(): NoteDao

    companion object {


        @Volatile
        private var instance: NoteDatabaseService? = null
        private val Lock = Any()


        operator fun invoke(context: Context) = instance ?: kotlin.synchronized(Lock) {

            instance ?: makeDatabase(context).also {
                instance = it

            }

        }

        fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabaseService::class.java,
            "NotesDatabase"
        ).build()


    }


}

