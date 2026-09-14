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
    val businessName: String = "Beacon Tech, Toys & Stationery",
    val founderName: String = "Alex Mercer",
    val founderRole: String = "Founder & Lead Curator",
    val establishedYear: String = "2012",
    val phone: String = "(555) 482-9301",
    val email: String = "hello@beacontechtoys.com",
    val address: String = "142 Main Street, Arts & Tech District",
    val hours: String = "Mon–Sat: 9:00 AM – 7:00 PM • Sun: 10:00 AM – 4:00 PM",
    val announcementText: String = "New arrivals: Keyflow 75% mechanical keyboards, STEM robot builder kits & Japanese pastel gel pens! Complimentary in-store gift wrapping.",
    val isAnnouncementActive: Boolean = true,
    val announcementType: String = "INFO", // "INFO", "ALERT", "PROMO"
    val isAcceptingCustomWork: Boolean = true
)

@Dao
interface ShopConfigDao {
    @Query("SELECT * FROM shop_config WHERE id = 1")
    fun getShopConfig(): Flow<ShopConfigEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveShopConfig(config: ShopConfigEntity)
}
