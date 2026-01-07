package com.example.pruebatecnicaab.models.responses

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

// RESPONSE PARA LA RESPUESTA CUANDO SE CREA UN PRODCUTO
data class ProductResponse(
    val id: Long,
    val name: String,
    val price: BigDecimal,

    @JsonProperty("stock_quantity")
    val stockQuantity: Int,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
)
