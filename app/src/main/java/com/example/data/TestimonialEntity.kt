package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "testimonials")
data class TestimonialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerName: String,
    val location: String = "",
    val serviceReceived: String,
    val rating: Int, // 1 to 5
    val comment: String,
    val date: String,
    val verifiedProject: Boolean = true,
    val timestamp: Long = System.currentTimeMillis(),
    val isGoogleReview: Boolean = false,
    val source: String = "Direct Client",
    val reviewerBadge: String = "",
    val reviewerInitials: String = "",
    val ownerResponse: String = "",
    val likeCount: Int = 0,
    val reviewUrl: String = ""
)
