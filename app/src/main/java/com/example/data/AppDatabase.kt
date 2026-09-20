package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        InquiryEntity::class,
        TestimonialEntity::class,
        GalleryItemEntity::class,
        ShopConfigEntity::class,
        ServiceItemEntity::class,
        DonationEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inquiryDao(): InquiryDao
    abstract fun testimonialDao(): TestimonialDao
    abstract fun galleryDao(): GalleryDao
    abstract fun shopConfigDao(): ShopConfigDao
    abstract fun serviceDao(): ServiceDao
    abstract fun donationDao(): DonationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "craft_business_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
