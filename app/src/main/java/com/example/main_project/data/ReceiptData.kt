package com.example.main_project.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class MyConverters {
    @TypeConverter
    fun listToJson(value: List<Receipt_step>?) = Json.encodeToString(value)

    @TypeConverter
    fun jsonToList(value: String) = Json.decodeFromString<List<Receipt_step>>(value)
}
@Serializable
@Entity(tableName="MainTable")
data class Receipt_data(
    @PrimaryKey
    val receiptId : Int,
    val name : String,
    val description : String,
    val stepsList : List<Receipt_step>,
    //val imageList : List<Receipt_image>,
   // val ingredientsList : List<Receipt_ingredient>,
)

@Entity
@Serializable
data class Receipt_image(
    @PrimaryKey
    val imageId : Int,
    val path : String
)

@Entity
@Serializable
data class Receipt_ingredient(
    @PrimaryKey
    val ingredientsId : Int,
    val name : String,
    val amount : String
)

@Entity
@Serializable
data class Receipt_step(
    @PrimaryKey
    val stepId : Int,
    val name : String,
   // val description : String,
   // val image : String
)


