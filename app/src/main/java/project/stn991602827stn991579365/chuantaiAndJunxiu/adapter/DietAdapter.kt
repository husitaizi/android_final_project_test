package project.stn991602827stn991579365.chuantaiAndJunxiu.adapter

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class is for recyclerview displaying of Diet segment.
* */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_diet.view.*
import project.stn991602827stn991579365.chuantaiAndJunxiu.R
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Diet
import project.stn991602827stn991579365.chuantaiAndJunxiu.manageworkout.ManagedietFragmentDirections
import project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel.ManagedietFragmentViewModel

/**
 * DietAdapter takes 2 parameters, one is a List of Diet objects retrieved from Database, the other
 * is a ManagedietFragmentViewModel which provides the methods to operate on the Database. The class
 * implements a RecyclerView Adapter.
 * */
class DietAdapter(
    private val dietList: List<Diet>,
    private val viewModel: ManagedietFragmentViewModel,
) :
    RecyclerView.Adapter<DietAdapter.DietViewHolder>() {

    /**
     * A class to define a viewholder for Diet objects.
     * */
    class DietViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTime: TextView = itemView.date_time
        val kj: TextView = itemView.kj
        val btn_update: Button = itemView.btn_update
        val btn_delete: Button = itemView.btn_delete

    }

    /**
     * A override method to inflate the listOfDiet card view.
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {
        // inflate the cardView
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_diet, parent, false)
        return DietViewHolder(itemView)
    }

    /**
     * A method to count the size of the list of Diets.
     * */
    override fun getItemCount() = dietList.size


    /**
     * A method to bind each item in cardview of Diets to the viewholder.
     * */
    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        // Get the current item of Diet.
        val currentItem = dietList[position]

        // Formulate the info to display in the text views.
        holder.dateTime.text = currentItem.date.toString() + "\n" + currentItem.time.toString()
        holder.kj.text = currentItem.food + "  " + currentItem.calories.toString() + " \n KJ"

        // If update button is clicked, go to edit jump view.
        holder.btn_update.setOnClickListener {
            // Formulate the information of the chose Diet to be passed to the destination fragment.
            var dietInfo = """
                currentItem.date.toString() + currentItem.time.toString()
            +currentItem.calories.toString()"""

            // Navigate to the EditdietFragment with arguments : isUpdated, dietId,dietInfo
            // and food information used to rebuild the toBeUpdated Diet in the Editdiet view.
            it.findNavController().navigate(
                ManagedietFragmentDirections.actionManagedietFragment3ToEditdietFragment(
                    true,
                    currentItem.id,
                    dietInfo,
                    currentItem.food))
        }

        // Delete the record Diet in the database to be committed in the background.
        holder.btn_delete.setOnClickListener {
            viewModel.delete(currentItem)
        }
    }
}
