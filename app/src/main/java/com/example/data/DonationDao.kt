package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DonationDao {
    @Query("SELECT * FROM donations ORDER BY timestamp DESC")
    fun getAllDonations(): Flow<List<DonationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDonation(donation: DonationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(donations: List<DonationEntity>)

    @Query("SELECT COUNT(*) FROM donations")
    suspend fun getCount(): Int

    @Query("SELECT SUM(amountDollars) FROM donations")
    suspend fun getTotalRaised(): Double?

    @Query("DELETE FROM donations WHERE id = :id")
    suspend fun deleteDonation(id: Long)

    @Query("DELETE FROM donations")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM donations WHERE note LIKE '%Thomas%' OR note LIKE '%timber%'")
    suspend fun getLegacyCount(): Int
}
