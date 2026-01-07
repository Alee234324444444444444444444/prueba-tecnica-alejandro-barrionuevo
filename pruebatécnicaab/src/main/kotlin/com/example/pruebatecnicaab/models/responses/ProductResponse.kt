package com.example.pruebatecnicaab.models.responses

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: BigDecimal,

    @JsonProperty("stock_quantity")
    val stockQuantity: Int,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
)
