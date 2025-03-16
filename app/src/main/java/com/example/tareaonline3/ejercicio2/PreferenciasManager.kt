package tareaOnline3.ejercicio2

import android.content.Context


class PreferenciasManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE)

    // Método para guardar el nombre del archivo
    fun guardarNombreArchivo(nombre: String) {
        sharedPreferences.edit().putString("nombre_archivo", nombre).apply()
    }

    // Método para obtener el nombre del archivo
    fun obtenerNombreArchivo(): String {
        return sharedPreferences.getString("nombre_archivo", "alarmas.txt") ?: "alarmas.txt"
    }

    // Método para guardar si se debe reproducir el sonido
    fun guardarReproducirSonido(habilitado: Boolean) {
        sharedPreferences.edit().putBoolean("reproducir_sonido", habilitado).apply()
    }

    // Método para obtener si se debe reproducir el sonido
    fun obtenerReproducirSonido(): Boolean {
        return sharedPreferences.getBoolean("reproducir_sonido", true)
    }
}