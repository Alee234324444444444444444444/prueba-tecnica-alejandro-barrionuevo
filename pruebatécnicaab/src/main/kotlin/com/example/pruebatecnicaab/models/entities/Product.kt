package com.example.pruebatecnicaab.models.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products")

data class Product(
    @Column(nullable = false, name = "product_name")
    var name: String,

    @Column(nullable = false, name = "unit_price")
    val price: BigDecimal,

    @Column(nullable = false, name = "stock_quantity")
    val stockQuantity: Int = 0,


    ) : BaseEntity()
