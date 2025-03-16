package com.example.tareaonline3_ejercicio2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros")
data class Libro(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val titulo: String,
    val autor: String,
    val enlace: String,
    val fechaPublicacion: String,
    val genero: String,
    val foto: String
)