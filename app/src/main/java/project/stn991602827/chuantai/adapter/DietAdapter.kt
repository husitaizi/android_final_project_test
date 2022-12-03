package project.stn991602827.chuantai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_diet.view.*
import project.stn991602827.chuantai.R
import project.stn991602827.chuantai.data.Diet
import project.stn991602827.chuantai.manageworkout.ManagedietFragmentDirections
import project.stn991602827.chuantai.viewmodel.ManagedietFragmentViewModel


class DietAdapter(private val dietList:List<Diet>,
                  private val viewModel: ManagedietFragmentViewModel
) :
    RecyclerView.Adapter<DietAdapter.DietViewHolder>() {
    class DietViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTime: TextView = itemView.date_time
        val kj: TextView = itemView.kj
        val btn_update:Button=itemView.btn_update
        val btn_delete:Button=itemView.btn_delete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {

        // inflate the cardView
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_diet, parent, false)
        return DietViewHolder(itemView)
    }
    override fun getItemCount()= dietList.size


    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        val currentItem = dietList[position]

        holder.dateTime.text=currentItem.date.toString()+"\n" +currentItem.time.toString()
        holder.kj.text=currentItem.food+"  "+currentItem.calories.toString()+" \n KJ"
        // if update button is clicked, go to edit jump
        holder.btn_update.setOnClickListener{
            var diet=currentItem.date.toString()+currentItem.time.toString()+currentItem.calories.toString()
            it.findNavController().navigate(
                ManagedietFragmentDirections.actionManagedietFragment3ToEditdietFragment(true,currentItem.id,diet,currentItem.food))
        }
        holder.btn_delete.setOnClickListener{
            viewModel.delete(currentItem)

        }
    }


}
