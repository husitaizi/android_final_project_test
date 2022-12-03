package project.stn991602827.chuantai.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import project.stn991602827.chuantai.data.Diet
import project.stn991602827.chuantai.data.DietDao

class EditdietViewModel(
    private val dietDao: DietDao,
    private val application: Application,
) :
    ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun addADiet(diet: Diet) {
        uiScope.launch {
            insert(diet)
        }
    }

    suspend fun insert(diet: Diet) {
        withContext(Dispatchers.IO) {
            dietDao.insertAll(diet)
        }
    }

    fun updateADiet(diet: Diet) {
        uiScope.launch {
            update(diet)
        }
    }

    suspend fun update(diet: Diet) {
        withContext(Dispatchers.IO) {

            dietDao.updateJumping(diet)
        }
    }

}