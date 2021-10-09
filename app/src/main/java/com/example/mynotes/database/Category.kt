package com.example.mynotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Category (

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "Category_id")
        val Category_id: Long,

    @ColumnInfo (name = "Category_name")
        val Category_name:String

        ){
}