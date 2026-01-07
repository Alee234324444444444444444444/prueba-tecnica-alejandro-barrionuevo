package com.example.pruebatecnicaab.repositories

import com.example.pruebatecnicaab.models.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun existsByNameIgnoreCase(name: String): Boolean
}