package project.stn991602827.chuantai.manageworkout

//import com.shawnlin.numberpicker.NumberPicker
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
import project.stn991602827.chuantai.R
import project.stn991602827.chuantai.data.Converters
import project.stn991602827.chuantai.data.Run
import project.stn991602827.chuantai.data.WorkoutDatabase
import project.stn991602827.chuantai.databinding.FragmentEditrunBinding
import project.stn991602827.chuantai.viewmodel.EditrunViewModel
import project.stn991602827.chuantai.viewmodel.EditrunViewModelFactory
import java.util.*

class EditrunFragment : Fragment(), DatePickerDialog.OnDateSetListener,
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

    var distance = 5

    private var dateString: String = "0"
    private var timeString: String = "0"
    private var strArgs: String = ""
    private var isUpdate: Boolean = false

    companion object {
        fun newInstance() = EditrunFragment()
    }

    private lateinit var binding: FragmentEditrunBinding
    private lateinit var viewModel: EditrunViewModel
    private lateinit var converter: Converters

    val args: EditrunFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // initialize the database
        mDb = WorkoutDatabase.getInstance(this.requireContext())
        val dataSource = mDb.runDao()
        val application = requireNotNull(this.activity).application

        val viewModelFactory = EditrunViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory)[EditrunViewModel::class.java]

        // get passed arguments
        isUpdate = args.isUpdate
        val runId = args.runningID
        strArgs = args.run.filter { it -> it.isDigit() }

        // used before binding initialization can not use binding use findviewbyid
        // var btn_text = this.view?.findViewById<Button>(R.id.btn_planARun)?.text
        // decide the "planARun"button content
        // to save or update a Run
        // TODO may have to be done before inflation


        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_editrun, container, false)


        when (isUpdate) {
            false -> {
                binding.btnPlanARun.text = "Add A Run"
            }
            true -> {
                binding.btnPlanARun.text = "Update The Run"
            }
            else -> "Add A Run"
        }

        // set up viewmodel
        binding.viewModel = viewModel

        // initialize the converter
        converter = Converters()

        binding.btnPlanARun.setOnClickListener {
            // TODO check if the date time is chosen, if not prompt. leads to exception with no default value set
            var dateStringToInt = dateString.filter { it -> it.isDigit() }.toInt()
            var timeStringToInt = timeString.filter { it -> it.isDigit() }.toInt()
            if (dateStringToInt == 0 || timeStringToInt == 0) {
                Toast.makeText(this.requireContext(),"Choose date and time first!",Toast.LENGTH_LONG).show()
            } else {
                when (isUpdate) {
                    false -> {
                        // actionType is a default true, add a new Run
                        viewModel.addARun(Run(0,
                            converter.stringToDate(dateString),
                            converter.stringToTime(timeString),
                            distance))
                    }
                    true -> {
                        // actionType is false, update the current Run
                        // need to get the ID of run to be updated from ManagerunFragment Adapter viewholder
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

        pickDate(isUpdate)
        pickNumber(isUpdate)


        return binding.root
    } // end of onCreate()

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