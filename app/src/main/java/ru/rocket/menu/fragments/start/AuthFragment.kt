package ru.rocket.menu.fragments.start


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import ru.rocket.menu.R
import ru.rocket.menu.activities.MainActivity
import ru.rocket.menu.constants.SHARED_USER_DATA
import ru.rocket.menu.constants.TYPE_KITCHEN
import ru.rocket.menu.domain.TypeKitchen
import java.lang.Thread.sleep

class AuthFragment : Fragment() {

    private lateinit var password: EditText
    private lateinit var radioGroup: RadioGroup
    private var typeKitchen: TypeKitchen = TypeKitchen.CANDY
    private lateinit var enterButton: Button

    private lateinit var progressDialog: ProgressDialog

    private var focusView: View? = null

    private lateinit var folder: String

    private var isAuth: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_auth, container, false)

        password = v.findViewById(R.id.loginPassword)
        radioGroup = v.findViewById(R.id.radioGroupTypeKitchen)
        enterButton = v.findViewById(R.id.logBtn)
        progressDialog = ProgressDialog(activity)


        folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            typeKitchen = when (checkedId) {
                R.id.radioButtonBakery -> TypeKitchen.BAKERY
                R.id.radioButtonCold -> TypeKitchen.COLD
                R.id.radioButtonHot -> TypeKitchen.HOT
                R.id.radioButtonPost -> TypeKitchen.POST
                R.id.radioButtonBlank -> TypeKitchen.BLANK
                R.id.radioButtonCandy -> TypeKitchen.CANDY
                else -> TypeKitchen.POST
            }
        }
        enterButton.setOnClickListener {
            val password = password.text.toString()

            if (!TextUtils.isEmpty(password)) {
                progressDialog.setTitle(getString(R.string.progressDialogLogin))
                progressDialog.setMessage(getString(R.string.progressDialogWait))
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.isIndeterminate = false
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)

                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                enterToCook(typeKitchen, password)
            } else {
                this.password.error = "Введите пароль"
                focusView = this.password
                focusView?.requestFocus()
            }
        }
        return v
    }

    private fun enterToCook(typeKitchen: TypeKitchen, password: String) {
        DownloadTask()
            .execute(
                typeKitchen.name, password
            )
    }

    private inner class DownloadTask : AsyncTask<String, Int, Void?>() {

        override fun onPreExecute() {
            progressDialog.show()
        }

        override fun doInBackground(vararg tasks: String): Void? {
            sleep(1000 * 1)
            isAuth = isAuthUser(tasks[0], tasks[1])
            return null
        }

        private fun isAuthUser(typeKitchen: String, password: String): Boolean {
            sharedTypeKitchen(typeKitchen)
            return true
            //todo: huck
        }

        override fun onProgressUpdate(vararg values: Int?) {
            progressDialog.progress = values[0]!!
        }

        override fun onPostExecute(result: Void?) {
            if (!isAuth) {
                progressDialog.dismiss()
                Toast.makeText(activity, "Пароль введен неверно", Toast.LENGTH_SHORT).show()
                password.error = "Проверьте пароль"
                focusView = password
                focusView?.requestFocus()
            } else {
                progressDialog.dismiss()
                Toast.makeText(activity, "Вы на кухне", Toast.LENGTH_SHORT).show()
                sentToMainActivity()
            }
        }
    }

    private fun sharedTypeKitchen(typeKitchen: String) {
        val mSharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences(SHARED_USER_DATA, Context.MODE_PRIVATE)
        val editor = mSharedPreferences.edit()
        editor.putString(TYPE_KITCHEN, typeKitchen)
        editor.apply()
    }

    private fun sentToMainActivity() {
        val mainIntent = Intent(activity, MainActivity::class.java)
        startActivity(mainIntent)
        activity?.finish()
    }

}
