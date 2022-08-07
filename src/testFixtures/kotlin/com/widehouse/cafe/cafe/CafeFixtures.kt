package com.widehouse.cafe.cafe

import com.widehouse.cafe.cafe.domain.Cafe

class CafeFixtures {
    companion object {
        fun create(id: String, name: String, description: String, theme: String) = Cafe(id, name, description, theme)
        fun create() = create("test", "name", "desc", "theme")
        fun create(id: String) = create(id, "name$id", "desc$id", "theme")
    }
}
