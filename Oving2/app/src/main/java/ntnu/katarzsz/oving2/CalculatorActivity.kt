package ntnu.katarzsz.oving2

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class CalculatorActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
    }

    fun changeNumbers() {

    }

    fun onAdd(view: View) {
        var number1 = findViewById<View>(R.id.number1) as TextView
        var number2 = findViewById<View>(R.id.number2) as TextView
        var userAnswer = findViewById<View>(R.id.answer) as TextView
        var userAnswerInt = Integer.parseInt(userAnswer.text.toString())
        var realAnswer = Integer.parseInt(number1.text.toString()) + Integer.parseInt(number2.text.toString())

        if (userAnswerInt == realAnswer) {
            Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.wrong) + " "+realAnswer.toString(), Toast.LENGTH_LONG).show()
        }

        changeNumbers()
    }

    fun onMultiply(view: View) {
        var number1 = findViewById<View>(R.id.number1) as TextView
        var number2 = findViewById<View>(R.id.number2) as TextView
        var userAnswer = findViewById<View>(R.id.answer) as TextView
        var userAnswerInt = Integer.parseInt(userAnswer.text.toString())
        var realAnswer = Integer.parseInt(number1.text.toString()) * Integer.parseInt(number2.text.toString())

        if (userAnswerInt == realAnswer) {
            Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.wrong) + " "+realAnswer.toString(), Toast.LENGTH_LONG).show()
        }

        changeNumbers()
    }
}