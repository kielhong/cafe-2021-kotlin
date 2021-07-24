package com.widehouse.cafe.cafe.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class CafeTest : StringSpec({
    "construct Cafe" {
        val cafe = Cafe("test", "name", "desc", theme = "movie")
        cafe.id shouldBe "test"
        cafe.name shouldBe "name"
        cafe.description shouldBe "desc"
        cafe.theme shouldBe "movie"
    }
})
