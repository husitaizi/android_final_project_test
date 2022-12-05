package project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class defines mostly the relevant database operations.
*/
import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Diet
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.DietDao

/**
 * The class defines mostly the relevant database operations.
 */
class EditdietViewModel(
    private val dietDao: DietDao,
    private val application: Application,
) :
    ViewModel() {

    // Defines a Job.
    private var viewModelJob = Job()

    // Defines a UI Scope.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Call the database insert() fun on uiScope.
     */
    fun addADiet(diet: Diet) {
        uiScope.launch {
            insert(diet)
        }
    }

    /**
     * Commit insert operation on database in the background.
     */
    suspend fun insert(diet: Diet) {
        withContext(Dispatchers.IO) {
            dietDao.insertAll(diet)
        }
    }

    /**
     * Call the database update fun on uiScope.
     */
    fun updateADiet(diet: Diet) {
        uiScope.launch {
            update(diet)
        }
    }

    /**
     * Commit update operation on database in the background.
     */
    suspend fun update(diet: Diet) {
        withContext(Dispatchers.IO) {
            dietDao.updateJumping(diet)
        }
    }
}