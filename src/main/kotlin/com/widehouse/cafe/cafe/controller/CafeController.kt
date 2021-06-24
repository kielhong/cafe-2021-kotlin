package com.widehouse.cafe.cafe.controller

import com.widehouse.cafe.cafe.model.Cafe
import com.widehouse.cafe.cafe.service.CafeService
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("cafe")
class CafeController(private val cafeService: CafeService) {
    @PostMapping
    fun createCafe(@RequestBody cafe: Cafe): Mono<Cafe> {
        return cafeService.create(cafe)
    }

    @GetMapping("{url}")
    fun getCafe(@PathVariable url: String): Mono<Cafe> = cafeService.getCafe(url)

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler
    fun handle(ex: DuplicateKeyException) {}
}
