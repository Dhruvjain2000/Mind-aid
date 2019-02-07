package com.example.android.myapplication.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.android.myapplication.Fragments.FavoritesFragment
import com.example.android.myapplication.Fragments.MusicScreenFragment
import com.example.android.myapplication.Fragments.SettingsFragment
import com.example.android.myapplication.Music.MusicActivity
import com.example.android.myapplication.R

class NavigationDrawerAdapter(_contentList: ArrayList<String>, _getImages: IntArray, _context: Context)
    : RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        var itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row_custom_navigationdrawer, parent, false)
        val returnThis = NavViewHolder(itemView)
        return returnThis
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder?.icon_GET?.setBackgroundResource(getImages?.get(position) as Int)
        holder?.text_GET?.setText(contentList?.get(position))
        holder?.contentHolder?.setOnClickListener({
            if (position == 0){
                val musicScreenFragment = MusicScreenFragment()
                (mContext as MusicActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment, musicScreenFragment)
                        .commit()
            }else if (position == 1){
                val favotiteFragment = FavoritesFragment()
                (mContext as MusicActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment, favotiteFragment)
                        .commit()
            }else if (position == 2) {
                val settingsFragment = SettingsFragment()
                (mContext as MusicActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment, settingsFragment)
                        .commit()
            }
            MusicActivity.Statified.drawerLayout?.closeDrawers()
        })
    }

    var contentList: ArrayList<String>?=null
    var getImages: IntArray?=null
    var mContext: Context?=null
    init {
        this.contentList = _contentList
        this.getImages = _getImages
        this.mContext = _context
    }

    override fun getItemCount(): Int {
        return contentList?.size as Int
    }

    class NavViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var icon_GET : ImageView?=null
        var text_GET : TextView?=null
        var contentHolder : RelativeLayout?=null
        init {
            icon_GET = itemView?.findViewById(R.id.icon_navdrawer)
            text_GET = itemView?.findViewById(R.id.text_navdrawer)
            contentHolder = itemView?.findViewById(R.id.navdrawer_item_content_holder)
        }

    }
    }