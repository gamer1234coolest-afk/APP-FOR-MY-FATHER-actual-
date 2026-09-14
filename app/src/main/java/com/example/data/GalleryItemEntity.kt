package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.R
import com.example.model.GalleryItem

@Entity(tableName = "gallery_items")
data class GalleryItemEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val imageResName: String,
    val woodType: String,
    val finish: String,
    val dimensions: String,
    val year: String,
    val description: String,
    val clientHighlight: String,
    val price: String = "$1,800",
    val stockStatus: String = "In Stock", // "In Stock", "Made to Order", "Sold"
    val stockQuantity: Int = 1,
    val isFeatured: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toGalleryItem(): GalleryItem {
        val resId = when (imageResName) {
            "img_tech_showcase" -> R.drawable.img_tech_showcase
            "img_toys_showcase" -> R.drawable.img_toys_showcase
            "img_stationery_showcase" -> R.drawable.img_stationery_showcase
            "img_hero_store" -> R.drawable.img_hero_store
            "img_gallery_furniture" -> R.drawable.img_tech_showcase
            "img_gallery_cabinetry" -> R.drawable.img_toys_showcase
            "img_gallery_restoration" -> R.drawable.img_stationery_showcase
            "img_gallery_architectural" -> R.drawable.img_hero_store
            "img_gallery_slab" -> R.drawable.img_toys_showcase
            else -> R.drawable.img_tech_showcase
        }
        return GalleryItem(
            id = id,
            title = title,
            category = category,
            imageRes = resId,
            woodType = woodType,
            finish = finish,
            dimensions = dimensions,
            year = year,
            description = description,
            clientHighlight = clientHighlight,
            price = price,
            stockStatus = stockStatus,
            stockQuantity = stockQuantity
        )
    }

    companion object {
        fun fromGalleryItem(item: GalleryItem, imageResName: String = "img_gallery_furniture"): GalleryItemEntity {
            return GalleryItemEntity(
                id = item.id,
                title = item.title,
                category = item.category,
                imageResName = imageResName,
                woodType = item.woodType,
                finish = item.finish,
                dimensions = item.dimensions,
                year = item.year,
                description = item.description,
                clientHighlight = item.clientHighlight,
                price = item.price,
                stockStatus = item.stockStatus,
                stockQuantity = item.stockQuantity
            )
        }
    }
}
