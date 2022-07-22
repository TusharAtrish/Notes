package com.example.notesapplication


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class Note(@ColumnInfo(name="title")var noteTitle:String ,
           @ColumnInfo(name = "description")var noteDescription:String ,
           @ColumnInfo(name = "timestamp")val timeStamp :String,
           @PrimaryKey(autoGenerate = false)
           var id: String = "")