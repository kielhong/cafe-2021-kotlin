package com.widehouse.cafe.cafe

import com.widehouse.cafe.cafe.model.Cafe

class CafeFixtures {
    companion object {
        @JvmStatic
        fun create(id: String) = Cafe(id)
        @JvmStatic
        fun create() = create("test")
    }
}
