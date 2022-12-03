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
import androidx.recyclerview.widget.LinearLayoutManager
import project.stn991602827.chuantai.R
import project.stn991602827.chuantai.adapter.JumpAdapter
import project.stn991602827.chuantai.data.WorkoutDatabase
import project.stn991602827.chuantai.databinding.FragmentManagejumpingBinding
import project.stn991602827.chuantai.viewmodel.ManagejumpingFragmentViewModel
import project.stn991602827.chuantai.viewmodel.ManagejumpingViewModelFactory

class ManagejumpingFragment : Fragment() {
    private lateinit var mDB: WorkoutDatabase

    companion object {
        fun newInstance() = ManagejumpingFragment()
    }

    private lateinit var binding: FragmentManagejumpingBinding
    private lateinit var viewModel: ManagejumpingFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_managejumping
        ,container,false)

        mDB = WorkoutDatabase.getInstance(this.requireContext())

        val dataSource=mDB.jumpingDao()
        val application= requireNotNull(this.activity).application

        val viewModelFactory = ManagejumpingViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory
        )[ManagejumpingFragmentViewModel::class.java]

        binding.btnEditjump.setOnClickListener {
        it.findNavController().navigate(R.id.action_fragmentManagejumping_to_editjumpFragment)
        }

        viewModel.jumps.observe(viewLifecycleOwner, Observer {
            binding.recyclerViewJump.adapter= JumpAdapter(it,viewModel)
        })

        viewModel.total.observe(viewLifecycleOwner, Observer {
            binding.amount.text=it.toString()
        })


        binding.recyclerViewJump.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewJump.setHasFixedSize(true)


       return binding.root
    }



}