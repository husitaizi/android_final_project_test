package project.stn991602827.chuantai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_jump.view.*
import project.stn991602827.chuantai.R
import project.stn991602827.chuantai.data.Jumping
import project.stn991602827.chuantai.manageworkout.ManagejumpingFragmentDirections
import project.stn991602827.chuantai.viewmodel.ManagejumpingFragmentViewModel


class JumpAdapter(private val jumpList: List<Jumping>,
                  private val viewModel:ManagejumpingFragmentViewModel
) :
    RecyclerView.Adapter<JumpAdapter.JumpViewHolder>() {
    class JumpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTime: TextView = itemView.date_time
        val count: TextView = itemView.rope
        val btn_update:Button=itemView.btn_update
        val btn_delete:Button=itemView.btn_delete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JumpViewHolder {

        // inflate the cardView
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_jump, parent, false)
        return JumpViewHolder(itemView)
    }
    override fun getItemCount()= jumpList.size


    override fun onBindViewHolder(holder: JumpViewHolder, position: Int) {
        val currentItem = jumpList[position]

        holder.dateTime.text=currentItem.date.toString()+"\n" +currentItem.time.toString()
        holder.count.text=currentItem.count.toString()+" \n ROPE"
        // if update button is clicked, go to edit jump
        holder.btn_update.setOnClickListener{
            var jump=currentItem.date.toString()+currentItem.time.toString()+currentItem.count.toString()
            it.findNavController().navigate(
                ManagejumpingFragmentDirections.actionFragmentManagejumpingToEditjumpFragment(true,currentItem.id,jump))
        }
        holder.btn_delete.setOnClickListener{
            viewModel.delete(currentItem)

        }
    }


}
