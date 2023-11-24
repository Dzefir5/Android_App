package com.example.main_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [Receipt_data::class],
    version = 1
)
@TypeConverters(MyConverters::class)
abstract class MainDataBase: RoomDatabase() {
    abstract val dao:DataDao


    companion object{
        @Volatile
        private var Instance:MainDataBase?=null

        fun createDataBase(context: Context):MainDataBase{
            return Instance?: synchronized(this){
                Room.databaseBuilder(
                    context ,
                    MainDataBase::class.java,
                    "database_01"
                ).build().also { Instance= it }
            }



        }
    }

}