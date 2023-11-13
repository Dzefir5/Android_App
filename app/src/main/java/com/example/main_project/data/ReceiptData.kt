package com.example.main_project.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream

@Serializable
class MyConverters {
    @TypeConverter
    fun listToJson1(value: List<Receipt_step>?) = Json.encodeToString(value)
    @TypeConverter
    fun jsonToList1(value: String) = Json.decodeFromString<List<Receipt_step>>(value)
    @TypeConverter
    fun listToJson2(value: List<Receipt_image>?) = Json.encodeToString(value)
    @TypeConverter
    fun jsonToList2(value: String) = Json.decodeFromString<List<Receipt_image>>(value)
    @TypeConverter
    fun listToJson3(value: List<Receipt_ingredient>?) = Json.encodeToString(value)
    @TypeConverter
    fun jsonToList3(value: String) = Json.decodeFromString<List<Receipt_ingredient>>(value)

    @TypeConverter
    fun listToMutList1(value: MutableList<Receipt_step>?) = value?.toList()
    @TypeConverter
    fun MutListTolist1(value: List<Receipt_step>?) =value?.toMutableList()
    @TypeConverter
    fun listToMutList2(value: MutableList<Receipt_image>?) = value?.toList()
    @TypeConverter
    fun MutListTolist2(value: List<Receipt_image>?) =value?.toMutableList()
    @TypeConverter
    fun listToMutList3(value: MutableList<Receipt_ingredient>?) = value?.toList()
    @TypeConverter
    fun MutListTolist3(value: List<Receipt_ingredient>?) =value?.toMutableList()

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap):ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
    @TypeConverter
    fun toBitmap(byteArray: ByteArray):Bitmap{
        return BitmapFactory.decodeByteArray(byteArray,0, byteArray.size)
    }


}
@Serializable
@Entity(tableName="MainTable")
data class Receipt_data(
    @PrimaryKey(autoGenerate = true)
    var receiptId : Int=0,
    var name : String="",
    var imageBmp : ByteArray =  byteArrayOf(0,1,0),
    var description : String="",
    var stepsList : MutableList<Receipt_step> = mutableListOf(),
    var imageList : MutableList<Receipt_image> = mutableListOf(),
    var ingredientsList : MutableList<Receipt_ingredient> = mutableListOf(),
)
@Entity
@Serializable
data class Receipt_image(
    @PrimaryKey
    var imageId : Int=0,
    //var imageBmp : ByteArray =  byteArrayOf()
)
@Serializable
@Entity
data class Receipt_ingredient(
    @PrimaryKey
    var ingredientsId : Int=0,
    var name : String="",
    var amount : String="",
    var units : String=""
)
@Serializable
@Entity
data class Receipt_step(
    @PrimaryKey
    var stepId : Int=0,
    var name : String="",
    var description : String="",
    var image : ByteArray =  byteArrayOf(0,1,0)
)


