package fr.givel.caritra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.google.android.material.snackbar.Snackbar
import fr.givel.caritra.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    /**
     * Shows a message when the form has been completed by the user
     */

    fun onFormCompleted(view: View){
        // Find selected option
        //var selected = findViewById(R.id.radioGroup)

        // Notify it
        Snackbar.make(
            view,
            getString(R.string.done),
            Snackbar.LENGTH_SHORT
        ).show()

        //TODO: save in database
    }
}
