package com.example.pruebatecnicaab.services

import com.example.pruebatecnicaab.mappers.ProductMapper
import com.example.pruebatecnicaab.models.requests.CreateProductRequest
import com.example.pruebatecnicaab.models.responses.ProductResponse
import com.example.pruebatecnicaab.repositories.ProductRepository
import com.example.pruebatecnicaab.exceptions.ProductAlreadyExistsException
import com.example.pruebatecnicaab.exceptions.InvalidProductPriceException
import com.example.pruebatecnicaab.exceptions.StockNotAvailableException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) {
    
    // SERVICIO --> FUNCION PARA CREAR EL PRODCUTO CON LOGICA DE NEGOCIO
    fun createProduct(request: CreateProductRequest): ProductResponse {
        val name = request.name.trim()

        if (productRepository.existsByNameIgnoreCase(name)) {
            throw ProductAlreadyExistsException("Product with name '$name' already exists.")
        }

        if (request.price <= BigDecimal.ZERO) {
            throw InvalidProductPriceException("Price must be greater than 0.")
        }

        if (request.stockQuantity <= 0) {
            throw StockNotAvailableException("Stock is not available.")
        }

        val entity = productMapper.toEntity(request).apply { this.name = name }
        val saved = productRepository.save(entity)
        return productMapper.toResponse(saved)
    }

    //FUNCION PARA FILTRAR PRODCUTOS
    fun findAllProducts(): List<ProductResponse> =
        productMapper.toResponseList(productRepository.findAll())
}
