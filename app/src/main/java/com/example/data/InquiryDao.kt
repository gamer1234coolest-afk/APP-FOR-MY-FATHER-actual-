package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InquiryDao {
    @Query("SELECT * FROM inquiries ORDER BY timestamp DESC")
    fun getAllInquiries(): Flow<List<InquiryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInquiry(inquiry: InquiryEntity): Long

    @Query("DELETE FROM inquiries WHERE id = :id")
    suspend fun deleteInquiryById(id: Long)

    @Query("UPDATE inquiries SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)
}
