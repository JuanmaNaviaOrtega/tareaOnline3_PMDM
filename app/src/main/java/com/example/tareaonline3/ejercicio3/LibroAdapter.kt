package tareaOnline3.ejercicio3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tareaonline3.R

class LibroAdapter(
    var libros: List<Libro>,
    private val onItemClick: (Libro) -> Unit,
    private val onItemLongClick: (Libro) -> Unit
) : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    //-------funcion onCreateViewHolder-------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.libro_item, parent, false)
        return LibroViewHolder(view)
    }

    //-------funcion onBindViewHolder-------
    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.bind(libro)
        holder.itemView.setOnClickListener { onItemClick(libro) }
        holder.itemView.setOnLongClickListener { onItemLongClick(libro); true }
    }

    //-------funcion getItemCount-------
    override fun getItemCount(): Int = libros.size

    class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloTextView: TextView = itemView.findViewById(R.id.tituloTextView)
        private val autorTextView: TextView = itemView.findViewById(R.id.autorTextView)
        private val fechaTextView: TextView = itemView.findViewById(R.id.fechaTextView)
        private val generoTextView: TextView = itemView.findViewById(R.id.generoTextView)
        private val enlaceTextView: TextView = itemView.findViewById(R.id.enlaceTextView)
        private val fotoImageView: ImageView = itemView.findViewById(R.id.fotoImageView)

        fun bind(libro: Libro) {
            tituloTextView.text = libro.titulo
            autorTextView.text = libro.autor
            fechaTextView.text = libro.fechaPublicacion
            generoTextView.text = libro.genero
            enlaceTextView.text = libro.enlace
            // Cargar la imagen usando Glide
            Glide.with(itemView.context)
                .load(libro.foto)
                .into(fotoImageView)
        }
    }
}