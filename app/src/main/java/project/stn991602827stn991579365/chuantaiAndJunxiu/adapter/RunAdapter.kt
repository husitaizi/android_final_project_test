package project.stn991602827stn991579365.chuantaiAndJunxiu.adapter

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class is for recyclerview displaying of Run segment.
* */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_run.view.*
import project.stn991602827stn991579365.chuantaiAndJunxiu.R
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Run
import project.stn991602827stn991579365.chuantaiAndJunxiu.manageworkout.ManagerunFragmentDirections
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.ManagerunViewModel

/**
 * RunAdapter takes 2 parameters, one is a List of Run objects retrieved from Database, the other
 * is a ManagerunFragmentViewModel which provides the methods to operate on the Database. The class
 * implements a RecyclerView Adapter.
 * */
class RunAdapter(
    private val runList: List<Run>,
    private val viewModel: ManagerunViewModel,
) :
    RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    /**
     * A class to define a viewholder for Run objects.
     */
    class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTime: TextView = itemView.date_time
        val distance: TextView = itemView.distance
        val btn_update: Button = itemView.btn_update
        val btn_delete: Button = itemView.btn_delete

    }

    /**
     * A override method to inflate the list Of Run card view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        // inflate the cardView
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_run, parent, false)
        return RunViewHolder(itemView)
    }

    /**
     * A method to count the size of the list of Runs.
     */
    override fun getItemCount() = runList.size

    /**
     * A method to bind each item in cardview of Runs to the viewholder.
     */
    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        // Get the current item of Jumpings.
        val currentItem = runList[position]

        // Formulate the info to display in the text views.
        holder.dateTime.text = currentItem.date.toString() + "\n" + currentItem.time.toString()
        holder.distance.text = currentItem.distance.toString() + " \nKM"

        // If update button is clicked, go to editrun view.
        holder.btn_update.setOnClickListener {
            // Formulate the information of the chose Jumping to be passed to the destination.
            var run ="""
                currentItem.date.toString() + currentItem.time.toString() + 
                currentItem.distance.toString()"""

            // Navigate to the EditrunFragment with arguments : runID isUpdate and the current
            // Run information used to update the toBeUpdated Run in the Editrun view.
            it.findNavController().navigate(
                ManagerunFragmentDirections.actionManagerunFragment2ToEditrunFragment(
                    currentItem.id,
                    true,
                    run))
        }

        // Delete the record of Run in the database to be committed in the background.
        holder.btn_delete.setOnClickListener {
            viewModel.delete(currentItem)
        }
    }
}
