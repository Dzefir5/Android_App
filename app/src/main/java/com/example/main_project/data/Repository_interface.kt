package com.example.main_project.data

import kotlinx.coroutines.flow.Flow

interface Repository{
    suspend fun insertData(receiptData:Receipt_data)

    suspend fun deleteData(receiptData:Receipt_data)

    suspend fun updateData(receiptData:Receipt_data)

    fun getAll(): Flow<List<Receipt_data>>

}