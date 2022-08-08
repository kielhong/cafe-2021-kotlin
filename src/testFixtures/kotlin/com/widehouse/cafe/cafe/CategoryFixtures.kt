package com.widehouse.cafe.cafe

import com.widehouse.cafe.cafe.domain.Category

class CategoryFixtures {
    companion object {
        fun create(id: Long, name: String = "name$id", listOrder: Int = id.toInt()) = Category(id, name, listOrder)
    }
}
