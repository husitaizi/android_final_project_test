package project.stn991602827stn991579365.chuantaiAndJunxiu.manageworkout

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class models a displaying screen of Jumps with buttons leads to actions on the data.
*/
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
import project.stn991602827stn991579365.chuantaiAndJunxiu.R
import project.stn991602827stn991579365.chuantaiAndJunxiu.adapter.JumpAdapter
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.WorkoutDatabase
import project.stn991602827stn991579365.chuantaiAndJunxiu.databinding.FragmentManagejumpingBinding
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.ManagejumpingFragmentViewModel
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.ManagejumpingViewModelFactory

/**
 * The class models a screen of Jumping information with buttons leads to actions on the data.
 */
class ManagejumpingFragment : Fragment() {

    // Define variables to be initialized as needed.
    private lateinit var mDB: WorkoutDatabase
    private lateinit var binding: FragmentManagejumpingBinding
    private lateinit var viewModel: ManagejumpingFragmentViewModel

    companion object {
        fun newInstance() = ManagejumpingFragment()
    }

    /**
     * Define the tasks to be done on the creation of the view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Initialize a data binding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_managejumping
        ,container,false)

        // Initialize a database.
        mDB = WorkoutDatabase.getInstance(this.requireContext())

        // Defines parameters for the viewmodel Factory.
        val dataSource=mDB.jumpingDao()
        val application= requireNotNull(this.activity).application

        // Initialize the viewModel Factory.
        val viewModelFactory = ManagejumpingViewModelFactory(dataSource, application)

        // Initialize the viewModel.
        viewModel = ViewModelProvider(this, viewModelFactory
        )[ManagejumpingFragmentViewModel::class.java]

        // Set an OnClickListener on btnEditjump if "plus" icon is clicked.
        binding.btnEditjump.setOnClickListener {
        it.findNavController().navigate(R.id.action_fragmentManagejumping_to_editjumpFragment)
        }

        // Observe the jumps LiveData to refresh the UI info as to changes immediately.
        viewModel.jumps.observe(viewLifecycleOwner, Observer {
            binding.recyclerViewJump.adapter= JumpAdapter(it,viewModel)
        })

        // Observe the total LiveData to refresh the UI info as to changes immediately.
        viewModel.total.observe(viewLifecycleOwner, Observer {
            binding.amount.text=it.toString()
        })

        // Set up the layoutManager on the viewModel.
        binding.recyclerViewJump.layoutManager = LinearLayoutManager(this.context)

        // Set the recycler items size as fixed.
        binding.recyclerViewJump.setHasFixedSize(true)


       return binding.root
    }



}