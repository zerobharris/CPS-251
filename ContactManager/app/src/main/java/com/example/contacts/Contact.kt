package com.example.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey

//this cell defines the info for each contact
@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phone: String
)