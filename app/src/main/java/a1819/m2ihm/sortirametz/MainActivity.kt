package a1819.m2ihm.sortirametz

import a1819.m2ihm.sortirametz.helpers.FingerPrintHelper
import a1819.m2ihm.sortirametz.helpers.Logger
import a1819.m2ihm.sortirametz.helpers.PreferencesHelper
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_friend.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val requestLogin = 14

    private var mapsFragment: MapsFragment = MapsFragment()
    private var consultFragment: ConsultFragment = ConsultFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Logger.INSTANCE.isLogged(this))
            startActivityForResult(Intent(this, LoginActivity::class.java), requestLogin)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Set MapsFragment to default
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_content, mapsFragment)
        fragmentTransaction.commit()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestLogin && resultCode == Activity.RESULT_OK) {
            txt_username?.text = Logger.INSTANCE.user?.username
            txt_email?.text = Logger.INSTANCE.user?.email
        }
    }

    override fun onResume() {
        super.onResume()
        txt_username?.text = Logger.INSTANCE.user?.username
        txt_email?.text = Logger.INSTANCE.user?.email
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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
                return true
            }

            R.id.menu_help -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_maps -> {
                val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_content, mapsFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
            R.id.nav_place -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_content, consultFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
            R.id.nav_friend -> {
                //TODO clixk on friend
            }
            R.id.nav_share -> {
                //TODO click on share
            }
            R.id.nav_send -> {
                //TODO click on send
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
