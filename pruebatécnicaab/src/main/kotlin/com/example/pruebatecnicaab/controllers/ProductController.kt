package com.example.pruebatecnicaab.controllers

import com.example.pruebatecnicaab.constants.Routes
import com.example.pruebatecnicaab.models.requests.CreateProductRequest
import com.example.pruebatecnicaab.models.responses.ProductResponse
import com.example.pruebatecnicaab.services.ProductService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Routes.PRODUCTS)
class ProductController(
    private val productService: ProductService
) {
    //API PARACREAR PRODUCTOS
    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest): ProductResponse =
        productService.createProduct(request)

    //API PARA FILTRAR TODOS LOS PRODUCTOS
    @GetMapping
    fun findAllProducts(): List<ProductResponse> =
        productService.findAllProducts()
}