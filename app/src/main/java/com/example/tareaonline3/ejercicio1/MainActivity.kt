package tareaOnline3.ejercicio1
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tareaonline3.R
import com.example.tareaonline3.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingsDataStore: SettingsDataStore
    private var conversionRate = 1.1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsDataStore = SettingsDataStore(this)

        setupListeners()
        cargarPreferencias()

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Conversor de Moneda"
    }

    private fun setupListeners() {
        binding.etEurosyDolares.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateResult()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            calculateResult()
        }
    }

    private fun calculateResult() {
        val input = binding.etEurosyDolares.text.toString()
        if (input.isEmpty()) {
            binding.etResultado.setText("")
            return
        }

        val amount = input.toDoubleOrNull()
        if (amount == null) {
            binding.etResultado.setText("Error")
            return
        }

        val result = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioEurosaDolares -> amount * conversionRate
            R.id.radioDolaresaEuros -> amount / conversionRate
            else -> 0.0
        }

        binding.etResultado.setText(String.format("%.2f", result))
    }

    private fun cargarPreferencias() {
        lifecycleScope.launch {
            // Observar cambios en el color de fondo
            settingsDataStore.backgroundColor.collect { color ->
                when (color) {
                    "blue" -> binding.root.setBackgroundResource(android.R.color.holo_blue_light)
                    "green" -> binding.root.setBackgroundResource(android.R.color.holo_green_light)
                    "yellow" -> binding.root.setBackgroundResource(android.R.color.holo_orange_light)
                }
            }
        }

        lifecycleScope.launch {
            // Observar cambios en el ratio de conversión
            settingsDataStore.conversionRate.collect { rate ->
                conversionRate = rate
                calculateResult()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_preferences -> {
                // Abrir la actividad de preferencias
                startActivity(Intent(this, PreferencesActivity::class.java))
                true
            }
            R.id.menu_about -> {
                showAboutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Acerca de")
            .setMessage("Aplicación creada por Juanma Navia Ortega")
            .setPositiveButton("Aceptar", null)
            .show()
    }
}