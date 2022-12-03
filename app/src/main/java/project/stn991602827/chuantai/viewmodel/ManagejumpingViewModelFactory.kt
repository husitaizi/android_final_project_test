package project.stn991602827.chuantai.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.stn991602827.chuantai.data.JumpingDao

class ManagejumpingViewModelFactory(
    private val dataSource:JumpingDao,
    private val application: Application
):ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ManagejumpingFragmentViewModel::class.java)){
            return ManagejumpingFragmentViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}