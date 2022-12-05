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
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Jumping
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.JumpingDao

/**
 * The class defines mostly the relevant database operations.
 */
class EditjumpViewModel(
    private val jumpingDao: JumpingDao,
    private val application: Application,
    private val jumpId: Int,
) :
    ViewModel() {

    // Defines a Job.
    private var viewModelJob = Job()

    // Defines a UI Scope.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Call the database insert() fun on uiScope.
     */
    fun addAJump(jumping: Jumping) {
        uiScope.launch {
            insert(jumping)
        }
    }

    /**
     * Commit insert operation on database in the background.
     */
    suspend fun insert(jumping: Jumping) {
        withContext(Dispatchers.IO) {
            jumpingDao.insertAll(jumping)
        }
    }

    /**
     * Call the database update fun on uiScope.
     */
    fun updateAJump(jumping: Jumping) {
        uiScope.launch {
            update(jumping)
        }
    }

    /**
     * Commit update operation on database in the background.
     */
    suspend fun update(jumping: Jumping) {
        withContext(Dispatchers.IO) {

            jumpingDao.updateJumping(jumping)
        }
    }

}