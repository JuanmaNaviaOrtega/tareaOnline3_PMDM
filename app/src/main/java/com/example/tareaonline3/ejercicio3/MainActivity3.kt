package tareaOnline3.ejercicio3
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tareaonline3.databinding.ActivityMain3Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding
    private lateinit var libroAdapter: LibroAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa la base de datos
        db = AppDatabase.getInstance(this)

        // Configura el RecyclerView
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        libroAdapter = LibroAdapter(
            emptyList(),
            { libro -> abrirEnlace(libro.enlace) },
            { libro -> editarLibro(libro) }
        )
        recyclerView.adapter = libroAdapter

        // Verificar si la base de datos está vacía y cargar libros predeterminados
        lifecycleScope.launch {
            val libros = db.libroDao().getAll()
            if (libros.isEmpty()) {
                // Insertar libros predeterminados
                insertarLibrosPredeterminados()
            }
            // Actualizar el RecyclerView
            actualizarRecyclerView()
        }

        // Configura el FloatingActionButton
        val fab = binding.fab
        fab.setOnClickListener { agregarLibro() }

        // Configura el ItemTouchHelper para el gesto de deslizar
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val libro = libroAdapter.libros[viewHolder.adapterPosition]
                lifecycleScope.launch {
                    db.libroDao().delete(libro)
                    actualizarRecyclerView()
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    //-------funcion insertarLibrosPredeterminados-------
    private suspend fun insertarLibrosPredeterminados() {
        val libro1 = Libro(
            titulo = "El Señor de los Anillos",
            autor = "J.R.R. Tolkien",
            enlace = "https://www.amazon.es/Anillos-Comunidad-Anillo-Biblioteca-Tolkien/dp/8445013955/ref=sr_1_2?crid=RNSBHGJQD5EG&dib=eyJ2IjoiMSJ9.3uR-QAw0lyOTcN850jUbTGRBdaHKC5-qOf3UZoRylsSBPaQ8z4n2Nfm0yZr50b212LysCCmoxw-C2pQOvBoAd8r7o1rQPct1B-8-ECZQNIicnDZrgL6irSOF2nB15wK5tA7Asb8_08jA0Nmgqlzp_-A4pEKJpd1JtQiZKVA9Oy57W4tL9dh_xdLOD_FDkihh791a__x_gCCY2Ld3GTlyfNpfGV4eSEolZzUE-A3uL9E.KhCLgJPc4Up2L-DUYt-KO6FCecQSRktmI_uZD52lTbo&dib_tag=se&keywords=el+se%C3%B1or+de+los+anillos+libro&qid=1741789580&s=books&sprefix=el+se%C3%B1%2Cstripbooks%2C130&sr=1-2",
            fechaPublicacion = "29 de julio de 1954",
            genero = "Fantasía",
            foto = "https://m.media-amazon.com/images/I/81DjOU3MIvL._SY522_.jpg"
        )
        db.libroDao().insert(libro1)

        val libro2 = Libro(
            titulo = "Harry Potter y la piedra filosofal",
            autor = "J.K. Rowling",
            enlace = "https://www.amazon.es/Harry-Potter-Piedra-Filosofal-Rowling/dp/8478884459/ref=asc_df_8478884459?mcid=922f2cbca05a3ba79e1cf52b1d5d2580&tag=googshopes-21&linkCode=df0&hvadid=699705985593&hvpos=&hvnetw=g&hvrand=6268039674515483890&hvpone=&hvptwo=&hvqmt=&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9216502&hvtargid=pla-564364367728&psc=1&gad_source=1",
            fechaPublicacion = " 25 marzo 1999",
            genero = " mágico",
            foto = "https://m.media-amazon.com/images/I/91R1AixEiLL._SY522_.jpg"
        )
        db.libroDao().insert(libro2)
    }

    //-------funcion abrirEnlace-------
    private fun abrirEnlace(enlace: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(enlace))
        startActivity(intent)
    }

    //-------funcion agregarLibro-------
    private fun agregarLibro() {
        val dialog = LibroDialogFragment(db = db, onLibroGuardado = {
            // Actualiza el RecyclerView cuando se guarda un libro
            actualizarRecyclerView()
        })
        dialog.show(supportFragmentManager, "LibroDialogFragment")
    }
 //------funcion editarLibro-------
    private fun editarLibro(libro: Libro) {
        val dialog = LibroDialogFragment(libro = libro, db = db, onLibroGuardado = {
            // Actualiza el RecyclerView cuando se guarda un libro
            actualizarRecyclerView()
        })
        dialog.show(supportFragmentManager, "LibroDialogFragment")
    }

    //-------funcion actualizarRecyclerView-------
    private fun actualizarRecyclerView() {
        lifecycleScope.launch {
            val libros = db.libroDao().getAll()
            libroAdapter.libros = libros
            libroAdapter.notifyDataSetChanged()
        }
    }
}