package project.stn991602827stn991579365.chuantaiAndJunxiu.adapter

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class is for the viewpager displaying of Run, Rope-jumping and Diet segment.
* */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_viewpager.view.*
import project.stn991602827stn991579365.chuantaiAndJunxiu.R

/***
 * The class handles the viewpager pattern displaying of home screens.
 * The class takes a fragmentList of modelFragment to generate three different theme screens.
 * The class implements a Recylcerview Adapter.
 */
class ViewpagerAdapter(private val fragmentList: ArrayList<Fragment>) :
    RecyclerView.Adapter<ViewpagerAdapter.ViewpagerHolder>() {

    /**
     * A class to define a viewholder for modelFragment objects.
     */
    class ViewpagerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workout: ImageView = itemView.imageView
        val link: WebView = itemView.webView
    }

    /**
     * Define a mutable map of drawables used to populate the to be generated modelFragments.
     */
    private val workoutMap = mutableMapOf<Int, Int>(
        0 to R.drawable.running2,
        1 to R.drawable.rope_jumping,
        2 to R.drawable.dieting
    )

    /**
     * Define a mutable map of weblinks used to populate the to be generated modelFragments.
     */
    private val linkMap = mutableMapOf<Int, String>(
        0 to "https://www.nike.com/ca/nrc-app",
        1 to "https://www.nike.com/a/benefits-of-jump-rope",
        2 to "https://www.cdc.gov/healthyweight/healthy_eating/index.html"
    )

    /**
     * A override method to inflate the card view, in this case of modelFragment.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewpagerHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_viewpager, parent, false)
        return ViewpagerHolder(itemView)
    }

    /**
     * A method to bind each modelFragment to a viewholder.
     */
    override fun onBindViewHolder(holder: ViewpagerHolder, position: Int) {
        // Populate the drawable and weblink information as to the position.
        when (position) {
            0 -> {
                // Set the imageResource, which in workoutMap value of key(0) "R.drawable.running2".
                workoutMap[position]?.let { holder.workout.setImageResource(it) }
                // Set the webView loadUrl to corresponding value in the linkMap.
                linkMap[position]?.let { holder.link.loadUrl(it) }
                // Navigate to ManagerunFragment view when the image is clicked.
                holder.workout.setOnClickListener {
                    it.findNavController()
                        .navigate(R.id.action_viewpagerStartPageFragment_to_managerunFragment2)
                }
            }

            1 -> {
                // Set the imageResource as to workoutMap value of key(1) "R.drawable.rope_jumping".
                workoutMap[position]?.let { holder.workout.setImageResource(it) }
                // Set the webView loadUrl to corresponding value in the linkMap.
                linkMap[position]?.let { holder.link.loadUrl(it) }
                // Navigate to ManagejumpFragment view when the image is clicked.
                holder.workout.setOnClickListener {
                    it.findNavController()
                        .navigate(R.id.action_viewpagerStartPageFragment_to_fragmentManagejumping)
                }
            }

            2 -> {
                // Set the imageResource as to workoutMap value of key(1) "R.drawable.dieting".
                workoutMap[position]?.let { holder.workout.setImageResource(it) }
                // Set the webView loadUrl to corresponding value in the linkMap.
                linkMap[position]?.let { holder.link.loadUrl(it) }
                // Navigate to ManagedietFragment view when the image is clicked.
                holder.workout.setOnClickListener {
                    it.findNavController()
                        .navigate(R.id.action_viewpagerStartPageFragment_to_managedietFragment3)
                }
            }
        }
    }

    /**
     * A method to count the size of the fragmentList.
     */
    override fun getItemCount(): Int {
        return fragmentList.size
    }
}