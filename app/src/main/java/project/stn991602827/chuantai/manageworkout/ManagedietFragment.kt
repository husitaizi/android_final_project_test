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
import project.stn991602827.chuantai.adapter.DietAdapter
import project.stn991602827.chuantai.data.WorkoutDatabase
import project.stn991602827.chuantai.databinding.FragmentManagedietBinding
import project.stn991602827.chuantai.viewmodel.ManagedietFragmentViewModel
import project.stn991602827.chuantai.viewmodel.ManagedietViewModelFactory

class ManagedietFragment : Fragment() {
    private lateinit var mDB: WorkoutDatabase

    companion object {
        fun newInstance() = ManagedietFragment()
    }
    private lateinit var binding:FragmentManagedietBinding
    private lateinit var viewModel: ManagedietFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_managediet
            ,container,false)

        mDB = WorkoutDatabase.getInstance(this.requireContext())

        val dataSource=mDB.dietDao()
        val application= requireNotNull(this.activity).application

        val viewModelFactory = ManagedietViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory
        )[ManagedietFragmentViewModel::class.java]

        binding.btnEditDiet.setOnClickListener {
            it.findNavController().navigate(R.id.action_managedietFragment3_to_editdietFragment)
        }

        viewModel.diets.observe(viewLifecycleOwner, Observer {
            binding.recyclerViewDiet.adapter= DietAdapter(it,viewModel)
        })

        viewModel.total.observe(viewLifecycleOwner, Observer {
            binding.amount.text=it.toString()
        })


        binding.recyclerViewDiet.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewDiet.setHasFixedSize(true)






        return binding.root
    }


}