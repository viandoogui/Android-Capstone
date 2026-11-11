package com.group4.group4androidcapstone.features.products.data

import android.util.Base64
import com.group4.group4androidcapstone.features.products.domain.Product

object ProductMapper {

    fun mapToDomain(dto: ProductDto): Product {
        return Product(
            productId = dto.productId,
            name = dto.name,
            productNumber = dto.productNumber,
            summary = dto.summary,
            thumbnailPhoto = Base64.decode(dto.thumbnailPhoto, Base64.DEFAULT),
            thumbnailPhotoFileName = dto.thumbnailPhotoFileName,
            warranty = dto.warranty,
            color = dto.color,
            listPrice = dto.listPrice
        )
    }

    fun mapToDto(domain: Product): ProductDto {
        return ProductDto(
            productId = domain.productId,
            name = domain.name,
            productNumber = domain.productNumber,
            summary = domain.summary,
            thumbnailPhoto = Base64.encodeToString(
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