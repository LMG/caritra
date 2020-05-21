package fr.givel.caritra.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import fr.givel.caritra.DataPoint
// Allows us to access the elements by their id directly
// Note: not cached, will call findViewById every time
import kotlinx.android.synthetic.main.main_fragment.*
import fr.givel.caritra.R

class MainFragment : Fragment(), View.OnClickListener  {

    companion object {
        fun newInstance() = MainFragment()
    }

    // Public variable to update the database content (for testing only)
    var contents = ""

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.main_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Doing it in onCreateView is too early because findViewById returns NULL
        button_validate.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    /**
     * Manages the clicks on the fragment elements
     */
    override fun onClick(view: View){
        when (view.id) {
            R.id.button_validate -> onFormCompleted(view)
        }
    }

    /**
     * Shows a message when the form has been completed by the user
     */
    private fun onFormCompleted(view: View){
        // Find selected option
        var buttonId = radioGroup.checkedRadioButtonId
        var selected  = "none"
        if (buttonId != 0)
        {
            selected = radioGroup.findViewById<RadioButton>(buttonId).text.toString()
        }

        contents = viewModel.allDataPoints.value?.joinToString() ?: "fail"

        // Notify it
        Snackbar.make(
            view,
            getString(R.string.done) + " " + selected + contents,
            Snackbar.LENGTH_SHORT
        ).show()

        // Save in database
        viewModel.insert(DataPoint(10))
    }
}
