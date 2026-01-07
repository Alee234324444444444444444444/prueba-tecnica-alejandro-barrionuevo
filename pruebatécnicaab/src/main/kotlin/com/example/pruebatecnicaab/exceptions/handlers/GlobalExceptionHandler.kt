package com.example.pruebatecnicaab.exceptions.handlers

import com.example.pruebatecnicaab.exceptions.ProductAlreadyExistsException
import com.example.pruebatecnicaab.exceptions.InvalidProductPriceException
import com.example.pruebatecnicaab.exceptions.StockNotAvailableException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ProductAlreadyExistsException::class)
    fun handleProductExists(ex: ProductAlreadyExistsException): ResponseEntity<Map<String, String>> =
        ResponseEntity(mapOf("error" to ex.message.orEmpty()), HttpStatus.CONFLICT)

    @ExceptionHandler(InvalidProductPriceException::class)
    fun handleInvalidPrice(ex: InvalidProductPriceException): ResponseEntity<Map<String, String>> =
        ResponseEntity(mapOf("error" to ex.message.orEmpty()), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(StockNotAvailableException::class)
    fun handleStockNotAvailable(ex: StockNotAvailableException): ResponseEntity<Map<String, String>> =
        ResponseEntity(mapOf("error" to ex.message.orEmpty()), HttpStatus.BAD_REQUEST)
}
