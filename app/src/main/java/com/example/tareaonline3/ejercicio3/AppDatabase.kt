package tareaOnline3.ejercicio3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch

@Database(entities = [Libro::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun libroDao(): LibroDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "libros.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}