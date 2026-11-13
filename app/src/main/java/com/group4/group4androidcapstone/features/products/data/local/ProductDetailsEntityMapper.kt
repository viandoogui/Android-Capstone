package com.group4.group4androidcapstone.features.products.data.local

import com.group4.group4androidcapstone.features.products.data.ProductDetailsDto
import com.group4.group4androidcapstone.features.products.domain.ProductDetails

object ProductDetailsEntityMapper {
    fun toDomain(entity: ProductDetailsEntity): ProductDetails {
        return ProductDetails(
            productId = entity.productId,
            productName = entity.productName,
            productNumber = entity.productNumber,
            summary = entity.summary,
            photo = entity.photo,
            photoFileName = entity.photoFileName,
            color = entity.color,
            listPrice = entity.listPrice,
            stockLevel = entity.stockLevel,
            productLine = entity.productLine,
            productModelId = entity.productModelId,
            productModelName = entity.productModelName,
            wheelDescription = entity.wheelDescription,
            saddleDescription = entity.saddleDescription,
            pedalDescription = entity.pedalDescription,
            riderExperience = entity.riderExperience,
            manufacturer = entity.manufacturer,
            bikeFrame = entity.bikeFrame,
            crankset = entity.crankset,
            material = entity.material,
            style = entity.style,
            warrantyPeriod = entity.warrantyPeriod,
            warrantyDescription = entity.warrantyDescription,
            maintenanceDescription = entity.maintenanceDescription,
            numberOfSteps = entity.numberOfSteps,
            setupHours = entity.setupHours,
            machineHours = entity.machineHours,
            laborHours = entity.laborHours,
            lotSize = entity.lotSize
        )
    }

    fun toEntity(domain: ProductDetailsDto): ProductDetailsEntity {
        return ProductDetailsEntity(
            productId = domain.productId,
            productName = domain.productName,
            productNumber = domain.productNumber,
            summary = domain.summary,
            photo = domain.photo,
            photoFileName = domain.photoFileName,
            color = domain.color,
            listPrice = domain.listPrice,
            stockLevel = domain.stockLevel,
            productLine = domain.productLine,
            productModelId = domain.productModelId,
            productModelName = domain.productModelName,
            wheelDescription = domain.wheelDescription,
            saddleDescription = domain.saddleDescription,
            pedalDescription = domain.pedalDescription,
            riderExperience = domain.riderExperience,
            manufacturer = domain.manufacturer,
            bikeFrame = domain.bikeFrame,
            crankset = domain.crankset,
            material = domain.material,
            style = domain.style,
            warrantyPeriod = domain.warrantyPeriod,
            warrantyDescription = domain.warrantyDescription,
            maintenanceDescription = domain.maintenanceDescription,
            numberOfSteps = domain.numberOfSteps,
            setupHours = domain.setupHours,
            machineHours = domain.machineHours,
            laborHours = domain.laborHours,
            lotSize = domain.lotSize
        )
    }
}