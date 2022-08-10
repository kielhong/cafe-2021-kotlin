package com.widehouse.cafe.cafe.adapter.`in`.web

import com.widehouse.cafe.cafe.domain.Cafe

data class CafeRequest(
    var id: String?,
    val name: String,
    val description: String,
    val categoryId: Long
) {
    fun toDomain(): Cafe =
        Cafe(id!!, name, description, categoryId)
}
