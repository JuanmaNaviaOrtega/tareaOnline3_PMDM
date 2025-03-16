package tareaOnline3.ejercicio2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tareaonline3.R

class AlarmaWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val ACTION_ALARMA_COMPLETADA = "tareaOnline3.ejercicio2.ALARMA_COMPLETADA"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    //-------funcion doWork-------
    override fun doWork(): Result {
        val duracion = inputData.getInt("duracion", 0)
        val descripcion = inputData.getString("descripcion") ?: ""
        val sonido = inputData.getString("sonido") ?: ""

        Log.d("AlarmaWorker", "Alarma programada: $descripcion, Sonido: $sonido, Duración: $duracion minutos")

        // Simular la espera de la alarma
        Log.d("AlarmaWorker", "Iniciando espera de $duracion minutos")
        Thread.sleep(duracion * 60 * 1000L)
        Log.d("AlarmaWorker", "Fin de la espera")

        // Mostrar notificación y reproducir sonido
        Log.d("AlarmaWorker", "Mostrando notificación: $descripcion")
        mostrarNotificacion(applicationContext, descripcion)

        Log.d("AlarmaWorker", "Reproduciendo sonido: $sonido")
        // Pasar descripcion como parámetro al método reproducirSonido
        reproducirSonido(applicationContext, sonido, descripcion)

        // Eliminar la alarma que ya ha sonado
        Log.d("AlarmaWorker", "Eliminando alarma: $descripcion")
        eliminarAlarma(applicationContext, duracion, descripcion, sonido)

        // Enviar broadcast de alarma completada
        enviarBroadcastAlarmaCompletada(applicationContext)

        Log.d("AlarmaWorker", "Alarma completada")
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    //-------funcion mostrar notificacion-------
    private fun mostrarNotificacion(context: Context, descripcion: String) {
        Log.d("AlarmaWorker", "mostrarNotificacion() - Iniciando")
        val channelId = "alarmas_channel"
        val channelName = "Alarmas"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(context, MainActivity2::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("¡Alarma!")
            .setContentText(descripcion)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
        Log.d("AlarmaWorker", "mostrarNotificacion() - Notificación mostrada")
    }

    // --------Método reproducirSonido actualizado para aceptar descripcion como parámetro-------
    private fun reproducirSonido(context: Context, sonido: String, descripcion: String) {
        Log.d("AlarmaWorker", "reproducirSonido() - Iniciando")
        val resourceId = context.resources.getIdentifier(sonido, "raw", context.packageName)

        if (resourceId != 0) {
            val mediaPlayer = MediaPlayer.create(context, resourceId)
            mediaPlayer.start()

            mediaPlayer.setOnCompletionListener {
                mediaPlayer.release()
                Log.d("AlarmaWorker", "reproducirSonido() - Sonido finalizado")

                // Mostrar un Toast en el hilo principal
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Alarma completada: $descripcion", Toast.LENGTH_LONG).show()
                }
            }
            Log.d("AlarmaWorker", "reproducirSonido() - Sonido iniciado")
        } else {
            Log.e("AlarmaWorker", "reproducirSonido() - Archivo de sonido no encontrado: $sonido")
        }
    }

    //-------funcion eliminar alarma-------
    private fun eliminarAlarma(context: Context, duracion: Int, descripcion: String, sonido: String) {
        Log.d("AlarmaWorker", "eliminarAlarma() - Iniciando")
        val alarmaManager = AlarmaManager(context)
        val alarmas = alarmaManager.cargarAlarmas().toMutableList()

        // Buscar y eliminar la alarma que ha sonado
        val alarmaAEliminar = alarmas.find { it.duracion == duracion && it.descripcion == descripcion && it.sonido == sonido }
        if (alarmaAEliminar != null) {
            alarmas.remove(alarmaAEliminar)
            alarmaManager.guardarAlarmas(alarmas)
            Log.d("AlarmaWorker", "eliminarAlarma() - Alarma eliminada: $alarmaAEliminar")
        } else {
            Log.d("AlarmaWorker", "eliminarAlarma() - No se encontró la alarma para eliminar")
        }
    }

    //-------funcion enviarBroadcastAlarmaCompletada-------
    private fun enviarBroadcastAlarmaCompletada(context: Context) {
        val intent = Intent(ACTION_ALARMA_COMPLETADA)
        context.sendBroadcast(intent)
        Log.d("AlarmaWorker", "enviarBroadcastAlarmaCompletada() - Broadcast enviado")
    }
}