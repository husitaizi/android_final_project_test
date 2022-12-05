package project.stn991602827stn991579365.chuantaiAndJunxiu.manageworkout

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class controls the Add and Update of a Diet.
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
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Diet
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.WorkoutDatabase
import project.stn991602827stn991579365.chuantaiAndJunxiu.databinding.FragmentEditdietBinding
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.EditdietViewModel
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.EditdietViewModelFactory
import java.util.*

/**
 * Besides Fragment(), the class also implements DatePickerDialog,TimePickerDialog and NumberPicker
 * 's related listeners and override their methods.
 */
class EditdietFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, NumberPicker.OnValueChangeListener {

    // Define variables to be initalized when needed.
    private lateinit var mDb: WorkoutDatabase
    private lateinit var binding: FragmentEditdietBinding
    private lateinit var viewModel: EditdietViewModel
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

    // Define a calories with default value 100.
    private var calories = 100

    // Define 2 string variable to formulate the info to be displayed on the UI.
    private var dateString: String = "0"
    private var timeString: String = "0"

    // Define variables for the Navigation passed arguments.
    private var isUpdate: Boolean = false
    private var strArgs: String = ""
    private var dietId: Int = 0
    private var food: String = ""

    // Define the arguments of the fragment.
    private val args: EditdietFragmentArgs by navArgs()

    /**
     * New instance.
     */
    companion object {
        fun newInstance() = EditdietFragment()
    }

    /**
     * Define Tasks to be done when the view is created.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Initialize the database
        mDb = WorkoutDatabase.getInstance(this.requireContext())

        // Define the parameters used by the viewmodel Factory.
        val dataSource = mDb.dietDao()
        val application = requireNotNull(this.activity).application

        // Initialize the viewModelFactory.
        val viewModelFactory = EditdietViewModelFactory(dataSource, application)

        // Initialize a viewModel for the Fragment.
        viewModel = ViewModelProvider(this, viewModelFactory)[EditdietViewModel::class.java]

        // Get the Navigation passed arguments.
        isUpdate = args.isUpdate
        dietId = args.dietID

        // Remove the non-digit part for the Converter.
        strArgs = args.strArgs.filter { it -> it.isDigit() }
        food = args.food
        // Define data binding.
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_editdiet, container, false)

        // Initialize the converter.
        converter = Converters()

        // Call the fun to set up what info the PlanADiet button to display.
        setPlanADietButtonDisplayInfo()

        // Call the method to setup onClickListener on PlanADiet button.
        btnPlanADietOnclickListener()

        // Call the fun to set up NumberPicker display and onValueChangeListener
        setUpNumberPicker()

        // Call the fun to set up Date and Time picker.
        pickDate(isUpdate)

        // Trigger the onValueChangeListener on the Numberpicker to get an input.
        pickNumber()


        return binding.root
    } // end of onCreate()

    /**
     * Set onclickListener on the Plan A Diet button.
     */
    private fun btnPlanADietOnclickListener() {
        binding.btnPlanADiet.setOnClickListener {
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
                        viewModel.updateADiet(Diet(dietId, food,
                            converter.getCaloriesFromArgs(strArgs),
                            converter.stringToDate(dateString),
                            converter.stringToTime(timeString)))
                    }
                    false -> {
                        // If it is an update, then call the viewModel fun to update the Run.
                        // Need to get the ID of run to be updated from arguments.
                        viewModel.addADiet(Diet(0,
                            binding.inputFoodText.text.toString(),
                            calories,
                            converter.stringToDate(dateString),
                            converter.stringToTime(timeString)))
                    }
                    else -> ""
                }
                // TODO the argument here is useless so far if possible,remove it later
                it.findNavController()
                    .navigate(R.id.action_editdietFragment_to_managedietFragment3)
            }
        }
    }

    /**
     * Change the PlanADiet button info as to the passed message.
     * If isUpdate is false, then it is an "Add" operation, the button is set to "Add A Diet".
     * If isUpdate is true, then it is an "Update" operation, the button is set to
     * "Update The Diet".
     */
    private fun setPlanADietButtonDisplayInfo() {
        when (isUpdate) {
            false -> {
                binding.btnPlanADiet.text = "Add A Diet"
            }
            true -> {
                binding.btnPlanADiet.text = "Update The Diet"
            }
            else -> "Add A Diet"
        }
    }

    /**
     * Set up the NumberPicker.
     */
    private fun setUpNumberPicker() {
        var countArray: Array<String> =
            arrayOf("100", "200", "300", "400", "500", "600", "700", "800", "900", "1000")
        binding.inputCaloriesNumber.minValue = 0
        binding.inputCaloriesNumber.maxValue = countArray.size - 1
        binding.inputCaloriesNumber.displayedValues = countArray
        if (isUpdate) {
            binding.inputCaloriesNumber.value = converter.getCountFromArgs(strArgs)
        } else {
            binding.inputCaloriesNumber.value = 100
        }
    }

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
                // If it is a Add operation, set the date and time to current date and time instead.
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

    private fun pickDate(isUpdate: Boolean) {
        binding.dateTimePicker.setOnClickListener {
            getDateTimeCalendar(isUpdate)
            this.context?.let { it1 -> DatePickerDialog(it1, this, year, month, day).show() }
        }
    }

    private fun pickNumber() {
        var countArray: Array<String> =
            arrayOf("100", "200", "300", "400", "500", "600", "700", "800", "900", "1000")
        // Set listener on the Number picker.
        binding.inputCaloriesNumber.setOnValueChangedListener { _, _, newVal ->
            calories = countArray[newVal].toInt()
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
        calories = newVal
    }
}