import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.main_project.data.LocalRepository
import com.example.main_project.data.MainDataBase
import com.example.main_project.data.Receipt_data
import com.example.main_project.data.Receipt_step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application){
    var status : Status = Status.WATCHING
    private val repository:LocalRepository
    var getAllData: Flow<List<Receipt_data>>
    init{
        val userDao = MainDataBase.createDataBase(application).dao
         repository = LocalRepository(userDao)
         getAllData = repository.getAll()
    }

    fun addReceipt(receiptData: Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(receiptData)
        }
    }
    fun updateReceipt(receiptData:Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(receiptData)
        }
    }
    fun deleteReceipt(receiptData:Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
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







