package project.stn991602827.chuantai.viewmodel


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.stn991602827.chuantai.data.DietDao


class EditdietViewModelFactory (
    private val dataSource: DietDao,
    private val application: Application
    ):ViewModelProvider.Factory
    {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditdietViewModel::class.java)) {
                return EditdietViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
