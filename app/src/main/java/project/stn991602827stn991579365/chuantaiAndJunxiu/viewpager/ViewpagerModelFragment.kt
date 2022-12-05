package project.stn991602827stn991579365.chuantaiAndJunxiu.viewpager

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class models the viewPager fragments.
*/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import project.stn991602827stn991579365.chuantaiAndJunxiu.R
import project.stn991602827stn991579365.chuantaiAndJunxiu.databinding.FragmentViewpagerBinding

/**
 * The class models the viewPager fragments.
 */
class ViewpagerModelFragment : Fragment() {

    // Defines a variable to be initialized as needed.
    private lateinit var binding: FragmentViewpagerBinding

    companion object {
        // Holds the fragment id passed in when created
        val messageID = "messageID"

        fun newInstance(message: String): ViewpagerModelFragment {
            // Create the fragment
            val fragment = ViewpagerModelFragment()
            // Create a bundle for our message/id
            val bundle = Bundle(1)
            // Load up the Bundle
            bundle.putString(messageID, message)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * Tasks to be done on creating the view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Initialize the data binding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_viewpager, container, false)

        // Set up attributes on the webview.
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()

        return binding.root
    }
}