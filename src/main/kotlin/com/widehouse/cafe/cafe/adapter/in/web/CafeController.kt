package com.widehouse.cafe.cafe.adapter.`in`.web

import com.widehouse.cafe.cafe.application.port.`in`.CafeCreateUseCase
import com.widehouse.cafe.cafe.application.port.`in`.CafeQueryUseCase
import com.widehouse.cafe.cafe.domain.Cafe
import com.widehouse.cafe.common.exception.AlreadyExistException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("cafe")
class CafeController(
    private val cafeQueryUseCase: CafeQueryUseCase,
    private val cafeCreateUseCase: CafeCreateUseCase
) {
    @PostMapping
    fun createCafe(@RequestBody cafe: Cafe): Mono<Cafe> {
        return cafeCreateUseCase.create(cafe)
    }

    @GetMapping("{id}")
    fun getCafe(@PathVariable id: String): Mono<Cafe> =
        cafeQueryUseCase.getCafe(id)

    @GetMapping(params = ["categoryId"])
    fun listCafeByCategory(@RequestParam categoryId: Long): Flux<Cafe> =
        cafeQueryUseCase.listByCategory(categoryId)

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler
    fun handle(ex: AlreadyExistException) {}
}
