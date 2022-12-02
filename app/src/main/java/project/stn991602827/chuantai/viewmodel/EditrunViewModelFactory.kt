package project.stn991602827.chuantai.viewmodel


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.stn991602827.chuantai.data.RunDao


class EditrunViewModelFactory (
    private val dataSource: RunDao,
    private val application: Application
    ):ViewModelProvider.Factory
    {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditrunViewModel::class.java)) {
                return EditrunViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
