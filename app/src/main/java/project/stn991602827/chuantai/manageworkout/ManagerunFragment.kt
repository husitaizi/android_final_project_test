package project.stn991602827.chuantai.manageworkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import project.stn991602827.chuantai.R
import project.stn991602827.chuantai.adapter.RunAdapter
import project.stn991602827.chuantai.data.WorkoutDatabase
import project.stn991602827.chuantai.databinding.FragmentManagerunBinding
import project.stn991602827.chuantai.viewmodel.ManagerunViewModel
import project.stn991602827.chuantai.viewmodel.ManagerunViewModelFactory

class ManagerunFragment : Fragment() {

    private lateinit var mDB: WorkoutDatabase

    companion object {
        fun newInstance() = ManagerunFragment()
    }

    private lateinit var binding: FragmentManagerunBinding
    private lateinit var viewModel: ManagerunViewModel
    private val args: ManagerunFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_managerun, container, false)


        // retrieve the argument passed or default
        val isChanged = args.addOrUpdate


        // initialize mDB
        mDB = WorkoutDatabase.getInstance(this.requireContext())

        // get a database instance
        val dataSource = mDB.runDao()     // IDE suggest requireContext()
        val application = requireNotNull(this.activity).application

        val viewModelFactory = ManagerunViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory
        )[ManagerunViewModel::class.java]


        // when addARun is clicked, go to edit run page, with NO argument "runningID" passed, the
        // default value is "0" so the button in edit_run page will show as "Add a run"
        binding.btnEditrun.setOnClickListener {
            it.findNavController().navigate(R.id.action_managerunFragment2_to_editrunFragment)
        }




/*        // initialize viewModel reference to the class example using ViewModelFactory with parameters
        viewModel = ViewModelProvider(this).get(ManagerunViewModel(mDB.runDao(), application)::class.java)*/

        // get run data from database/ done in viewModel Coroutine operation



        viewModel.runs.observe(viewLifecycleOwner, Observer {
            binding.recyclerViewRun.adapter = RunAdapter(it,viewModel)
               // viewModel.allRun.value?.let { RunAdapter(it, viewModel) }
        })

        viewModel.total.observe(viewLifecycleOwner, Observer {
            binding.amount.text=it.toString()
        })



        binding.recyclerViewRun.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewRun.setHasFixedSize(true)

        binding.viewModel = viewModel

        return binding.root
    }

    }


