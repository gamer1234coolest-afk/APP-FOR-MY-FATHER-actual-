package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryDao {
    @Query("SELECT * FROM gallery_items ORDER BY timestamp DESC")
    fun getAllItems(): Flow<List<GalleryItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: GalleryItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<GalleryItemEntity>)

    @Update
    suspend fun updateItem(item: GalleryItemEntity)

    @Query("DELETE FROM gallery_items WHERE id = :id")
    suspend fun deleteItem(id: String)

    @Query("SELECT COUNT(*) FROM gallery_items")
    suspend fun getCount(): Int

    @Query("UPDATE gallery_items SET stockStatus = :status WHERE id = :id")
    suspend fun updateStockStatus(id: String, status: String)

    @Query("UPDATE gallery_items SET price = :price, stockQuantity = :quantity WHERE id = :id")
    suspend fun updatePriceAndQuantity(id: String, price: String, quantity: Int)

    @Query("DELETE FROM gallery_items")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM gallery_items WHERE id LIKE 'gal_%'")
    suspend fun getLegacyCount(): Int
}
