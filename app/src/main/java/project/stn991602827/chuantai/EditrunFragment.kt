package project.stn991602827.chuantai

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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
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

    private var dateString: String = ""
    private var timeString: String = ""

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
        val actionType = args.addOrUpdate
        val runId=args.runningID

        // used before binding initialization can not use binding use findviewbyid
       // var btn_text = this.view?.findViewById<Button>(R.id.btn_planARun)?.text
        // decide the "planARun"button content
        // to save or update a Run
        // TODO may have to be done before inflation


        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_editrun, container, false)


        when (actionType) {
            true -> {
                binding.btnPlanARun.text = "Add A Run"
            }
            false -> {
                binding.btnPlanARun.text = "Update The Run"
            }
            else -> "Add A Run"
        }

        // set up viewmodel
        binding.viewModel = viewModel

        // initialize the converter
        converter = Converters()






        binding.btnPlanARun.setOnClickListener {
            when (actionType) {
                true -> {
                    // actionType is a default true, add a new Run
                    viewModel.addARun(Run(0, converter.stringToDate(dateString), distance))
                }
                false -> {
                    // actionType is false, update the current Run
                    // need to get the ID of run to be updated from ManagerunFragment Adapter viewholder
                    viewModel.updateARun(Run(runId,converter.stringToDate(dateString),distance))
                }
                else -> ""
            }
            // pass an argument, change addOrUpdate to true to command the ManagerunViewModel to do refresh() on liveData
            it.findNavController().navigate(EditrunFragmentDirections.actionEditrunFragmentToManagerunFragment2(true))
        }

        pickDate()
        pickNumber()


        return binding.root
    } // end of onCreate()

    private fun getDateTimeCalendar() {
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        binding.dateTimePicker.setOnClickListener {
            getDateTimeCalendar()
            this.context?.let { it1 -> DatePickerDialog(it1, this, year, month, day).show() }
        }
    }
private fun pickNumber(){
    binding.inputDistanceNumber.setOnValueChangedListener{picker, oldVal, newVal ->

    distance=newVal

    }
}


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
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
      //  distance = newVal
    }


}