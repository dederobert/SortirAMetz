package a1819.m2ihm.sortirametz

import a1819.m2ihm.sortirametz.MapsFragment.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory
import a1819.m2ihm.sortirametz.helpers.Logger
import a1819.m2ihm.sortirametz.helpers.ValueHelper
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mapsFragment: MapsFragment = MapsFragment()
    private var listFragment: ListFragment = ListFragment()
    private var friendFragment: FriendFragment = FriendFragment()

    private var placeDAO: PlaceDAO? = null
    private var categoryDAO: CategoryDAO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        placeDAO = AbstractDAOFactory.getFactory(this, ValueHelper.INSTANCE.factoryType)
                .placeDAO

        categoryDAO = AbstractDAOFactory.getFactory(this, ValueHelper.INSTANCE.factoryType).categoryDAO

        //Set MapsFragment to default
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_content, mapsFragment)
        fragmentTransaction.commit()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.menu.getItem(0).isChecked = true

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.size == 1
                    && permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                mapsFragment.locator.map.isMyLocationEnabled = true
            else
                Toast.makeText(this, R.string.needed_loaction, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menu_setting -> {
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_disconnect -> {
                Logger.INSTANCE.disconnect(this)
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
                return true
            }

            R.id.menu_help -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PlaceActivity.REQUEST_ADD && data != null) {
                val place = placeDAO?.find(data.getLongExtra("placeId", -1))
                if (place != null) listFragment.adapter.insertElement(place)
                Log.d("[MainActivity]", "Adding a new place: " + place!!)
            } else if (requestCode == CategoryActivity.REQUEST_ADD && data != null) {
                val category = categoryDAO?.find(data.getLongExtra("categoryId", -1))
                if (category != null) listFragment.adapter.insertElement(category)
                Log.d("[MainActivity]", "Adding a new category: " + category!!)
            }
        }
        if (requestCode == PlaceActivity.REQUEST_EDIT ||
                requestCode == CategoryActivity.REQUEST_EDIT ||
                resultCode == RESULT_CANCELED)
            listFragment.list.adapter!!.notifyDataSetChanged()
    }

    private var isListNav: Boolean = false

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_maps -> {
                val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_content, mapsFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                isListNav = false
            }
            R.id.nav_place -> {
                listFragment.displayMode = ListFragment.DisplayMode.PLACE
                if (!isListNav) {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_content, listFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }else
                    listFragment.updateAdapter()
                isListNav = true
            }
            R.id.nav_category -> {
                listFragment.displayMode = ListFragment.DisplayMode.CATEGORY
                if (!isListNav) {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_content, listFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }else
                    listFragment.updateAdapter()
                isListNav = true
            }
            R.id.nav_friend -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_content, friendFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                isListNav = false
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
