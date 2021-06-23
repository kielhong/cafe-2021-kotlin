package com.widehouse.cafe.cafe.model

import org.assertj.core.api.BDDAssertions
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class CafeTest {
    @Test
    fun getUrl() {
        val cafe = Cafe("test")
        // then
        then(cafe.url).isEqualTo("test")
    }
}