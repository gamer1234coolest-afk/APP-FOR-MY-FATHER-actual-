package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donations")
data class DonationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val donorName: String,
    val amountDollars: Double,
    val tierTitle: String,
    val frequency: String, // "One-Time", "Monthly Patron"
    val note: String = "",
    val paymentMethod: String = "Credit Card",
    val isAnonymous: Boolean = false,
    val referenceCode: String,
    val timestamp: Long = System.currentTimeMillis()
)
