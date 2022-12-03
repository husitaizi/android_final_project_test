package project.stn991602827.chuantai.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import project.stn991602827.chuantai.data.Jumping
import project.stn991602827.chuantai.data.JumpingDao

class ManagejumpingFragmentViewModel(val jumpingDao: JumpingDao,application: Application) : ViewModel() {
    // TODO: Implement the ViewModel

    private var viewModelJob= Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var total=jumpingDao.getTotal(10)
    var jumps=jumpingDao.getAll()

    // handle delete here
    fun delete(jump:Jumping){
        uiScope.launch {
            deleteFromDatabase(jump)
        }
    }
    suspend fun deleteFromDatabase(jump: Jumping){
        withContext(Dispatchers.IO){
            jumpingDao.delete(jump)
        }
    }

}