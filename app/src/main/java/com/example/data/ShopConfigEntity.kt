package com.example.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "shop_config")
data class ShopConfigEntity(
    @PrimaryKey val id: Int = 1,
    val businessName: String = "Apex Enterprises",
    val founderName: String = "Soman Paliath",
    val founderRole: String = "Proprietor & Entrepreneur",
    val establishedYear: String = "2024",
    val phone: String = "87548 25880",
    val email: String = "apexentp2025@gmail.com",
    val address: String = "Shop no: B02, KOKO Chennai Food Street, 181/3, Veerathamman Kovil St, Jalladiampet, Medavakkam, Chennai, Tamil Nadu 600100",
    val hours: String = "Mon–Sat: 8:30 AM – 9:30 PM • Sun: 9:00 AM – 8:00 PM",
    val announcementText: String = "Welcome to Apex Enterprises! Fast Xerox, color prints, spiral binding, school & office stationery, mobile accessories, toys, and pan-India courier booking under one roof.",
    val isAnnouncementActive: Boolean = true,
    val announcementType: String = "INFO", // "INFO", "ALERT", "PROMO"
    val isAcceptingCustomWork: Boolean = true
)

@Dao
interface ShopConfigDao {
    @Query("SELECT * FROM shop_config WHERE id = 1")
    fun getShopConfig(): Flow<ShopConfigEntity?>

    @Query("SELECT * FROM shop_config WHERE id = 1")
    suspend fun getShopConfigSync(): ShopConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveShopConfig(config: ShopConfigEntity)
}
