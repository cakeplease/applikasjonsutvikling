package ntnu.katarzsz.oving2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class NumberGenerator : Activity() {
    private var upperLimit = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        upperLimit = intent.getIntExtra("upperLimit", upperLimit)
        val value1 = (0..upperLimit).random()
        val value2 = (0..upperLimit).random()
        val intent = Intent()
        intent.putExtra("upperLimit", upperLimit.toString())
        intent.putExtra("randomNumber", value1.toString())
        intent.putExtra("randomNumber2", value2.toString())
        //Toast.makeText(this, value.toString(), Toast.LENGTH_LONG).show()
        setResult(RESULT_OK, intent)

        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RequestCode.TEST) {
            val textView = findViewById<View>(R.id.text_view_random_number) as TextView
            textView.text = data.getStringExtra("randomNumber")
        }
    }


}