package project.stn991602827.chuantai.manageworkout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.stn991602827.chuantai.R
import project.stn991602827.chuantai.viewmodel.ManagejumpingFragmentViewModel

class ManagejumpingFragment : Fragment() {

    companion object {
        fun newInstance() = ManagejumpingFragment()
    }

    private lateinit var viewModel: ManagejumpingFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_managejumping, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManagejumpingFragmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}