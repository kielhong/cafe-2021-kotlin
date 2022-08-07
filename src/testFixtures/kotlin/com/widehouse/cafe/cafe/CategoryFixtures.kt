package com.widehouse.cafe.cafe

import com.widehouse.cafe.cafe.domain.Category

class CategoryFixtures {
    companion object {
        fun create(id: Long, name: String) = Category(id, name)
        fun create(id: Long) = create(id, "name$id")
    }
}
