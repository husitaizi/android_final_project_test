package project.stn991602827.chuantai.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import project.stn991602827.chuantai.data.Run
import project.stn991602827.chuantai.data.RunDao

class ManagerunViewModel(val runDao: RunDao, application: Application) : ViewModel() {

    val runs = runDao.getAll()

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var total = runDao.getTotal(10)
    var totalText = 0

    // handle Delete a recored here
    fun delete(run: Run) {
        uiScope.launch {
            deleteFromDatabase(run)
        }
    }

    suspend fun deleteFromDatabase(run: Run) {
        withContext(Dispatchers.IO) {
            runDao.delete(run)
        }


    }
}

