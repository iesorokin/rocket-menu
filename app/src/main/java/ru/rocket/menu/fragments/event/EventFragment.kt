package ru.rocket.menu.fragments.event

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rocket.menu.R
import ru.rocket.menu.activities.EventActivity
import ru.rocket.menu.adapters.EventMonitorAdapter

class EventFragment : Fragment() {

    // Для отображения табов
    private var mViewPager: ViewPager? = null

    // Область, в которой расположены табы
    private var mTabLayout: TabLayout? = null

    //
    private var mPageNumber: Int = 0

    private lateinit var mEventMonitorAdapter: EventMonitorAdapter

    private val isAuthor = false
//        get() = EventActivity.sEventPreview.getAuthor().equals(MainActivity.sPersonDate.getEmail())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_event_guest, container, false)
        mViewPager = view.findViewById(R.id.viewPager)
        mTabLayout = view.findViewById(R.id.tabLayout)

        mEventMonitorAdapter = EventMonitorAdapter(activity as EventActivity?, fragmentManager, 2, isAuthor)
        mViewPager!!.adapter = mEventMonitorAdapter

        // Добавляем стандартного слушателя
        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mTabLayout))

        mTabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager!!.currentItem = tab.position
                mPageNumber = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        mViewPager!!.currentItem = mPageNumber

        return view

    }

}
