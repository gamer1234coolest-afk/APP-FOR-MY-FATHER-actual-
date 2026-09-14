package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TestimonialDao {
    @Query("SELECT * FROM testimonials ORDER BY timestamp DESC")
    fun getAllTestimonials(): Flow<List<TestimonialEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestimonial(testimonial: TestimonialEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(testimonials: List<TestimonialEntity>)

    @Query("SELECT COUNT(*) FROM testimonials")
    suspend fun getCount(): Int

    @Query("DELETE FROM testimonials WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM testimonials")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM testimonials WHERE customerName LIKE '%Thomas%' OR comment LIKE '%walnut%'")
    suspend fun getLegacyCount(): Int
}
