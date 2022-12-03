package project.stn991602827.chuantai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import project.stn991602827.chuantai.databinding.FragmentWorkoutBinding


/**
 * A simple [Fragment] subclass.
 * Use the [WorkoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkoutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // define databinding
        val binding:FragmentWorkoutBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_workout,container,false)

        // TODO set clicklistener on imageviews
        binding.runningImage.setOnClickListener{
            it.findNavController().navigate(R.id.action_workoutFragment2_to_managerunFragment2)
        }
        binding.ropeJumpingImage.setOnClickListener{
            it.findNavController().navigate(R.id.action_workoutFragment2_to_fragmentManagejumping)
        }
        binding.dietingImage.setOnClickListener{
            it.findNavController().navigate(R.id.action_workoutFragment2_to_managedietFragment3)
        }


        // Inflate the layout for this fragment
        return binding.root
      //  return inflater.inflate(R.layout.fragment_workout, container, false)
    }


}