package tareaOnline3.ejercicio2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tareaonline3.databinding.ActivityEditarAlarmaBinding

class EditarAlarmaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarAlarmaBinding
    private lateinit var alarmaManager: AlarmaManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarAlarmaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmaManager = AlarmaManager(this)

        // Cargar datos de la alarma si se pasaron
        val duracion = intent.getIntExtra("duracion", 0)
        val descripcion = intent.getStringExtra("descripcion") ?: ""
        val sonido = intent.getStringExtra("sonido") ?: ""

        binding.etDuracion.setText(duracion.toString())
        binding.etDescripcion.setText(descripcion)
        binding.etSonido.setText(sonido)

        binding.btnGuardar.setOnClickListener {
            val nuevaDuracion = binding.etDuracion.text.toString().toIntOrNull() ?: 0
            val nuevaDescripcion = binding.etDescripcion.text.toString()
            val nuevoSonido = binding.etSonido.text.toString()

            if (nuevaDuracion > 0 && nuevaDescripcion.isNotEmpty() && nuevoSonido.isNotEmpty()) {
                val alarmas = alarmaManager.cargarAlarmas().toMutableList()

                // Verifica que no se supere el límite de 5 alarmas
                if (alarmas.size >= 5) {
                    Toast.makeText(this, "No puedes tener más de 5 alarmas", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val alarma = Alarma(nuevaDuracion, nuevaDescripcion, nuevoSonido)

                // Si se está editando una alarma existente la reemplázara
                if (duracion > 0 || descripcion.isNotEmpty() || sonido.isNotEmpty()) {
                    val index = alarmas.indexOfFirst { it.duracion == duracion && it.descripcion == descripcion && it.sonido == sonido }
                    if (index != -1) {
                        alarmas[index] = alarma
                    }
                } else {

                    alarmas.add(alarma)
                }

                alarmaManager.guardarAlarmas(alarmas)
                Log.d("EditarAlarmaActivity", "Alarma guardada: $alarma")
                finish()
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}