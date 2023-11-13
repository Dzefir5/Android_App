package com.example.main_project.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao {

    @Insert
    suspend fun InsertData(receiptData:Receipt_data)

    @Delete
    suspend fun DeleteData(receiptData:Receipt_data)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun UpdateData(receiptData:Receipt_data)


    @Query("SELECT * FROM MainTable ORDER BY receiptId ASC")
    fun getAllData(): Flow<List<Receipt_data>>
}