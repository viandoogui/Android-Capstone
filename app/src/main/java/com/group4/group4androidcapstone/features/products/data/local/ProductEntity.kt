package com.group4.group4androidcapstone.features.products.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val productNumber: String,
    val summary: String?,
    val thumbnailPhoto: ByteArray,
    val thumbnailPhotoFileName: String,
    val warranty: String?,
    val color: String?,
    val listPrice: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductEntity

        if (productId != other.productId) return false
        if (listPrice != other.listPrice) return false
        if (name != other.name) return false
        if (productNumber != other.productNumber) return false
        if (summary != other.summary) return false
        if (!thumbnailPhoto.contentEquals(other.thumbnailPhoto)) return false
        if (thumbnailPhotoFileName != other.thumbnailPhotoFileName) return false
        if (warranty != other.warranty) return false
        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        var result = productId
        result = 31 * result + listPrice.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + productNumber.hashCode()
        result = 31 * result + (summary?.hashCode() ?: 0)
        result = 31 * result + thumbnailPhoto.contentHashCode()
        result = 31 * result + thumbnailPhotoFileName.hashCode()
        result = 31 * result + (warranty?.hashCode() ?: 0)
        result = 31 * result + (color?.hashCode() ?: 0)
        return result
    }
}