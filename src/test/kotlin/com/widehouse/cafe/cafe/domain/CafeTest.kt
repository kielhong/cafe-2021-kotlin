package com.widehouse.cafe.cafe.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class CafeTest : StringSpec({
    "construct Cafe" {
        val cafe = Cafe("test", "name", "desc", 1L)
        cafe.id shouldBe "test"
        cafe.name shouldBe "name"
        cafe.description shouldBe "desc"
        cafe.categoryId shouldBe 1L
    }
})
