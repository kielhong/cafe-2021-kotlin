package com.widehouse.cafe.controller

import com.widehouse.cafe.domain.Cafe
import com.widehouse.cafe.service.CafeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("cafe")
class CafeController(private val cafeService: CafeService) {
    @GetMapping("{url}")
    fun getCafe(@PathVariable url: String): Mono<Cafe> {
        return cafeService.getCafe(url)
    }
}
