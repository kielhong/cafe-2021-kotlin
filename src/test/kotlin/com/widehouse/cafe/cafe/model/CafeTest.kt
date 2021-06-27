package com.widehouse.cafe.cafe.model

import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

internal class CafeTest {
    @Test
    fun testConstruction() {
        val cafe = Cafe("test", "name", "desc")
        // then
        then(cafe)
            .hasFieldOrPropertyWithValue("id", "test")
            .hasFieldOrPropertyWithValue("name", "name")
            .hasFieldOrPropertyWithValue("description", "desc")
    }
}
