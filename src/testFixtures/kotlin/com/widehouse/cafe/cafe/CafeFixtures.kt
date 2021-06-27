package com.widehouse.cafe.cafe

import com.widehouse.cafe.cafe.model.Cafe

class CafeFixtures {
    companion object {
        @JvmStatic
        fun create(id: String, name: String, description: String) = Cafe(id, name, description)
        @JvmStatic
        fun create() = create("test", "name", "desc")
    }
}
