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
                        name = "Холодные закуски, салаты",
                        image = "1",
                        title = false
                    ),
                    order().copy(
                        name = "Салат: Селедка под шубой",
                        image = "1",
                        title = true,
                        price = "180р",
                        weight = "150г"
                    ),
                    order().copy(
                        name = "Салат: Цезарь с курицей",
                        title = true,
                        image = "18",
                        price = "390р",
                        weight = "270г"
                    ),
                    order().copy(
                        name = "Мясное ассорти",
                        title = true,
                        image = "2",
                        price = "460р",
                        weight = "250г"
                    ),
                    order().copy(
                        name = "Блинчики с форелью",
                        image = "3",
                        title = true,
                        price = "230р",
                        weight = "130г"
                    ),
                    order().copy(
                        name = "Холодец",
                        title = true,
                        image = "4",
                        price = "150р",
                        weight = "100г"
                    ),
                    order().copy(
                        name = "Горячие блюда",
                        image = "1",
                        title = false
                    ),
                    order().copy(
                        name = "Яичница с беконом",
                        image = "16",
                        title = true,
                        price = "380р",
                        weight = "250г"
                    ),
                    order().copy(
                        name = "Свинина с пастой карбанара",
                        title = true,
                        image = "10",
                        price = "780р",
                        weight = "650г"
                    ),
                    order().copy(
                        name = "Шашлык из свинины",
                        image = "7",
                        title = true,
                        price = "480р",
                        weight = "350г"
                    ),
                    order().copy(
                        name = "Свинина с вареным картофелем",
                        title = true,
                        image = "15",
                        price = "1280р",
                        weight = "750г"
                    ),
                    order().copy(
                        name = "Рыба",
                        image = "1",
                        title = false
                    ),
                    order().copy(
                        name = "Форель с броколи",
                        image = "11",
                        title = true,
                        price = "780р",
                        weight = "550г"
                    ),
                    order().copy(
                        name = "Десерты",
                        title = false,
                        image = "10"
                    ),
                    order().copy(
                        name = "Торт: Шотландия",
                        image = "17",
                        title = true,
                        price = "190р",
                        weight = "180г"
                    ),
                    order().copy(
                        name = "Щастье в щоколаде",
                        title = true,
                        image = "15",
                        price = "280р",
                        weight = "220г"
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
//            if (mRecyclerView!!.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING) {
//                mRecyclerView!!.scrollToPosition(mOrderList!!.size - 1)
//            }
        }
    }

    private fun order(): Order {
        return Order(
            name = "Order",
            image = "1",
            title = true
        )
    }

}
