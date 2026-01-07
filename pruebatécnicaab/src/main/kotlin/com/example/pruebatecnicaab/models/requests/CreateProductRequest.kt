package com.example.pruebatecnicaab.models.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class CreateProductRequest(
    val name: String,
    val price: BigDecimal,

    @JsonProperty("stock_quantity")
    val stockQuantity: Int,
)
