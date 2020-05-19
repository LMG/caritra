package fr.givel.caritra.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import fr.givel.caritra.R

class MainFragment : Fragment(), View.OnClickListener  {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.main_fragment, container, false)

        var button: Button = view.findViewById(R.id.button_validate)
        button.setOnClickListener(this);

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    /**
     * Manages the clicks on the fragement elements
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
