package tareaOnline3.ejercicio1
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tareaonline3.databinding.ActivityPreferencesBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class PreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferencesBinding
    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsDataStore = SettingsDataStore(this)

        setupListeners()
        cargarPreferenciasActuales()
    }
//-------funcion setupListeners-------
    private fun setupListeners() {
        binding.buttonSave.setOnClickListener {
            guardarPreferencias()
        }
    }

    //-------funcion cargarPreferenciasActuales-------
    private fun cargarPreferenciasActuales() {
        lifecycleScope.launch {
            settingsDataStore.backgroundColor.collect { color ->
                when (color) {
                    "blue" -> binding.radioBlue.isChecked = true
                    "green" -> binding.radioGreen.isChecked = true
                    "yellow" -> binding.radioYellow.isChecked = true
                }
            }
        }

        lifecycleScope.launch {
            val rate = settingsDataStore.conversionRate.first()
            binding.etConversionRate.setText(rate.toString())
        }
    }

    //-------funcion guardarPreferencias-------
    private fun guardarPreferencias() {
        val color = when {
            binding.radioBlue.isChecked -> "blue"
            binding.radioGreen.isChecked -> "green"
            binding.radioYellow.isChecked -> "yellow"
            else -> "blue"
        }

        val rate = binding.etConversionRate.text.toString().toDoubleOrNull() ?: 1.1

        lifecycleScope.launch {
            settingsDataStore.guardarBackgroundColor(color)
            settingsDataStore.guardarRateConversion(rate)
            finish()
        }
    }
}