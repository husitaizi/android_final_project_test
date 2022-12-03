package project.stn991602827.chuantai.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import project.stn991602827.chuantai.data.Jumping
import project.stn991602827.chuantai.data.JumpingDao

class EditjumpViewModel(
    private val jumpingDao: JumpingDao,
    private val application: Application,
    private val jumpId: Int,
) :
    ViewModel() {
    // TODO: Implement the ViewModel
    // need a jumpingDao to work on
    //var jumpToBeUpdated = MutableLiveData<Jumping>()

    var jumpToBeUpdated = jumpingDao.loadAllByIds(intArrayOf(jumpId))

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

/*    init {
        getTheJumpToBeUpdated(jumpId)
    }*/

    fun addAJump(jumping: Jumping) {
        uiScope.launch {
            insert(jumping)
        }
    }

    suspend fun insert(jumping: Jumping) {
        withContext(Dispatchers.IO) {
            jumpingDao.insertAll(jumping)
        }
    }

    fun updateAJump(jumping: Jumping) {
        uiScope.launch {
            update(jumping)
        }
    }

    suspend fun update(jumping: Jumping) {
        withContext(Dispatchers.IO) {

            jumpingDao.updateJumping(jumping)
        }
    }

    // TODO get the to_be_edited jump, to show the info in the screen
    // or if i can make the date time picker to show the value, but whatever we need the
    // to_be_edited Jump info first
/*    fun getTheJumpToBeUpdated(jumpId: Int) {

        uiScope.launch {
           jumpToBeUpdated.value = getTheUpdatingJumpFromDatabase(jumpId)!!
        }
    }*/


/*    suspend fun getTheUpdatingJumpFromDatabase(jumpId: Int): Jumping {
        // encapsulate the jumpId into IntArray[1]
        var ja: IntArray = intArrayOf(jumpId)
        // define a local variable to get the result from suspend operation
        var jumpToBeUpdated: Jumping
        withContext(Dispatchers.IO) {
            // get the Jump in List<Jumping>
            jumpToBeUpdated = jumpingDao.loadAllByIds(ja).get(0)

        }
        return jumpToBeUpdated
    }*/

}