package com.widehouse.cafe.common.controller

import com.widehouse.cafe.common.exception.DataNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException::class)
    fun notFound(ex: DataNotFoundException): ResponseEntity<String> {
        return ResponseEntity.notFound().build()
    }
}
