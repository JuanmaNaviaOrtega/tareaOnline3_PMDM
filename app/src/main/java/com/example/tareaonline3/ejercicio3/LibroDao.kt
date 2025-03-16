package com.example.tareaonline3_ejercicio2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LibroDao {
    @Insert
    suspend fun insertItem(libro: Libro)

    @Query("SELECT * FROM libros")
    suspend fun getAllItems(): List<Libro>

    @Query("SELECT * FROM libros")
    suspend fun getAll(): List<Libro>

    @Insert
    suspend fun insert(libro: Libro)

    @Update
    suspend fun update(libro: Libro)

    @Delete
    suspend fun delete(libro: Libro)

    @Query("DELETE FROM libros")
    suspend fun deleteAll()
}