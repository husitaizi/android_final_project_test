package project.stn991602827stn991579365.chuantaiAndJunxiu.adapter

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class is for recyclerview displaying of Rope-jumping segment.
* */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_jump.view.*
import project.stn991602827stn991579365.chuantaiAndJunxiu.R
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Jumping
import project.stn991602827stn991579365.chuantaiAndJunxiu.manageworkout.ManagejumpingFragmentDirections
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.ManagejumpingFragmentViewModel

/**
 * JumpAdapter takes 2 parameters, one is a List of Jumping objects retrieved from Database, the other
 * is a ManagejumpFragmentViewModel which provides the methods to operate on the Database. The class
 * implements a RecyclerView Adapter.
 */
class JumpAdapter(
    private val jumpList: List<Jumping>,
    private val viewModel: ManagejumpingFragmentViewModel,
) :
    RecyclerView.Adapter<JumpAdapter.JumpViewHolder>() {

    /**
     * A class to define a viewholder for Jumping objects.
     */
    class JumpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTime: TextView = itemView.date_time
        val count: TextView = itemView.rope
        val btn_update: Button = itemView.btn_update
        val btn_delete: Button = itemView.btn_delete

    }

    /**
     * A override method to inflate the listOfJumping card view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JumpViewHolder {
        // inflate the cardView
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_jump, parent, false)
        return JumpViewHolder(itemView)
    }

    /**
     * A method to count the size of the list of Jumpings.
     */
    override fun getItemCount() = jumpList.size

    /**
     * A method to bind each item in cardview of Jumpings to the viewholder.
     */
    override fun onBindViewHolder(holder: JumpViewHolder, position: Int) {
        // Get the current item of Jumpings.
        val currentItem = jumpList[position]

        // Formulate the info to display in the text views.
        holder.dateTime.text = currentItem.date.toString() + "\n" + currentItem.time.toString()
        holder.count.text = currentItem.count.toString() + " \n ROPE"

        // If update button is clicked, go to Editjump view.
        holder.btn_update.setOnClickListener {
            // Formulate the information of the chose Jumping to be passed to the destination.
            var jump =
                currentItem.date.toString() + currentItem.time.toString() +
                        currentItem.count.toString()

            // Navigate to the EditjumpFragment with arguments : update, jumpID and the current
            // jumping information used to update the toBeUpdated Jumping in the Editjump view.
            it.findNavController().navigate(
                ManagejumpingFragmentDirections.actionFragmentManagejumpingToEditjumpFragment(
                    true,
                    currentItem.id,
                    jump))
        }

        // Delete the record of Jumping in the database to be committed in the background.
        holder.btn_delete.setOnClickListener {
            viewModel.delete(currentItem)
        }
    }
}

