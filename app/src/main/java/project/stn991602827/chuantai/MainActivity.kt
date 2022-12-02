package project.stn991602827.chuantai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import project.stn991602827.chuantai.data.WorkoutDatabase
import project.stn991602827.chuantai.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var mDb: WorkoutDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)


        // Initialize the database
        mDb = WorkoutDatabase.getInstance(applicationContext)

        mDb.runDao().getAll()
        // TODO when run image is clicked go to run management



        // TODO can we reach menu item via binding?
        val nav_item = binding.navView.menu.getItem(0)
        while (onOptionsItemSelected(nav_item) == true) {

/*            when (nav_item.title){
                    R.string.workout_management.toString() -> Navigation.findNavController()
            }*/
        }


        // drawer layout instance to toggle the menu icon to open and close
        drawerLayout = binding.drawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)

        // pass the Open and Close toggle from the drawer layout listener to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation Drawer icon always apear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // function to implement
        // the item click listener callback
        // to open and close the navigation
        // drawer when the icon is clicked
         fun onOptionsItemSelected(item: MenuItem): Boolean {
            return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
                true
            } else super.onOptionsItemSelected(item)
        }


        // menu navigation can reference to advanced navigation sample


    }//end of onCreate


}


// override the onOptionItemSelected()

