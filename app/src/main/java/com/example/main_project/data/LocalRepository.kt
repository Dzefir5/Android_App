package com.example.main_project.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LocalRepository(private val userDao:DataDao) : Repository{
    override suspend fun insertData(receiptData: Receipt_data) =userDao.InsertData(receiptData)

   override suspend fun updateData(receiptData: Receipt_data) =userDao.UpdateData(receiptData)

    override suspend fun deleteData(receiptData: Receipt_data) {
        userDao.DeleteData(receiptData)
    }

    fun getAll():Flow<List<Receipt_data>> =userDao.getAllData()
    fun getSearch(search:String): Flow<List<Receipt_data>> =userDao.getByName(search)
}