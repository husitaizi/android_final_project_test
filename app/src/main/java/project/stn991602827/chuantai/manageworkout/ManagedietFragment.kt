package project.stn991602827.chuantai.manageworkout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.stn991602827.chuantai.R
import project.stn991602827.chuantai.viewmodel.ManagedietViewModel

class ManagedietFragment : Fragment() {

    companion object {
        fun newInstance() = ManagedietFragment()
    }

    private lateinit var viewModel: ManagedietViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_managediet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManagedietViewModel::class.java)
        // TODO: Use the ViewModel
    }

}