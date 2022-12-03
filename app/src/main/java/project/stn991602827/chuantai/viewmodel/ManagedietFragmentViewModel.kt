package project.stn991602827.chuantai.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import project.stn991602827.chuantai.data.Diet
import project.stn991602827.chuantai.data.DietDao

class ManagedietFragmentViewModel(val dietDao: DietDao, application: Application) : ViewModel() {
    // TODO: Implement the ViewModel

    private var viewModelJob= Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var total=dietDao.getTotal(10)
    var diets=dietDao.getAll()

    // handle delete here
    fun delete(diet: Diet){
        uiScope.launch {
            deleteFromDatabase(diet)
        }
    }
    suspend fun deleteFromDatabase(diet: Diet){
        withContext(Dispatchers.IO){
            dietDao.delete(diet)
        }
    }

}