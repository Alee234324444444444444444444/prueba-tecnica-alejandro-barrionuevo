package com.example.pruebatecnicaab.mappers

import com.example.pruebatecnicaab.models.entities.Product
import com.example.pruebatecnicaab.models.requests.CreateProductRequest
import com.example.pruebatecnicaab.models.responses.ProductResponse
import org.springframework.stereotype.Component

//HEREDA DE BASEMAPPER E IMPLEMENTA TORESPONSE Y TORESPONSELIST
@Component
class ProductMapper: BaseMapper<Product, ProductResponse> {
    // CONVIERTE EL REQUEST A UNA ENTIDAD
    fun toEntity(request: CreateProductRequest): Product{
        return Product(
            name = request.name,
            price = request.price,
            stockQuantity = request.stockQuantity
        )
    }
    //CONVIERTE LA ENTIDAD A UN RESPONSE
    override fun toResponse(entity: Product): ProductResponse {
        return ProductResponse(
            id = entity.id,
            name = entity.name,
            price = entity.price,
            stockQuantity = entity.stockQuantity,
            createdAt = entity.createdAt
        )
    }

}