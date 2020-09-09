package fr.mijieux.mesmoutons

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val addSheepButton: FloatingActionButton = findViewById(R.id.fab)
        addSheepButton.setOnClickListener {
//            val directions =
            navController.navigate(R.id.nav_add_sheep)
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_present_sheep, R.id.nav_sold_sheep, R.id.nav_dead_sheep,
                R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, arguments ->
//            val showDrawer = arguments?.getBoolean("showDrawer", false) ?: false

            val showDrawerArg = destination.arguments.get("showDrawer")
            val showDrawer = showDrawerArg != null
            Log.d("test1", "destination=${destination}")
            Log.d("test1", "arguments=${arguments}")
            if (showDrawer) {
                Log.d("test1", "showdrawer is true")
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                addSheepButton.isVisible = true
            } else {
                Log.d("test1", "showdrawer is false")
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                addSheepButton.isVisible = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener( object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String ?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_LONG).show()

                searchView.clearFocus()
                searchView.setQuery("", false)
//                searchView.collapse()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.setOnCloseListener {
            searchView.setQuery("", true)
            false
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            val toolbar:Toolbar = findViewById(R.id.toolbar)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
