package project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class defines a viewModel factory.
*/
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.DietDao

/**
 * The class is a factory used to initialize a viewModel with 2 parameters.
 */
class ManagedietViewModelFactory(
    private val dataSource:DietDao,
    private val application: Application
):ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ManagedietFragmentViewModel::class.java)){
            return ManagedietFragmentViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}