package project.stn991602827stn991579365.chuantaiAndJunxiu

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class defines a viewModel factory.
*/
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.WorkoutDatabase
import project.stn991602827stn991579365.chuantaiAndJunxiu.databinding.ActivityMainBinding


/**
 * MainActivity class.
 */
class MainActivity : AppCompatActivity() {

    // Defines the variables to be initialized as needed.
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var mDb: WorkoutDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)


        // TODO can we reach menu item via binding?
/*        val nav_item = binding.navView.menu.getItem(0)
        while (onOptionsItemSelected(nav_item) == true) {

*//*            when (nav_item.title){
                    R.string.workout_management.toString() -> Navigation.findNavController()
            }*//*
        }*/


        // Drawer layout instance to toggle the menu icon to open and close.
        drawerLayout = binding.drawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)

        // Pass the Open and Close toggle from the drawer layout listener to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // To make the Navigation Drawer icon always apear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }//end of onCreate

    // Function to implement the item click listener callback to open and close the navigation
    // drawer when the icon is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}


