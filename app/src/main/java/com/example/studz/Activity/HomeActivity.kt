package com.example.studz.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.studz.GlideImageLoader
import com.example.studz.Model.Constants
import com.example.studz.Model.User
import com.example.studz.R
import com.facebook.login.LoginManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.nav_header_main.view.*

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var user: User
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_timetable, R.id.nav_subject, R.id.nav_tasks, R.id.nav_note), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        mAuth = FirebaseAuth.getInstance()

        if (intent.hasExtra(Constants.USER)){
            user = intent.getParcelableExtra(Constants.USER)!!
            val headerView: View = navView.getHeaderView(0)
            val tvName:TextView = headerView.tv_name
            val tvEmail:TextView = headerView.tv_email
            val imageView:ImageView = headerView.imageView_photo

            tvName.text = user.uName
            tvEmail.text = user.uEmail
            GlideImageLoader(this).loadImage(user.uPhoto, imageView)

        }

        navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            mAuth.signOut()
            LoginManager.getInstance().logOut()
            finish()
            startActivity(Intent(this, SignInActivity::class.java))
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}