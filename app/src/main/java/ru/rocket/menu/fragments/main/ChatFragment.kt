package ru.rocket.menu.fragments.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import ru.rocket.menu.R
import ru.rocket.menu.activities.MainActivity
import ru.rocket.menu.adapters.MessageAdapter
import ru.rocket.menu.domain.Message
import java.time.LocalDateTime
import java.util.*

//todo: add listener
class ChatFragment : Fragment() {

    private var mMainActivity: MainActivity? = null
    //Лист сообщений
    private var mMessagesList: MutableList<Message>? = null

    // Адаптер сообщений
//    private var mMessageAdapter: MessageAdapter? = null

    // Сообщение
    private var mMessage: EditText? = null

    // Кнопка для отправки сообщения
    private var mSendBtn: ImageButton? = null

    // Список сообщений
    private var mRecyclerView: RecyclerView? = null

    private var mMessageAdapter: MessageAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_chat, container, false)

        mRecyclerView = v.findViewById(R.id.recyclerChat)

        mRecyclerView!!.setHasFixedSize(true)
        val llm = LinearLayoutManager(activity)
        mRecyclerView!!.layoutManager = llm

        showMessages()

        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivity = activity as MainActivity?
    }

    private fun showMessages() {
        initializeData()
    }

    private fun addMessage(message: String) {
        if (!TextUtils.isEmpty(message)) {
            val data = LocalDateTime.now()
            mMessagesList?.add(
                message().copy(
                    from = "Холодный цех",
                    message = message,
                    date = data
                )
            )
            mMessage?.setText("")
            showMessages()
//            mMainActivity!!.sendData(data)
        }
    }

    private fun initializeData() {
//        val thread = object : Thread() {
//            override fun run() {
        if (mMessagesList == null)
            mMessagesList = listOf(
                message().copy(
                    message = "Салат: цезарь с курицей",
                    from = "Холодный цех"
                )
            ).toMutableList()
        activity!!.runOnUiThread {
            mMessageAdapter = MessageAdapter(mMessagesList)
            mRecyclerView!!.adapter = mMessageAdapter
            if (mRecyclerView!!.scrollState != RecyclerView.SCROLL_STATE_DRAGGING) {
                mRecyclerView!!.scrollToPosition(mMessagesList!!.size - 1)
            }
        }
//            }
//        }
//        thread.start()
//        try {
//            thread.join()
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
    }

    private fun message(): Message {
        return Message(
            date = LocalDateTime.now(),
            from = if (Random().nextBoolean()) "Bacary" else "Candy",
            message = "It is my first message"
        )
    }

    fun getCommandType(): Char {
        return '1'
    }

    fun getData(data: String) {
        Log.d("DEBUG", "chat get data")

        val email = data.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        val timeString = data.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        val time = java.lang.Long.parseLong(timeString)
        val mes = data.substring(email.length + timeString.length + 2)
        Log.d("DEBUG", "Get $data")
        if (!mes.isEmpty()) {
/*            val message = Message(
                time,
                email,
                mes
            )*/
/*
            activity!!.runOnUiThread {
                mMessagesList!!.add(message)
//                mRecyclerView!!.adapter = mMessageAdapter
                mMessage!!.setText("")
                if (mRecyclerView!!.scrollState != RecyclerView.SCROLL_STATE_DRAGGING) {
                    mRecyclerView!!.scrollToPosition(mMessagesList!!.size - 1)
                }
            }*/
        }
    }

}
