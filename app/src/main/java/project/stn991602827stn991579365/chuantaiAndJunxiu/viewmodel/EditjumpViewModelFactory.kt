package project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel


/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class defines a viewModel factory of Editjump.
*/
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.JumpingDao

/**
 * The class is a factory used to initialize a viewModel with 2 parameters.
 */
class EditjumpViewModelFactory (
    private val dataSource: JumpingDao,
    private val application: Application,
    private val jumpId:Int
    ):ViewModelProvider.Factory
    {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditjumpViewModel::class.java)) {
                return EditjumpViewModel(dataSource, application,jumpId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
