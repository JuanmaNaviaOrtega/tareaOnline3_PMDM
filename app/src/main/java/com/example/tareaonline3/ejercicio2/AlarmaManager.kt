package tareaOnline3.ejercicio2

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.File

class AlarmaManager(private val context: Context) {

    private val archivoAlarmas = File(context.getExternalFilesDir(null), "alarmas.txt")

    //-------funcion Guardar alarmas en el archivo-------
    fun guardarAlarmas(alarmas: List<Alarma>) {
        try {
            val lineas = alarmas.joinToString("\n") { "${it.duracion},${it.descripcion},${it.sonido}" }
            archivoAlarmas.writeText(lineas)
            Log.d("AlarmaManager", "guardarAlarmas() - Alarmas guardadas: $lineas")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al guardar las alarmas", Toast.LENGTH_SHORT).show()
            Log.e("AlarmaManager", "guardarAlarmas() - Error al guardar las alarmas", e)
        }
    }

    // --------Funcion para cargar las alarmas desde el archivo-------
    fun cargarAlarmas(): List<Alarma> {
        Log.d("AlarmaManager", "cargarAlarmas() - Iniciando")
        return try {
            if (archivoAlarmas.exists()) {
                Log.d("AlarmaManager", "cargarAlarmas() - Archivo encontrado")
                val lineas = archivoAlarmas.readLines()
                Log.d("AlarmaManager", "cargarAlarmas() - Lineas leidas: ${lineas.size}")
                lineas.mapNotNull { linea ->
                    try {
                        val partes = linea.split(",")
                        if (partes.size == 3) {
                            val alarma = Alarma(partes[0].toInt(), partes[1], partes[2])
                            Log.d("AlarmaManager", "cargarAlarmas() - Alarma cargada: $alarma")
                            alarma
                        } else {
                            Log.e("AlarmaManager", "cargarAlarmas() - Formato de línea incorrecto: $linea")
                            null // Ignorar líneas con formato incorrecto
                        }
                    } catch (e: NumberFormatException) {
                        Log.e("AlarmaManager", "cargarAlarmas() - Error al convertir a número: $linea", e)
                        null // Ignorar líneas con errores de formato
                    }
                }
            } else {
                Log.w("AlarmaManager", "cargarAlarmas() - Archivo de alarmas no encontrado")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("AlarmaManager", "cargarAlarmas() - Error al cargar las alarmas", e)
            Toast.makeText(context, "Error al cargar las alarmas", Toast.LENGTH_SHORT).show()
            emptyList()
        } finally {
            Log.d("AlarmaManager", "cargarAlarmas() - Finalizado")
        }
    }

    // ------- funcion para eliminar la alarma -------
    fun reiniciarAlarmas() {
        if (archivoAlarmas.exists()) {
            archivoAlarmas.delete()
            Log.d("AlarmaManager", "reiniciarAlarmas() - Archivo alarmas.txt eliminado")
        }
    }
}