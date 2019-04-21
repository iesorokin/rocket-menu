package ru.rocket.menu.fragments.event

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.rocket.menu.R
import ru.rocket.menu.activities.MainActivity
import java.lang.Thread.sleep

class DescriptionFragment : Fragment() {

    // Кнопка для отправки сообщения
    private var mSettingsBtn: Button? = null
    private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)
        // Inflate the layout for this fragment
        mSettingsBtn = view.findViewById(R.id.btnReady)
        // Переход к настройкам
        progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Спасибо, это блюдо очень вкусное")
        progressDialog.setMessage("Возвращаемся к меню")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.isIndeterminate = false
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        mSettingsBtn?.setOnClickListener {
            DownloadTask().execute(true)

            //      progressDialog.show()
            //     sleep(1 * 1000)


        }

        return view
    }

    private inner class DownloadTask : AsyncTask<Boolean, Int, Void?>() {

        // Before the tasks execution
        override fun onPreExecute() {

            progressDialog.show()

            //     val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //        imm.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            // Display the progress dialog on async task start
//            if (!THE_MAIN_BUTTON_FOR_VIDEO) {
//                sleep(3 * 1000)
//            }
//            mProgressDialog!!.show()

        }

        // Do the task in background/non UI thread
        override fun doInBackground(vararg tasks: Boolean?): Void? {
            sleep(1 * 1000)
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            progressDialog.progress = values[0]!!
        }

        override fun onPostExecute(result: Void?) {
            progressDialog.dismiss()

            val mainIntent = Intent(activity, MainActivity::class.java)
         startActivity(mainIntent)
            activity?.finish()
//            Collections.reverse(mOrderList)\
            /*if (THE_MAIN_BUTTON_FOR_VIDEO){
                sleep(1 * 1000)
            }*/
//            mProgressDialog!!.dismiss()
        }
    }

    private fun goToStart() {


    }

}
