package project.stn991602827.chuantai.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import project.stn991602827.chuantai.data.RunDao

class ManagerunViewModel(val runDao: RunDao, application: Application) : ViewModel() {
    // TODO: Implement the ViewModel
    // TODO: recyclerview settings bind,holder,count etc
    // define viewModel Factory in a companion object
/*    companion object{
        val Factory:ViewModelProvider.Factory=object:ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>,extras:CreationExtras): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ManagerunViewModel(
                    (application as MyApplication).runDao,
                    application
                ) as T
                // return super.create(modelClass)
            }
        }
    }*/

    val runs = runDao.getAll()

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /*   private var runList = MutableLiveData<MutableList<Run>>()

       //private val _allRun= MutableLiveData<List<Run>>()
       private val _allRun= MutableLiveData<MutableList<Run>>() //<MutableLiveData<Run>()>>

   /*    // interna call refresh() to get a default value for _allRun and allRun
       fun defautValueOfAllRun(){
           refresh()
       }*/

       val allRun:MutableLiveData<MutableList<Run>> get() = _allRun

       init {
           initializeAllRun()
       }

       private fun initializeAllRun(){
           uiScope.launch {
               runList.value = getRunListFromDatabase() as MutableList<Run>
           }
       }

       private suspend fun getRunListFromDatabase():LiveData<List<Run>>?{
           return withContext(Dispatchers.IO){
               runDao.getAll()
           }
       }
      // val runs = runDao.getAll()
   */

/*    val runs=runDao.getAll()

    val _runList= Transformations.map(runs){ runs ->
        toList(runs)
    }

    private fun toList(runs: List<Run>):List<Run> {
        val runList=ArrayList<Run>()
        runList.apply {
            runs.forEach {
                add(Run(it.id,it.date,it.distance))
            }
        }
        return runList
    }*/


    var total = runDao.getTotal(10)
    var totalText=0
    // all ManagerunViewModel need to do is provide the sum of Total/ not yet defined
/*    fun getTotal(): Int {
        var total = 0
        uiScope.launch {
            // update the value of mutableLiveData
            //  _allRun.value= runList.value
            //getAll().value as MutableList<Run>?
            total = total
        }
        return total
    }*/



/*    private suspend fun getTotalFromDatabase(): Int {
        return withContext(Dispatchers.IO) {
            // TODO getTotal not defined in Dao yet
            runDao.getTotal(10)
        }
    }*/

/*    // retrieve all Run in the background
    private suspend fun getAll(): LiveData<List<Run>> {
        return withContext(Dispatchers.IO) {
            // runDao.getAll()
            runDao.getAll()
        }
    }*/


}