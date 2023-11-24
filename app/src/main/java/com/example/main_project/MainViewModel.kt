import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.main_project.data.LocalRepository
import com.example.main_project.data.MainDataBase
import com.example.main_project.data.Receipt_data
import com.example.main_project.data.Receipt_step
import com.example.main_project.data.RemoteRepository
import com.example.main_project.deleteFromInternalStorage
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.sql.Blob
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application){
    var status : Status = Status.WATCHING

    var search by mutableStateOf(false)
    var searchString by mutableStateOf("")


    var remoteDatabase = Firebase.firestore
    //val remoteStorage = Firebase.storage.getReference("gs://main-project-fe50b.appspot.com")

    val repository:LocalRepository
    lateinit var remoteRepository:RemoteRepository

    var getAllData: Flow<List<Receipt_data>>
    var getSearchData: Flow<List<Receipt_data>>
    init{
        //remoteRepository=RemoteRepository(remoteDatabase)
        val userDao = MainDataBase.createDataBase(application).dao
         repository = LocalRepository(userDao)
         remoteRepository= RemoteRepository(remoteDatabase,Firebase.storage)
         getAllData = repository.getAll()
        getSearchData = repository.getSearch(searchString)
    }
    fun addReceipt(receiptData: Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
            for( i in 0 ..  receiptData.stepsList.size-1){
                receiptData.stepsList[i].stepId=i
            }
            for( i in 0 ..  receiptData.ingredientsList.size-1){
                receiptData.ingredientsList[i].ingredientsId=i
            }
            repository.insertData(receiptData)

        }
    }
    fun updateReceipt(receiptData:Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
            for( i in 0 ..  receiptData.stepsList.size-1){
                receiptData.stepsList[i].stepId=i
            }
            for( i in 0 ..  receiptData.ingredientsList.size-1){
                receiptData.ingredientsList[i].ingredientsId=i
            }
            repository.updateData(receiptData)
        }
    }
    fun deleteReceipt(receiptData:Receipt_data,context:Context){
        viewModelScope.launch(Dispatchers.IO) {
            deleteFromInternalStorage(receiptData.imageBmp,context)
            receiptData.stepsList.forEach(){receiptStep ->
                deleteFromInternalStorage(receiptStep.image,context)
            }
            repository.deleteData(receiptData)
        }
    }

    var Step = Receipt_step()
    var EditedReceipt by mutableStateOf( Receipt_data())
    var ingredientAmount by mutableStateOf(0)
    var stepsAmount by mutableStateOf(0)


}

enum class Status{
    CREATING,
    EDITING,
    WATCHING
}

