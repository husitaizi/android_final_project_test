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
import project.stn991602827.chuantai.data.Jumping
import project.stn991602827.chuantai.data.WorkoutDatabase
import project.stn991602827.chuantai.databinding.FragmentEditjumpBinding
import project.stn991602827.chuantai.viewmodel.EditjumpViewModel
import project.stn991602827.chuantai.viewmodel.EditjumpViewModelFactory
import java.util.*

class EditjumpFragment : Fragment(), DatePickerDialog.OnDateSetListener,
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

    var count = 100

    private var dateString: String = "0"
    private var timeString: String = "0"
    private var isUpdate: Boolean = false
    private var jumpToBeUpdated: Jumping? = null
    private var strArgs: String = ""

    companion object {
        fun newInstance() = EditjumpFragment()
    }

    private lateinit var binding: FragmentEditjumpBinding
    private lateinit var viewModel: EditjumpViewModel
    private lateinit var converter: Converters

    val args: EditjumpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // initialize the database
        mDb = WorkoutDatabase.getInstance(this.requireContext())
        val dataSource = mDb.jumpingDao()
        val application = requireNotNull(this.activity).application
        // get passed arguments
        isUpdate = args.update
        val jumpId = args.jumpID
        strArgs = args.jump.filter { it -> it.isDigit() }

        val viewModelFactory = EditjumpViewModelFactory(dataSource, application, jumpId)

        viewModel = ViewModelProvider(this, viewModelFactory)[EditjumpViewModel::class.java]

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_editjump, container, false)


        when (isUpdate) {
            false -> {
                binding.btnPlanAJump.text = "Add A Jump"
            }
            true -> {
                binding.btnPlanAJump.text = "Update The Jump"
            }
            else -> "Add A Jump"
        }


        // initialize the converter
        converter = Converters()

        binding.btnPlanAJump.setOnClickListener {
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

                        // isUpdate is a default true, update the current Jump
                        // TODO if not chosen, use the old value
                        viewModel.updateAJump(Jumping(jumpId,
                            converter.stringToDate(dateString),
                            converter.stringToTime(timeString),
                            count))
                    }
                    false -> {
                        // isUpdate update is false, add a new Jump
                        // TODO if no value is chosen, use the current day and time
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

        binding.inputCountNumber.setOnValueChangedListener { picker, oldVal, newVal ->

            count = countArray[newVal].toInt()
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
        count = newVal
    }


}