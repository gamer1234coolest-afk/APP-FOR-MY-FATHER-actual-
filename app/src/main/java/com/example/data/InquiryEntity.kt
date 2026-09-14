package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inquiries")
data class InquiryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val referenceCode: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val serviceNeeded: String,
    val timeline: String,
    val budget: String,
    val details: String,
    val preferredContact: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Submitted"
)
