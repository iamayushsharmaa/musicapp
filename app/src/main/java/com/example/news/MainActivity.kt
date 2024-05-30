
package com.example.news

import ViewPagerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.news.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


//d688ac952aff4c4187ea180d0bfa960c

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toggle = ActionBarDrawerToggle(this@MainActivity, binding.root, R.string.open, R.string.close)
        //binding.root.addDrawerListener(toggle)
        setSupportActionBar(binding.toolbar)
        //toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toggle = ActionBarDrawerToggle(this,binding.drawerlayout, binding.toolbar ,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        binding.drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        // Initialize the ViewPager adapter
        adapter = ViewPagerAdapter(supportFragmentManager, 6) // Adjust the count accordingly
        binding.viewPager.adapter = adapter


        binding.navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation view item clicks here.
            when (menuItem.itemId) {
                R.id.profile -> {
                    val intenttoProfile = Intent(this, UserProfileActivity::class.java)
                    startActivity(intenttoProfile)
                }
                R.id.settings -> {
                    // Handle the gallery action
                }
                R.id.logout -> {
                    Firebase.auth.signOut()

                    Toast.makeText(this, "Sign out", Toast.LENGTH_SHORT).show()
                    val intentBackLogin = Intent(this,LoginActivity::class.java)
                    startActivity(intentBackLogin)
                }
            }
            binding.drawerlayout.closeDrawer(GravityCompat.START)
            true
        }
        // Setup the TabLayout with the ViewPager
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        // Listen for tab selection changes
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // No need to check position here
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Not yet implemented
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Not yet implemented
            }
        })

        // Listen for page changes to update TabLayout
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
    }
}

