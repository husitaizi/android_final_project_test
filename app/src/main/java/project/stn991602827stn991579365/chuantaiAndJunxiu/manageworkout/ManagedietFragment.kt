package project.stn991602827stn991579365.chuantaiAndJunxiu.manageworkout

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class models a displaying screen of Runs with buttons leads to actions on the data.
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
import project.stn991602827stn991579365.chuantaiAndJunxiu.adapter.DietAdapter
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.WorkoutDatabase
import project.stn991602827stn991579365.chuantaiAndJunxiu.databinding.FragmentManagedietBinding
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.ManagedietFragmentViewModel
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.ManagedietViewModelFactory

/**
 * The class models a screen of Run information with buttons leads to actions on the data.
 */
class ManagedietFragment : Fragment() {

    // Define variables to be initialized as needed.
    private lateinit var mDB: WorkoutDatabase
    private lateinit var binding: FragmentManagedietBinding
    private lateinit var viewModel: ManagedietFragmentViewModel

    companion object {
        fun newInstance() = ManagedietFragment()
    }

    /**
     * Define the tasks to be done on the creation of the view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Initialize a data binding.
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_managediet
            ,container,false)

        // Initialize a database.
        mDB = WorkoutDatabase.getInstance(this.requireContext())

        // Defines parameters for the viewmodel Factory.
        val dataSource=mDB.dietDao()
        val application= requireNotNull(this.activity).application

        // Initialize the viewModel Factory.
        val viewModelFactory = ManagedietViewModelFactory(dataSource, application)

        // Initialize the viewModel.
        viewModel = ViewModelProvider(this, viewModelFactory
        )[ManagedietFragmentViewModel::class.java]

        // Set an OnClickListener on btnEditDiet if "plus" icon is clicked.
        binding.btnEditDiet.setOnClickListener {
            it.findNavController().navigate(R.id.action_managedietFragment3_to_editdietFragment)
        }

        // Observe the diets LiveData to refresh the UI info as to changes immediately.
        viewModel.diets.observe(viewLifecycleOwner, Observer {
            binding.recyclerViewDiet.adapter= DietAdapter(it,viewModel)
        })

        // Observe the total LiveData to refresh the UI info as to changes immediately.
        viewModel.total.observe(viewLifecycleOwner, Observer {
            binding.amount.text=it.toString()
        })

        // Set up the layoutManager on the viewModel.
        binding.recyclerViewDiet.layoutManager = LinearLayoutManager(this.context)

        // Set the recycler items size as fixed.
        binding.recyclerViewDiet.setHasFixedSize(true)

        return binding.root
    }


}