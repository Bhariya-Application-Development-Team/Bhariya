package com.sudhir.bhariya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuItemView
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import com.sudhir.bhariya.fragments.*

class MainActivity : AppCompatActivity() {
    private val dashboardFragment = DriverFragment()
    private val historyFragment = HistoryFragment()
    private val settingFragment = SettingFragment()
    private val profileFragment = ProfileFragment()
    private val mapsFragment = MapsFragment()
    private lateinit var button_nav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(dashboardFragment)
        button_nav = findViewById(R.id.botton_nav)
        button_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_dashboard -> replaceFragment(dashboardFragment)
                R.id.ic_history -> replaceFragment(historyFragment)
                R.id.ic_notification -> replaceFragment(profileFragment)
                R.id.ic_setting -> replaceFragment(settingFragment)
            }
            true
        }
    }
    private  fun replaceFragment(fragment: Fragment){

        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentcontainer, fragment)
            transaction.commit()
        }
    }
}