package project.stn991602827.chuantai.manageworkout

//import android.widget.NumberPicker
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
import project.stn991602827.chuantai.R
import project.stn991602827.chuantai.data.Converters
import project.stn991602827.chuantai.data.Diet
import project.stn991602827.chuantai.data.WorkoutDatabase
import project.stn991602827.chuantai.databinding.FragmentEditdietBinding
import project.stn991602827.chuantai.viewmodel.EditdietViewModel
import project.stn991602827.chuantai.viewmodel.EditdietViewModelFactory
import java.util.*

class EditdietFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, NumberPicker.OnValueChangeListener {
    private lateinit var mDb: WorkoutDatabase

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    var calories = 100

    private var dateString: String = "0"
    private var timeString: String = "0"
    private var isUpdate: Boolean = false
    private var strArgs: String = ""

    companion object {
        fun newInstance() = EditdietFragment()
    }

    private lateinit var binding: FragmentEditdietBinding
    private lateinit var viewModel: EditdietViewModel
    private lateinit var converter: Converters

    val args: EditdietFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // initialize the database
        mDb = WorkoutDatabase.getInstance(this.requireContext())
        val dataSource = mDb.dietDao()
        val application = requireNotNull(this.activity).application
        // get passed arguments
        isUpdate = args.isUpdate
        val dietId = args.dietID
        strArgs = args.strArgs.filter { it -> it.isDigit() }
        val food=args.food

        val viewModelFactory = EditdietViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory)[EditdietViewModel::class.java]

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_editdiet, container, false)


        when (isUpdate) {
            false -> {
                binding.btnPlanADiet.text = "Add A Diet"
            }
            true -> {
                binding.btnPlanADiet.text = "Update The Diet"
            }
            else -> "Add A Diet"
        }


        // initialize the converter
        converter = Converters()

        binding.btnPlanADiet.setOnClickListener {
            // TODO first, check if the date time is set or not, if not toast to ask for it to be done
            var dateStringToInt = dateString.filter { it -> it.isDigit() }.toInt()
            var timeStringToInt = timeString.filter { it -> it.isDigit() }.toInt()
            if (dateStringToInt == 0 || timeStringToInt == 0) {
                Toast.makeText(this.requireContext(),
                    "Choose date and time first!",
                    Toast.LENGTH_LONG).show()
            } else {
                when (isUpdate) {
                    true -> {

                        // isUpdate is a default true, update the current Diet
                        // TODO if not chosen, use the old value
                        viewModel.updateADiet(Diet(dietId,food,
                            converter.getCaloriesFromArgs(strArgs),
                            converter.stringToDate(dateString),
                            converter.stringToTime(timeString)))
                    }
                    false -> {
                        // isUpdate update is false, add a new Diet
                        // TODO if no value is chosen, use the current day and time
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

        binding.inputCaloriesNumber.setOnValueChangedListener { picker, oldVal, newVal ->

            calories = countArray[newVal].toInt()
        }

        pickDate(isUpdate)
        // pickNumber()


        return binding.root
    } // end of onCreate()

    private fun getDateTimeCalendar(isUpdate: Boolean) {
        when (isUpdate) {
            true -> {
/*                // if isUpdated, observe the toBeUpdated jump get it for setting of date time picker later
                viewModel.jumpToBeUpdated.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    jumpToBeUpdated= it.get(0)
                })*/
                // transfer the passed args of date, time and calories to Int


                // TODO let the date time picker show to be updated date and time
/*                var date= jumpToBeUpdated!!.date
                var time=jumpToBeUpdated!!.time*/

                day = converter.getDayFromArgs(strArgs)
                month = converter.getMonthFromArgs(strArgs)
                year = converter.getYearFromArgs(strArgs)
                hour = converter.getHourFromArgs(strArgs)
                minute = converter.getMinuteFromArgs(strArgs)
            }
            false -> {
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

/*    private fun pickNumber() {
    }*/


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
        calories = newVal
    }


}