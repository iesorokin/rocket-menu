package ru.rocket.menu.fragments.main


import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rocket.menu.R
import ru.rocket.menu.activities.isReady
import ru.rocket.menu.adapters.EventsListAdapter
import ru.rocket.menu.domain.Order
import java.lang.Thread.sleep

class HomeFragment : Fragment() {

    // Список события
    private var mOrderList: MutableList<Order>? = null
    // Адаптер для вывода списка событий
    private var mRecyclerView: RecyclerView? = null
    private var mEventsListAdapter: EventsListAdapter? = null

    // Диалог во время выполнения авторизации
    private var mProgressDialog: ProgressDialog? = null
    private var mMyTask: AsyncTask<*, *, *>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_home, container, false)
        mProgressDialog = ProgressDialog(activity)

        mProgressDialog!!.setTitle("Загрузка заказов")
        mProgressDialog!!.setMessage(getString(R.string.progressDialogWait))
        mProgressDialog!!.setCanceledOnTouchOutside(false)
        mProgressDialog!!.isIndeterminate = false
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        mRecyclerView = v.findViewById(R.id.recyclerHome)

        mRecyclerView!!.setHasFixedSize(true)

        val llm = LinearLayoutManager(activity)
        mRecyclerView!!.layoutManager = llm

        // Загрузка событий из базы данных
        initializeData(true)
        val isR = !isReady
        if (isR) {
            initializeData(false)
        }
        return v

    }

    private fun addBludo() {
        mOrderList?.add(
            order().copy(
                name = "Салат: Цезарь с курицей",
                image = "5"
            )
        )
    }

    // Загрузка событий из баззы данных
    private fun initializeData(a: Boolean) {
        mMyTask = DownloadTask()
            .execute(a)
    }

    private inner class DownloadTask : AsyncTask<Boolean, Int, Void?>() {

        // Before the tasks execution
        override fun onPreExecute() {

            // Display the progress dialog on async task start
//            if (!THE_MAIN_BUTTON_FOR_VIDEO) {
//                sleep(3 * 1000)
//            }
//            mProgressDialog!!.show()

        }

        // Do the task in background/non UI thread
        override fun doInBackground(vararg tasks: Boolean?): Void? {
            if (!tasks[0]!!) {
                sleep(4 * 1000)
                addBludo()
                isReady = true
            }
            //todo: added rest to back for load all orders
            if (mOrderList == null) {
                mOrderList = listOf(
                    order().copy(
                        name = "Салат: Селедка под шубой",
                        image = "1"
                    ),
                    order().copy(
                        name = "Мясное ассорти",
                        image = "2"
                    ),
                    order().copy(
                        name = "Блинчики с форелью",
                        image = "3"
                    ),
                    order().copy(
                        name = "Холодец",
                        image = "4"
                    )
                ).toMutableList()

            }
            return null

        }

        fun onProgressUpdate(vararg progress: Int) {
            mProgressDialog!!.progress = progress[0]
        }

        override fun onPostExecute(result: Void?) {
//            Collections.reverse(mOrderList)\
            /*if (THE_MAIN_BUTTON_FOR_VIDEO){
                sleep(1 * 1000)
            }*/
//            mProgressDialog!!.dismiss()
            mEventsListAdapter = EventsListAdapter(mOrderList)
            mRecyclerView!!.adapter = mEventsListAdapter
            if (mRecyclerView!!.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING) {
                mRecyclerView!!.scrollToPosition(mOrderList!!.size - 1)
            }
        }
    }

    private fun order(): Order {
        return Order(
            name = "Order",
            image = "1"
        )
    }

}
