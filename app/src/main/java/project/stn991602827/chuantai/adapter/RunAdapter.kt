package project.stn991602827.chuantai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_run.view.*
import project.stn991602827.chuantai.R
import project.stn991602827.chuantai.data.Run
import project.stn991602827.chuantai.manageworkout.ManagerunFragmentDirections
import project.stn991602827.chuantai.viewmodel.ManagerunViewModel


class RunAdapter(private val runList: List<Run>,
                 private val viewModel:ManagerunViewModel
) :
    RecyclerView.Adapter<RunAdapter.RunViewHolder>() {
    class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTime: TextView = itemView.date_time
        val distance: TextView = itemView.distance
        val btn_update:Button=itemView.btn_update
        val btn_delete:Button=itemView.btn_delete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {

        // inflate the cardView
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_run, parent, false)
        return RunViewHolder(itemView)
    }
    override fun getItemCount()= runList.size


    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val currentItem = runList[position]

        holder.dateTime.text=currentItem.date.toString()+"\n" +currentItem.time.toString()
        holder.distance.text=currentItem.distance.toString()+" \nKM"
        // if update button is clicked, go to edit run
        holder.btn_update.setOnClickListener{
            var run=currentItem.date.toString()+currentItem.time.toString()+currentItem.distance.toString()
            it.findNavController().navigate(
                ManagerunFragmentDirections.actionManagerunFragment2ToEditrunFragment(currentItem.id,true,run))
        }
        holder.btn_delete.setOnClickListener{
            viewModel.delete(currentItem)

        }
    }


}
