package ntnu.katarzsz.oving5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

const val URL = "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp"

class GameActivity : Activity() {
    private lateinit var displayName: TextView
    private lateinit var number: EditText
    private val network: HttpWrapper = HttpWrapper(URL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        val name = intent.getStringExtra("name") as String
        val bankAccount = intent.getStringExtra("bankAccount") as String
        displayName = findViewById(R.id.display_name)
        displayName.setText("Hei "+name)

        performRequest(HTTP.GET, mapOf(
            "navn" to name,
            "kortnummer" to bankAccount,
        ))
    }

    /**
     * Utfør en HTTP-forespørsel separat fra hovedtråden
     */
    private fun performRequest(typeOfRequest: HTTP, parameterList: Map<String, String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val response: String = try {
                when (typeOfRequest) {
                    HTTP.GET -> network.get(parameterList)
                    HTTP.POST -> network.post(parameterList)
                    HTTP.GET_WITH_HEADER -> network.getWithHeader(parameterList)
                }
            } catch (e: Exception) {
                Log.e("performRequest()", e.message!!)
                e.toString()
            }

            // Endre UI på hovedtråden
            MainScope().launch {
                setResult(response)
            }
        }
    }

    /**
     * Show result from server in UI
     */
    private fun setResult(response: String?) {
        findViewById<TextView>(R.id.result).text = response
    }

    /**
     * Send number to the server
     */
    fun sendNumber(view: View) {
        number = findViewById(R.id.number)
        performRequest(HTTP.GET, mapOf("tall" to number.text.toString()))
    }

    /**
     * Go back to registration form
     */
    fun showRegisterView(view: View) {
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

}