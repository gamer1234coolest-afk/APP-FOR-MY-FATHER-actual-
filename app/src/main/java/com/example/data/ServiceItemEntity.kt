package com.example.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.example.R
import com.example.model.ServiceItem
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "service_items")
data class ServiceItemEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val description: String,
    val startingPrice: String,
    val typicalLeadTime: String,
    val keyMaterials: String, // comma-separated
    val processSteps: String, // newline-separated
    val sampleImageResName: String? = null,
    val isAvailable: Boolean = true,
    val orderIndex: Int = 0
) {
    fun toServiceItem(): ServiceItem {
        val imageRes = when (sampleImageResName) {
            "img_tech_showcase" -> R.drawable.img_tech_showcase
            "img_toys_showcase" -> R.drawable.img_toys_showcase
            "img_stationery_showcase" -> R.drawable.img_stationery_showcase
            "img_hero_store" -> R.drawable.img_hero_store
            "img_gallery_furniture" -> R.drawable.img_tech_showcase
            "img_gallery_cabinetry" -> R.drawable.img_toys_showcase
            "img_gallery_restoration" -> R.drawable.img_stationery_showcase
            "img_gallery_architectural" -> R.drawable.img_hero_store
            else -> null
        }
        return ServiceItem(
            id = id,
            title = title,
            category = category,
            summary = summary,
            description = description,
            startingPrice = startingPrice,
            typicalLeadTime = typicalLeadTime,
            keyMaterials = keyMaterials.split(",").map { it.trim() }.filter { it.isNotEmpty() },
            processSteps = processSteps.split("\n").map { it.trim() }.filter { it.isNotEmpty() },
            sampleImageRes = imageRes
        )
    }

    companion object {
        fun fromServiceItem(item: ServiceItem, sampleImageResName: String? = null, orderIndex: Int = 0): ServiceItemEntity {
            return ServiceItemEntity(
                id = item.id,
                title = item.title,
                category = item.category,
                summary = item.summary,
                description = item.description,
                startingPrice = item.startingPrice,
                typicalLeadTime = item.typicalLeadTime,
                keyMaterials = item.keyMaterials.joinToString(", "),
                processSteps = item.processSteps.joinToString("\n"),
                sampleImageResName = sampleImageResName,
                isAvailable = true,
                orderIndex = orderIndex
            )
        }
    }
}

@Dao
interface ServiceDao {
    @Query("SELECT * FROM service_items ORDER BY orderIndex ASC")
    fun getAllServices(): Flow<List<ServiceItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: ServiceItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(services: List<ServiceItemEntity>)

    @Update
    suspend fun updateService(service: ServiceItemEntity)

    @Query("DELETE FROM service_items WHERE id = :id")
    suspend fun deleteService(id: String)

    @Query("SELECT COUNT(*) FROM service_items")
    suspend fun getCount(): Int

    @Query("UPDATE service_items SET startingPrice = :price, typicalLeadTime = :leadTime WHERE id = :id")
    suspend fun updatePriceAndLeadTime(id: String, price: String, leadTime: String)

    @Query("DELETE FROM service_items")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM service_items WHERE id LIKE 'srv_furniture%'")
    suspend fun getLegacyCount(): Int
}
