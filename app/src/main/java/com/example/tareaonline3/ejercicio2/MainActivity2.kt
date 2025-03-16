package tareaOnline3.ejercicio2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.tareaonline3.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var alarmaManager: AlarmaManager
    private lateinit var preferenciasManager: PreferenciasManager

    // ------Handler para actualizar la UI cada segundo---------
    private val handler = Handler(Looper.getMainLooper())
    private val actualizarUITask = object : Runnable {
        override fun run() {
            val alarmas = alarmaManager.cargarAlarmas()
            Log.d("MainActivity2", "actualizarUITask - Alarmas cargadas: ${alarmas.size}")
            actualizarUI(alarmas)

            if (alarmas.isNotEmpty()) {
                handler.postDelayed(this, 1000)
            } else {
                Log.d("MainActivity2", "No hay más alarmas pendientes. Deteniendo actualización.")
            }
        }
    }

    // BroadcastReceiver para escuchar cuando una alarma se completa
    private val alarmaCompletadaReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("MainActivity2", "Broadcast recibido: Alarma completada")

            Handler(Looper.getMainLooper()).post {
                val alarmas = alarmaManager.cargarAlarmas()
                actualizarUI(alarmas)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmaManager = AlarmaManager(this)
        preferenciasManager = PreferenciasManager(this)

        // Cargar alarmas y actualizar  UI
        val alarmas = alarmaManager.cargarAlarmas()
        Log.d("MainActivity2", "onCreate() - Alarmas cargadas: ${alarmas.size}")
        actualizarUI(alarmas)

        binding.btnEditarAlarmas.setOnClickListener {
            Log.d("MainActivity2", "btnEditarAlarmas- Clic")
            startActivity(Intent(this, EditarAlarmaActivity::class.java))
        }

        binding.btnIniciarAlarmas.setOnClickListener {
            Log.d("MainActivity2", "btnIniciarAlarmas - Clic")
            handler.removeCallbacks(actualizarUITask) // Detener el Handler
            programarAlarmas(this, alarmaManager.cargarAlarmas())
        }

        binding.btnReiniciarAlarmas.setOnClickListener {
            Log.d("MainActivity2", "btnReiniciarAlarmas - Clic")
            alarmaManager.reiniciarAlarmas()
            actualizarUI(emptyList()) // Actualizar la UI para mostrar 0 alarmas
            Toast.makeText(this, "Alarmas reiniciadas a 0", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        // Registrar el BroadcastReceiver con el flag RECEIVER_EXPORTED o RECEIVER_NOT_EXPORTED
        val filter = IntentFilter(AlarmaWorker.ACTION_ALARMA_COMPLETADA)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Para Android 13 (API 33) o superior
            registerReceiver(alarmaCompletadaReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Para Android 12 (API 31) o superior
            registerReceiver(alarmaCompletadaReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            // Para versiones anteriores a Android 12
            registerReceiver(alarmaCompletadaReceiver, filter)
        }

        Log.d("MainActivity2", "onResume() - Iniciando actualizarUITask")
        val alarmas = alarmaManager.cargarAlarmas()
        actualizarUI(alarmas)
        handler.post(actualizarUITask)
    }

    override fun onPause() {
        super.onPause()
        // Desregistrar el BroadcastReceiver
        unregisterReceiver(alarmaCompletadaReceiver)

        // Detener la actualización de la UI
        Log.d("MainActivity2", "onPause() - Deteniendo actualizarUITask")
        handler.removeCallbacks(actualizarUITask)
    }

    //------ funcion actualizarUI -------
    private fun actualizarUI(alarmas: List<Alarma>) {
        val tiempoRestante = alarmas.sumOf { it.duracion } // Sumar la duración de todas las alarmas
        Log.d("MainActivity2", "actualizarUI() - Tiempo restante: $tiempoRestante, Alarmas pendientes: ${alarmas.size}")

        // Actualizar los TextView en la UI
        binding.tvTiempoRestante.text = "Tiempo restante: $tiempoRestante minutos"
        binding.tvAlarmasPendientes.text = "Alarmas pendientes: ${alarmas.size}"
    }

    //------ funcion programarAlarmas -------
    private fun programarAlarmas(context: Context, alarmas: List<Alarma>) {
        Log.d("MainActivity2", "programarAlarmas() - Iniciando")
        val workManager = WorkManager.getInstance(context)

        var delay = 0L
        alarmas.forEachIndexed { index, alarma ->
            val data = workDataOf(
                "duracion" to alarma.duracion,
                "descripcion" to alarma.descripcion,
                "sonido" to alarma.sonido
            )

            val workRequest = OneTimeWorkRequestBuilder<AlarmaWorker>()
                .setInputData(data)
                .setInitialDelay(delay, java.util.concurrent.TimeUnit.MINUTES)
                .build()

            Log.d("MainActivity2", "Encolando alarma: ${alarma.descripcion}, Duración: ${alarma.duracion}, Sonido: ${alarma.sonido}, Delay: $delay minutos")
            workManager.enqueue(workRequest)

            delay += alarma.duracion
        }
        Log.d("MainActivity2", "programarAlarmas() - Finalizado")
    }
}