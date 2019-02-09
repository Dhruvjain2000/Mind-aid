package com.example.android.myapplication.Meme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ToxicBakery.viewpager.transforms.*
import com.example.android.myapplication.R

class MemeActivity : AppCompatActivity() {

    private var selectedClassIndex: Int = 0
    private lateinit var pager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_meme)
        setSupportActionBar(findViewById(R.id.toolbar))

        selectedClassIndex = savedInstanceState?.getInt(KEY_SELECTED_CLASS) ?: 0

        pager = findViewById(R.id.container)
        pager.adapter = PageAdapter(supportFragmentManager)
        pager.currentItem = savedInstanceState?.getInt(KEY_SELECTED_PAGE) ?: 0

        title = ""

        findViewById<AppCompatSpinner>(R.id.spinner).apply {
            adapter = ArrayAdapter(
                    applicationContext,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    TRANSFORM_CLASSES)
            setSelection(selectedClassIndex)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit

                override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long) = selectPage(position)
            }
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_CLASS, selectedClassIndex)
        outState.putInt(KEY_SELECTED_PAGE, pager.currentItem)
    }

    private fun selectPage(position: Int) {
        selectedClassIndex = position
        pager.setPageTransformer(true, TRANSFORM_CLASSES[position].clazz.newInstance())
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val position = arguments!!.getInt(EXTRA_POSITION)
            val textViewPosition = inflater.inflate(R.layout.fragment_meme, container, false) as TextView
            textViewPosition.text = position.toString()
            textViewPosition.setBackgroundColor(COLORS[position - 1])

            return textViewPosition
        }

        companion object {
            private const val EXTRA_POSITION = "EXTRA_POSITION"
            private val COLORS = intArrayOf(-0xcc4a1b, -0x559934, -0x663400, -0x44cd, -0xbbbc)

            fun newInstance(position: Int) = PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(PlaceholderFragment.EXTRA_POSITION, position)
                }
            }
        }

    }

    private class PageAdapter internal constructor(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment =
                PlaceholderFragment.newInstance(position + 1)

        override fun getCount(): Int {
            return 5
        }

    }

    class TransformerItem(
            val clazz: Class<out ViewPager.PageTransformer>,
            val title: String = clazz.simpleName
    ) {
        override fun toString(): String = title
    }

    companion object {
        private const val KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE"
        private const val KEY_SELECTED_CLASS = "KEY_SELECTED_CLASS"

        private val TRANSFORM_CLASSES: List<TransformerItem> = listOf(
                TransformerItem(StackTransformer::class.java)
        )

    }

}
