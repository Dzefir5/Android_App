import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.main_project.data.LocalRepository
import com.example.main_project.data.MainDataBase
import com.example.main_project.data.Receipt_data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application){
    private val repository:LocalRepository
    init{
        val userDao = MainDataBase.createDataBase(application).dao
         repository = LocalRepository(userDao)
        val getAllData = repository.getAll()
    }

    fun addNote(receiptData: Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(receiptData)
        }
    }
    fun EditNote(receiptData:Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(receiptData)
        }
    }
    fun deleteNote(receiptData:Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(receiptData)
        }
    }


}