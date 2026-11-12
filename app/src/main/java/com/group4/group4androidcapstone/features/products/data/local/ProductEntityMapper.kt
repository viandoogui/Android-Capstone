package com.group4.group4androidcapstone.features.products.data.local

import android.util.Base64
import com.group4.group4androidcapstone.features.products.data.ProductDto
import com.group4.group4androidcapstone.features.products.domain.Product

object ProductEntityMapper {
    fun toDomain(entity: ProductEntity): Product {
        return Product(
            productId = entity.productId,
            name = entity.name,
            productNumber = entity.productNumber,
            summary = entity.summary,
            thumbnailPhoto = Base64.encode(
                entity.thumbnailPhoto,
                Base64.DEFAULT
            ),
            thumbnailPhotoFileName = entity.thumbnailPhotoFileName,
            warranty = entity.warranty,
            color = entity.color,
            listPrice = entity.listPrice
        )

    }

    fun toEntity(domain: ProductDto): ProductEntity {
        return ProductEntity(
            productId = domain.productId,
            name = domain.name,
            productNumber = domain.productNumber,
            summary = domain.summary,
            thumbnailPhoto = Base64.decode(
                domain.thumbnailPhoto,
                Base64.DEFAULT
            ),
            thumbnailPhotoFileName = domain.thumbnailPhotoFileName,
            warranty = domain.warranty,
            color = domain.color,
            listPrice = domain.listPrice
        )
    }
}