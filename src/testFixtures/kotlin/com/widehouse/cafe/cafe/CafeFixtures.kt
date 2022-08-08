package com.widehouse.cafe.cafe

import com.widehouse.cafe.cafe.domain.Cafe

class CafeFixtures {
    companion object {
        fun create(id: String, name: String, description: String, categoryId: Long) = Cafe(id, name, description, categoryId)
        fun create() = create("test", "name", "desc", 1L)
        fun create(id: String) = create(id, "name$id", "desc$id", 1L)
    }
}
