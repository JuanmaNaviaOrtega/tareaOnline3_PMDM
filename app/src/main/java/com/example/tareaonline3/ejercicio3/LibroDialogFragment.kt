package tareaOnline3.ejercicio3

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.tareaonline3.R
import kotlinx.coroutines.launch

class LibroDialogFragment(
    private val libro: Libro? = null,
    private val db: AppDatabase,
    private val onLibroGuardado: () -> Unit
) : DialogFragment() {

    //-------funcion onCreateDialog-------
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_libro, null)

        val tituloEditText = view.findViewById<EditText>(R.id.tituloEditText)
        val autorEditText = view.findViewById<EditText>(R.id.autorEditText)
        val enlaceEditText = view.findViewById<EditText>(R.id.enlaceEditText)
        val fechaEditText = view.findViewById<EditText>(R.id.fechaEditText)
        val generoEditText = view.findViewById<EditText>(R.id.generoEditText)
        val fotoEditText = view.findViewById<EditText>(R.id.fotoEditText)

        libro?.let {
            tituloEditText.setText(it.titulo)
            autorEditText.setText(it.autor)
            enlaceEditText.setText(it.enlace)
            fechaEditText.setText(it.fechaPublicacion)
            generoEditText.setText(it.genero)
            fotoEditText.setText(it.foto)
        }

        builder.setView(view)
            .setTitle(if (libro == null) "Agregar Libro" else "Editar Libro")
            .setPositiveButton("Guardar") { dialog, id ->
                val titulo = tituloEditText.text.toString()
                val autor = autorEditText.text.toString()
                val enlace = enlaceEditText.text.toString()
                val fecha = fechaEditText.text.toString()
                val genero = generoEditText.text.toString()
                val foto = fotoEditText.text.toString()

                if (titulo.isNotEmpty() && autor.isNotEmpty() && enlace.isNotEmpty() && fecha.isNotEmpty() && genero.isNotEmpty() && foto.isNotEmpty()) {
                    val nuevoLibro = Libro(
                        titulo = titulo,
                        autor = autor,
                        enlace = enlace,
                        fechaPublicacion = fecha,
                        genero = genero,
                        foto = foto
                    )
                    lifecycleScope.launch {
                        if (libro == null) {
                            db.libroDao().insert(nuevoLibro)
                        } else {
                            nuevoLibro.id = libro.id
                            db.libroDao().update(nuevoLibro)
                        }
                        onLibroGuardado()
                    }
                } else {
                    Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar") { dialog, id ->
                dialog.cancel()
            }
        return builder.create()
    }
}