package com.group4.group4androidcapstone.features.products.data

import com.group4.group4androidcapstone.features.products.domain.ProductDetails

object ProductDetailsMapper {

    fun mapToDomain(dto: ProductDetailsDto): ProductDetails {
        return ProductDetails(
            productId = dto.productId,
            productName = dto.productName,
            productNumber = dto.productNumber,
            summary = dto.summary,
            photo = dto.photo,
            photoFileName = dto.photoFileName,
            color = dto.color,
            listPrice = dto.listPrice,
            stockLevel = dto.stockLevel,
            productLine = dto.productLine,
            productModelId = dto.productModelId,
            productModelName = dto.productModelName,
            wheelDescription = dto.wheelDescription,
            saddleDescription = dto.saddleDescription,
            pedalDescription = dto.pedalDescription,
            riderExperience = dto.riderExperience,
            manufacturer = dto.manufacturer,
            bikeFrame = dto.bikeFrame,
            crankset = dto.crankset,
            material = dto.material,
            style = dto.style,
            warrantyPeriod = dto.warrantyPeriod,
            warrantyDescription = dto.warrantyDescription,
            maintenanceDescription = dto.maintenanceDescription,
            numberOfSteps = dto.numberOfSteps,
            setupHours = dto.setupHours,
            machineHours = dto.machineHours,
            laborHours = dto.laborHours,
            lotSize = dto.lotSize
        )
    }

    fun mapToDto(domain: ProductDetails): ProductDetailsDto {
        return ProductDetailsDto(
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