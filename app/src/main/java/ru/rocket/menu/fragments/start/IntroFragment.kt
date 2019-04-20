package ru.rocket.menu.fragments.start


import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import ru.rocket.menu.R

class IntroFragment : Fragment() {

    private lateinit var loginButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater.inflate(R.layout.fragment_intro, container, false)

        loginButton = v.findViewById(R.id.startLoginButton)

        loginButton.setOnClickListener {
            sendToFragment(AuthFragment())
        }

        return v
    }

    private fun sendToFragment(fragment: Fragment) {
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.startContainer, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private inner class HttpRequestTask : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void): String? {
            try {
                val restTemplate = RestTemplate()
                restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
                return restTemplate.getForObject<String>("http://192.168.1.3:8080/order/string", String::class.java)
            } catch (e: Exception) {
                System.err.println("NADDDDDDD")
            }

            return null
        }

    }

}
