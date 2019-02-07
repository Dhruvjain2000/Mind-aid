package com.example.android.myapplication.Music

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.example.android.myapplication.Adapters.NavigationDrawerAdapter
import com.example.android.myapplication.Fragments.MusicScreenFragment
import com.example.android.myapplication.Fragments.SongPlayingFragment
import com.example.android.myapplication.MainActivity
import com.example.android.myapplication.R

class MusicActivity : AppCompatActivity() {

    var navigationDrawerIconsList: ArrayList<String> = arrayListOf()
    var images_for_navdrawer = intArrayOf(R.drawable.navigation_allsongs, R.drawable.navigation_favorites,
            R.drawable.navigation_settings, R.drawable.navigation_aboutus)
    var trackNotificationBuilder: Notification?=null


    var permissionsString = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.RECORD_AUDIO)

    object Statified{
        var drawerLayout : DrawerLayout?=null
        var notificationManager: NotificationManager?=null
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_music)

        if (!hasPermissions(this@MusicActivity, *permissionsString)) {
            //we have to ask for permissions
            ActivityCompat.requestPermissions(this@MusicActivity, permissionsString, 131)
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        MusicActivity.Statified.drawerLayout = findViewById(R.id.drawer_layout)

        navigationDrawerIconsList.add("All Songs")
        navigationDrawerIconsList.add("Favorites")
        navigationDrawerIconsList.add("Settings")
        navigationDrawerIconsList.add("About Us")

        var toggle = ActionBarDrawerToggle(this@MusicActivity, Statified.drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        MusicActivity.Statified.drawerLayout?.setDrawerListener(toggle)
        toggle.syncState()

        val musicScreenFragment = MusicScreenFragment()
        this.supportFragmentManager
                .beginTransaction()
                .add(R.id.details_fragment, musicScreenFragment, "MusicScreenFragment")
                .commit()

        var _navigationAdapter = NavigationDrawerAdapter(navigationDrawerIconsList, images_for_navdrawer, this)
        _navigationAdapter.notifyDataSetChanged()

        var navigation_recycler_view = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager = LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator = DefaultItemAnimator()
        navigation_recycler_view.adapter = _navigationAdapter
        navigation_recycler_view.setHasFixedSize(true)

        val intent = Intent(this@MusicActivity, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this@MusicActivity, System.currentTimeMillis().toInt(),
                intent, 0)
        trackNotificationBuilder = Notification.Builder(this)
                .setContentTitle("A track is playing in the background")
                .setSmallIcon(R.drawable.logo_head2)
                .setContentIntent(pIntent)
                .setOngoing(true)
                .setAutoCancel(true)
                .build()
        Statified.notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    override fun onStart() {
        super.onStart()
        try {
            Statified.notificationManager?.cancel(345)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            if (SongPlayingFragment.Statified.mediaplayer?.isPlaying as Boolean) {
                Statified.notificationManager?.notify(345, trackNotificationBuilder)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            Statified.notificationManager?.cancel(345)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            131 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
                    Handler().postDelayed({
                        val startAct = Intent(this@MusicActivity, MainActivity::class.java)
                        startActivity(startAct)
                        this.finish()
                    }, 2000)
                } else {
                    Toast.makeText(this@MusicActivity, "Please grant all permissions!", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
                return
            }
            else -> {
                Toast.makeText(this@MusicActivity, "Something went wrong!", Toast.LENGTH_SHORT).show()
                this.finish()
                return
            }
        }
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        var hasAllPermissions = true
        for (permission in permissions) {
            var res = context.checkCallingOrSelfPermission(permission)
            if (res != PackageManager.PERMISSION_GRANTED) {
                hasAllPermissions = false
            }
        }
        return hasAllPermissions
    }


}