package ru.rocket.menu.activities

import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import ru.rocket.menu.R
import ru.rocket.menu.fragments.event.DescriptionFragment
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket
import java.util.*

class EventActivity : AppCompatActivity() {
    private var mEventId: String? = null
    // ID в массиве сокетов
    private var mSocketId: Int = 0
    private lateinit var instanse: EventActivity
    private val mFragmentManager: FragmentManager? = null

    private val mContainer: Fragment? = null
    private val mFragment: Fragment? = null

    private var out: DataOutputStream? = null
    private var `in`: DataInputStream? = null

    private var mConnectionListeners: ArrayList<ConnectionListener>? = null

    private val id: String
        get() = intent.getStringExtra("ID")


    fun getResourseForDraw(): Resources {
        return instanse.resources
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instanse = this
        setContentView(R.layout.activity_event)
/*
        mEventId = id
        makeEvent(mEventId)

        mSocketId = sThreadsTo.size

*/
/*
        sThreadsTo.add(
            object : Thread() {
                override fun run() {
                    makeConnection()
                }
            }
        )
*//*




        sThreadsTo[mSocketId].start()

        try {
            sThreadsTo[mSocketId].join()

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
*/


        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.eventFragmentContainer)

        if (fragment == null) {

            fragment = DescriptionFragment()
            fm.beginTransaction()
                .add(R.id.eventFragmentContainer, fragment)
                .commit()

        }

        mConnectionListeners = ArrayList()

    }

    private fun makeEvent(id: String?) {

/*        val event = Commands.findEventById(id)
        EventActivity.sEventPreview = EventPreview(
            event.getID(),
            event.getTitle(),
            event.getDescribe(),
            event.getAuthor(),
            event.getImage(),
            event.getKind(),
            event.getTime(),
            event.getPosition(),
            event.getAddress(),
            event.getDate()

        )*/

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

/*
    fun makeConnection() {

        try {

            val ipAddress = InetAddress.getByName(address) // создаем объект который отображает вышеописанный IP-адрес.
            println("Any of you heard of a socket with IP address $address and port $serverPort?")

            var socket = sSocketMap[mEventId]
            if (socket == null || socket.isClosed) {
                println("Yes! I just got hold of the program.")
                socket = Socket(ipAddress, serverPort) // создаем сокет используя IP-адрес и порт сервера.
                sSocketMap[mEventId] = socket

                // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
                val sin = socket.getInputStream()
                val sout = socket.getOutputStream()

                // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
                `in` = DataInputStream(sin)
                out = DataOutputStream(sout)

                out!!.writeUTF(id)

                System.err.println(id)

                out!!.flush()

                println("Type in something and press enter. Will send it to the server and tell ya what it thinks.")
                println()

                sThreadsFrom.add(Thread {

                    while (true) {

                        var messageString = "0 " // ждем пока сервер отошлет строку текста.
                        try {
                            messageString = `in`!!.readUTF()
                        } catch (e: IOException) {
                            break
                        }

                        Log.d("DEBUG", messageString)
                        val commandType = messageString[0]

                        var data = ""
                        if (messageString.length >= 2) {
                            data = messageString.substring(2, messageString.length - 1)
                        }

                        for (listener in mConnectionListeners!!) {
                            if (listener.commandType == commandType) {
                                listener.getData(data)
                            }
                        }
                    }

                })
                sThreadsFrom[mSocketId].start()
            }
        } catch (x: Exception) {

            x.printStackTrace()
        }

    }
*/

    fun addConnectionListener(listener: ConnectionListener) {
        mConnectionListeners!!.add(listener)
    }

    fun sendData(data: String) {
        try {
            out!!.writeUTF(data) // отсылаем введенную строку текста серверу.
            out!!.flush() // заставляем поток закончить передачу данных.
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    interface ConnectionListener {
        val commandType: Char

        fun getData(data: String)
    }

    companion object {

//        var sEventPreview: EventPreview

        var sThreadsFrom: ArrayList<Thread>

        var sThreadsTo: ArrayList<Thread>

        var sSocketMap: HashMap<String, Socket>

        init {
            sThreadsTo = ArrayList()
            sThreadsFrom = ArrayList()
            sSocketMap = HashMap()
        }

        internal var serverPort = 6667 // здесь обязательно нужно указать порт к которому привязывается сервер.

        internal var address = "37.230.113.214" // это IP-адрес компьютера, где исполняется наша серверная программа.
    }
}
