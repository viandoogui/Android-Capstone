package com.group4.group4androidcapstone.core.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.group4.group4androidcapstone.features.products.data.local.ProductDao
import com.group4.group4androidcapstone.features.products.data.local.ProductDetailsEntity
import com.group4.group4androidcapstone.features.products.data.local.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        ProductDetailsEntity::class,
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = [
    ]
)

@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
