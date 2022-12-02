package project.stn991602827.chuantai.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import project.stn991602827.chuantai.data.Run
import project.stn991602827.chuantai.data.RunDao

class EditrunViewModel(private val runDao: RunDao,private val application: Application) : ViewModel() {
    // TODO: Implement the ViewModel
    // need a runDao to work on
    private var viewModelJob = Job()

    private val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)

    fun addARun(run: Run) {
        uiScope.launch {
            insert(run)
        }
    }

    suspend fun insert(run: Run) {
        withContext(Dispatchers.IO) {
            runDao.insertAll(run)
        }
    }

    fun updateARun(run:Run){
        uiScope.launch {
            update(run)
        }
    }

    suspend fun update(run:Run){
        withContext(Dispatchers.IO){

            // TODO compose a run collecting the data from date and time picker, numberpicker and
            // the passed runId

            runDao.updateRun(run)
        }
    }
}