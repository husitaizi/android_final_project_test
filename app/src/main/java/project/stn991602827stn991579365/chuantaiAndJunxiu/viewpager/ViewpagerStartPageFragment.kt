package project.stn991602827stn991579365.chuantaiAndJunxiu.viewpager

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The viewpager2 configurations.
*/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import project.stn991602827stn991579365.chuantaiAndJunxiu.R
import project.stn991602827stn991579365.chuantaiAndJunxiu.adapter.ViewpagerAdapter
import project.stn991602827stn991579365.chuantaiAndJunxiu.databinding.FragmentViewpagerStartPageBinding


/**
 * The class configures the viewpager2 model, and the corresponding xml layout contains the
 * viewpager2 view.
 */
class ViewpagerStartPageFragment : Fragment() {

    /**
     * Tasks on the creation of the view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Define a data binding.
        val binding: FragmentViewpagerStartPageBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_viewpager_start_page,
            container,
            false)

        // Define an ArrayList of Fragment.
        val fragmentList = ArrayList<Fragment>()

        // Add three ViewpagerModelFragment represent run, jump and diet.
        fragmentList.add(ViewpagerModelFragment.newInstance("run"))
        fragmentList.add(ViewpagerModelFragment.newInstance("jump"))
        fragmentList.add(ViewpagerModelFragment.newInstance("diet"))

        // Define the pager object.
        val pager = binding.pager as ViewPager2

        // Set up an adapter on pager.
        pager.adapter = ViewpagerAdapter(fragmentList)

        return binding.root
    }
}