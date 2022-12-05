package project.stn991602827stn991579365.chuantaiAndJunxiu.manageworkout

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class controls the Add and Update of a Run.
*/
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import project.stn991602827stn991579365.chuantaiAndJunxiu.R
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Converters
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Run
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.WorkoutDatabase
import project.stn991602827stn991579365.chuantaiAndJunxiu.databinding.FragmentEditrunBinding
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.EditrunViewModel
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.EditrunViewModelFactory
import java.util.*

/**
 * Besides Fragment(), the class also implements DatePickerDialog,TimePickerDialog and NumberPicker
 * 's related listeners and override their methods.
 */
class EditrunFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, NumberPicker.OnValueChangeListener {

    // Define variables to be initialized when needed.
    private lateinit var mDb: WorkoutDatabase
    private lateinit var binding: FragmentEditrunBinding
    private lateinit var viewModel: EditrunViewModel
    private lateinit var converter: Converters

    // Define variables of Calendar.
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    // Define a run unit of with default value 5 km.
    var distance = 5

    // Define 2 string variable to formulate the info to be displayed on the UI.
    private var dateString: String = "0"
    private var timeString: String = "0"

    // Define variables for the Navigation passed arguments.
    private var strArgs: String = ""
    private var isUpdate: Boolean = false
    private var runId: Int = 0

    // Define the arguments of the fragment.
    private val args: EditrunFragmentArgs by navArgs()

    companion object {
        fun newInstance() = EditrunFragment()
    }

    /**
     * Define the tasks to be done on the creation of the view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Initialize the database.
        mDb = WorkoutDatabase.getInstance(this.requireContext())

        // Define the parameters used by the viewmodel Factory.
        val dataSource = mDb.runDao()
        val application = requireNotNull(this.activity).application

        // Initialize the viewModelFactory.
        val viewModelFactory = EditrunViewModelFactory(dataSource, application)

        // Initialize a viewModel for the Fragment.
        viewModel = ViewModelProvider(this, viewModelFactory)[EditrunViewModel::class.java]

        // Get the Navigation passed arguments.
        isUpdate = args.isUpdate
        runId = args.runningID
        strArgs = args.run.filter { it -> it.isDigit() }

        // Define data binding.
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_editrun, container, false)

        // initialize the converter
        converter = Converters()

        // Call the fun to set up what info the PlanAJump button to display.
        setPlanARunButtonDisplayInfo()


        // Call the fun to set up what info the PlanAJump button to display.
        btnPlanARunOnclickListener()

        // Call the fun to pick date and time.
        pickDate(isUpdate)

        // Call the fun to pick a Number.
        pickNumber(isUpdate)

        return binding.root
    } // end of onCreate()

    /**
     * Set up the info for the Plan A Run button to display as to Add or Update operation.
     */
    private fun setPlanARunButtonDisplayInfo(){
        when (isUpdate) {
            false -> {
                binding.btnPlanARun.text = "Add A Run"
            }
            true -> {
                binding.btnPlanARun.text = "Update The Run"
            }
            else -> "Add A Run"
        }
    }

    /**
     * Set onclickListener on the Plan A Run button.
     */
    private fun btnPlanARunOnclickListener(){
        binding.btnPlanARun.setOnClickListener {
            // Check if the date time is chosen, if not prompt. Could leads to exception with no
            // default value set.
            var dateStringToInt = dateString.filter { it -> it.isDigit() }.toInt()
            var timeStringToInt = timeString.filter { it -> it.isDigit() }.toInt()
            if (dateStringToInt == 0 || timeStringToInt == 0) {
                Toast.makeText(this.requireContext(),
                    "Choose date and time first!",
                    Toast.LENGTH_LONG).show()
            } else {
                when (isUpdate) {
                    false -> {
                        // If it is an Add, then call the viewModel fun to add the Run.
                        viewModel.addARun(Run(0,
                            converter.stringToDate(dateString),
                            converter.stringToTime(timeString),
                            distance))
                    }
                    true -> {
                        // If it is an update, then call the viewModel fun to update the Run.
                        // Need to get the ID of run to be updated from arguments.
                        viewModel.updateARun(Run(runId,
                            converter.stringToDate(dateString),
                            converter.stringToTime(timeString),
                            distance))
                    }
                    else -> ""
                }
                // TODO the argument here is useless so far if possible,remove it later
                it.findNavController()
                    .navigate(EditrunFragmentDirections.actionEditrunFragmentToManagerunFragment2(
                        true))
            }
        }
    }
    private fun getDateTimeCalendar(isUpdate: Boolean) {
        when (isUpdate) {
            false -> {
                val cal: Calendar = Calendar.getInstance()
                day = cal.get(Calendar.DAY_OF_MONTH)
                month = cal.get(Calendar.MONTH)
                year = cal.get(Calendar.YEAR)
                hour = cal.get(Calendar.HOUR)
                minute = cal.get(Calendar.MINUTE)
            }
            true -> {
                day = converter.getDayFromArgs(strArgs)
                month = converter.getMonthFromArgs(strArgs)
                year = converter.getYearFromArgs(strArgs)
                hour = converter.getHourFromArgs(strArgs)
                minute = converter.getMinuteFromArgs(strArgs)
            }
        }

    }

    private fun pickDate(isUpdate: Boolean) {
        binding.dateTimePicker.setOnClickListener {
            getDateTimeCalendar(isUpdate)
            this.context?.let { it1 -> DatePickerDialog(it1, this, year, month, day).show() }
        }
    }

    private fun pickNumber(isUpdate: Boolean) {
        if (isUpdate) {
            binding.inputDistanceNumber.value = converter.getDistanceFromArgs(strArgs)
        } else {

        }
        binding.inputDistanceNumber.setOnValueChangedListener { picker, oldVal, newVal ->

            distance = newVal

        }
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        getDateTimeCalendar(isUpdate)
        TimePickerDialog(this.context, this, hour, minute, true).show()
        dateString = "$savedYear-$savedMonth-$savedDay"
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        timeString = "$savedHour:$savedMinute:00"

        binding.dateTimeChosen.text =
            "$savedDay-$savedMonth-$savedYear\n Hour: $savedHour Minute: $savedMinute"
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        TODO("Not yet implemented")
        //  calories = newVal
    }


}