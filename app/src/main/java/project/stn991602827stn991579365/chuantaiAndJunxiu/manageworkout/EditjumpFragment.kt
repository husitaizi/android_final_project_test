package project.stn991602827stn991579365.chuantaiAndJunxiu.manageworkout


/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class controls the Add and Update of a Jump.
*/
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.shawnlin.numberpicker.NumberPicker
import project.stn991602827stn991579365.chuantaiAndJunxiu.R
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Converters
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Jumping
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.WorkoutDatabase
import project.stn991602827stn991579365.chuantaiAndJunxiu.databinding.FragmentEditjumpBinding
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.EditjumpViewModel
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.EditjumpViewModelFactory
import java.util.*

/**
 * Besides Fragment(), the class also implements DatePickerDialog,TimePickerDialog and NumberPicker
 * 's related listeners and override their methods.
 */
class EditjumpFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, NumberPicker.OnValueChangeListener {

    // Define variables to be initialized when needed.
    private lateinit var mDb: WorkoutDatabase
    private lateinit var binding: FragmentEditjumpBinding
    private lateinit var viewModel: EditjumpViewModel
    private lateinit var converter: Converters

    // Define variables of Calendar.
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    // Define variables for the Date Time picker.
    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    // Define a count unit of rope-jumping with default value 100.
    var count = 100

    // Define 2 string variable to formulate the info to be displayed on the UI.
    private var dateString: String = "0"
    private var timeString: String = "0"

    // Define variables for the Navigation passed arguments.
    private var isUpdate: Boolean = false
    private var strArgs: String = ""
    private var jumpId: Int = 0

    // Define the arguments of the fragment.
    private val args: EditjumpFragmentArgs by navArgs()

    /**
     * New instance.
     */
    companion object {
        fun newInstance() = EditjumpFragment()
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
        val dataSource = mDb.jumpingDao()
        val application = requireNotNull(this.activity).application

        // Initialize the viewModelFactory.
        val viewModelFactory = EditjumpViewModelFactory(dataSource, application, jumpId)

        // Initialize a viewModel for the Fragment.
        viewModel = ViewModelProvider(this, viewModelFactory)[EditjumpViewModel::class.java]

        // Get the Navigation passed arguments.
        isUpdate = args.update
        jumpId = args.jumpID
        // Remove the non-digit part for the Converter.
        strArgs = args.jump.filter { it -> it.isDigit() }

        // Define data binding.
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_editjump, container, false)

        // initialize the converter
        converter = Converters()

        // Call the fun to set up what info the PlanAJump button to display.
        setPlanAJumpButtonDisplayInfo()

        // Call the fun to set up what info the PlanAJump button to display.
        btnPlanAjumpOnclickListener()

        // Call the fun to set up NumberPicker display and onValueChangeListener
        setUpNumberPicker()

        // Call the fun to pick up a date.
        pickDate(isUpdate)

        // Call the fun to pick up a Number.
        pickNumber()

        return binding.root
    } // end of onCreate()

    /**
     * Change the PlanAJump button info as to the passed message.
     * If isUpdate is false, then it is an "Add" operation, the button is set to "Add A Jump".
     * If isUpdate is true, then it is an "Update" operation, the button is set to
     * "Update The Jump".
     */
    private fun setPlanAJumpButtonDisplayInfo() {
        when (isUpdate) {
            false -> {
                binding.btnPlanAJump.text = "Add A Jump"
            }
            true -> {
                binding.btnPlanAJump.text = "Update The Jump"
            }
            else -> "Add A Jump"
        }
    }

    /**
     * Set onclickListener on the Plan A Jump button.
     */
    private fun btnPlanAjumpOnclickListener() {
        binding.btnPlanAJump.setOnClickListener {
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
                    true -> {
                        // If it is an Add, then call the viewModel fun to add the Run.
                        viewModel.updateAJump(Jumping(jumpId,
                            converter.stringToDate(dateString),
                            converter.stringToTime(timeString),
                            count))
                    }
                    false -> {
                        // If it is an update, then call the viewModel fun to update the Run.
                        // Need to get the ID of run to be updated from arguments.
                        viewModel.addAJump(Jumping(0,
                            converter.stringToDate(dateString),
                            converter.stringToTime(timeString),
                            count))
                    }
                    else -> ""
                }
                // TODO the argument here is useless so far if possible,remove it later
                it.findNavController()
                    .navigate(R.id.action_editjumpFragment_to_fragmentManagejumping)
            }
        }
    }

    /**
     * Set up the NumberPicker.
     */
    private fun setUpNumberPicker() {
        var countArray: Array<String> =
            arrayOf("100", "200", "300", "400", "500", "600", "700", "800", "900", "1000")
        binding.inputCountNumber.minValue = 0
        binding.inputCountNumber.maxValue = countArray.size - 1
        binding.inputCountNumber.displayedValues = countArray
        if (isUpdate) {
            binding.inputCountNumber.value = converter.getCountFromArgs(strArgs)
        } else {
            binding.inputCountNumber.value = 100
        }
    }

    /**
     * Get the date and time to be added/updated.
     */
    private fun getDateTimeCalendar(isUpdate: Boolean) {
        when (isUpdate) {
            true -> {
                // If it is the Update operation, set the date and time as to the arguments passed.
                day = converter.getDayFromArgs(strArgs)
                month = converter.getMonthFromArgs(strArgs)
                year = converter.getYearFromArgs(strArgs)
                hour = converter.getHourFromArgs(strArgs)
                minute = converter.getMinuteFromArgs(strArgs)
            }
            false -> {
                // If it is the Add operation, get the current date and time instead.
                val cal: Calendar = Calendar.getInstance()
                day = cal.get(Calendar.DAY_OF_MONTH)
                month = cal.get(Calendar.MONTH)
                year = cal.get(Calendar.YEAR)
                hour = cal.get(Calendar.HOUR)
                minute = cal.get(Calendar.MINUTE)
            }
            else -> "Add A Jump"
        }

    }

    /**
     * Set up OnClickListener.
     */
    private fun pickDate(isUpdate: Boolean) {
        binding.dateTimePicker.setOnClickListener {
            // Get the date time info.
            getDateTimeCalendar(isUpdate)

            // Set the chosen date.
            this.context?.let { it1 -> DatePickerDialog(it1, this, year, month, day).show() }
        }
    }

    /**
     * Set up setOnValueChangedListener on the NumberPicker.
     */
    private fun pickNumber() {
        var countArray: Array<String> =
            arrayOf("100", "200", "300", "400", "500", "600", "700", "800", "900", "1000")
        binding.inputCountNumber.setOnValueChangedListener { picker, oldVal, newVal ->

            count = countArray[newVal].toInt()
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
        count = newVal
    }


}