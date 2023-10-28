import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.main_project.data.MainDataBase
import com.example.main_project.data.Receipt_data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application){


    val userDao = MainDataBase.createDataBase(application).dao
    init{

        //repository = LocalRepository(userDao)
        //getAllNotes = repository.getAll()
    }

    fun addNote(receiptData: Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
            userDao.InsertData(receiptData)
        }
    }
    fun EditNote(receiptData:Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
          //  repository.updateData(noteEntity)
        }
    }
    fun deleteNote(receiptData:Receipt_data){
        viewModelScope.launch(Dispatchers.IO) {
            userDao.DeleteData(receiptData)
        }
    }


}