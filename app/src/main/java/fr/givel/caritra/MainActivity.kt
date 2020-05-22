package fr.givel.caritra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.givel.caritra.ui.main.MainFragment
import fr.givel.caritra.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(), "main") // TODO: is putting main here the best move?
                    .commitNow()
        }
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.allDataPoints.observe(this, Observer {dataPoints ->
            val mainFragment = supportFragmentManager.findFragmentByTag("main") as? MainFragment
            if(mainFragment != null) {
                // Update the cached copy of the dataPoints in the mainFragment.
                mainFragment.contents = ""
                dataPoints?.forEach {
                    mainFragment.contents += ";" + it.id + " " + it.value
                }
            }
        })
    }
}
